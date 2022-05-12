package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.domains.Player;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.components.Mapper;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public PlayerDto createPlayer(String name) {
        // Objects.requireNonNullElse(name, "ANONIMOUS") - crea el objeto con name o "ANONIMOUS" si name es null.
        Player player = Player.getInstance(Objects.requireNonNullElse(name, "ANONIMOUS"));
        return mapper.toPlayerDto(playerRepository.save(player));
    }

    @Override
    public PlayerDto updatePlayerName(PlayerDto playerDto) {
        Optional<Player> playerOptional = playerRepository.findByPlayerId(playerDto.getPlayerId());
        Player playerUpdated = null;
        if(playerOptional.isPresent()){
            playerUpdated = playerOptional.get();
            playerUpdated.setName(playerDto.getName());
        } else {
            System.err.println("Player not found.");
        }
        return mapper.toPlayerDto(playerRepository.save(playerUpdated));
    }

    @Override
    public List<PlayerDto> getAllPlayers() {
        // Fem una llista de Dtos amb totes les entitats y la retornem.
        return playerRepository.findAll().stream()
                .map(player -> mapper.toPlayerDto(player))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Float> getRankingOfAllPlayers() {
        Map<String, Float> rankingMap = new LinkedHashMap<>();
        // Usamos stream en la lista de jugadores para ordenar por porcentaje de acierto antes de pasarla al LinkedHashMap.
        // Usamos LinkedHashMap porque guarda los valores por orden de entrada al map.
        List<Player> playerList = playerRepository.findAll().stream()
                .sorted(Comparator.comparing(Player::getWinningPercentage).reversed())
                .collect(Collectors.toList());
        // Con el forEach pasamos de la List al Map
        playerList.forEach(p -> rankingMap.put("playerId: " + p.getPlayerId(), p.getWinningPercentage()));
        if(playerList.isEmpty()){
            throw new NullPointerException("Player's list is empty."); // Lanzamos excepción en caso de que la lista esté vacía.
        } else {
            return rankingMap;
        }
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

}
