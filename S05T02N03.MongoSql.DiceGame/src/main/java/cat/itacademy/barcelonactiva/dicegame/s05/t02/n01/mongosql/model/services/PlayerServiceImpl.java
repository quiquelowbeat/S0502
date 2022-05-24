package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.components.Mapper;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.domains.Player;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private final PlayerRepository playerRepository;

    @Autowired
    private final Mapper mapper;

    @Override
    public PlayerDto createPlayer(String name) {
        Player player = Player.getInstance(name);
        return mapper.toPlayerDto(playerRepository.save(player));
    }

    @Override
    public PlayerDto updatePlayerName(PlayerDto playerDto) {
        Player playerUpdated = null;
        playerUpdated = findPlayer(playerDto.getPlayerId());
        playerUpdated.setName(playerDto.getName());
        return mapper.toPlayerDto(playerRepository.save(playerUpdated));
    }

    @Override
    public void deleteAllGamesByPlayerId(String playerId){
        Player player = findPlayer(playerId);
        // Llamamos a los juegos del jugador en concreto y procedemos a borrarlos.
        player.getGames().clear();
        // Actualizamos el porcentaje de acierto y las partidas ganadas a 0 y actualizamos al entidad Player.
        player.setWinningPercentage(0.0d);
        player.setTotalWins(0);
        playerRepository.save(player);
    }

    @Override
    public List<PlayerDto> getAllPlayers() {
        // Fem una llista de Dtos amb totes les entitats y la retornem.
        return playerRepository.findAll().stream()
                .map(mapper::toPlayerDto) // player -> mapper.toPlayerDto(player
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> getRankingOfAllPlayers() {
        Map<String, Double> rankingMap = new LinkedHashMap<>();
        // Usamos stream en la lista de jugadores para ordenar por porcentaje de acierto antes de pasarla al LinkedHashMap.
        // Usamos LinkedHashMap porque guarda los valores por orden de entrada al map.
        List<Player> playerList = playerRepository.findAll().stream()
                .sorted(Comparator.comparing(Player::getWinningPercentage).reversed())
                .collect(Collectors.toList());
        if(playerList.isEmpty()) throw new NullPointerException("Player's list is empty.");
        // Con el forEach pasamos de la List al Map
        playerList.forEach(p -> rankingMap.put("playerId: " + p.getPlayerId(), p.getWinningPercentage()));// Lanzamos excepción en caso de que la lista esté vacía.
        return rankingMap;
    }

    @Override
    public PlayerDto getLoser() {
        Player player = null;
        // Usamos stream para buscar el valor mínimo usando una functional interface dentro
        // de .min -> Player::getWinningPercentage
        Optional<Player> playerOptional = playerRepository.findAll().stream()
                .min(Comparator.comparing(Player::getWinningPercentage));
        if(playerOptional.isPresent()){
            player = playerOptional.get();
        }
        return mapper.toPlayerDto(player);
    }

    @Override
    public PlayerDto getWinner() {
         Player player = null;
        // Usamos stream para buscar el valor máximo usando una functional interface dentro
        // de .mac -> Player::getWinningPercentage
        Optional<Player> playerOptional = playerRepository.findAll().stream()
                 .max(Comparator.comparing(Player::getWinningPercentage));
         if(playerOptional.isPresent()){
            player = playerOptional.get();
         }
         return mapper.toPlayerDto(player);
    }

    @Override
    // Abstraemos la búsqueda de player usando Optional al método findPlayer()
    public Player findPlayer(String playerId){
        Optional<Player> playerOptional = playerRepository.findByPlayerId(playerId);
        Player player = null;
        if(playerOptional.isEmpty()){
            System.err.println("Player not found.");
            return null;
        }
        player = playerOptional.get();
        return player;
    }
}
