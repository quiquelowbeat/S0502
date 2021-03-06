package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.components.Mapper;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.domains.Game;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.domains.Player;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.dto.GameDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.repositories.PlayerRepository;
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
    public GameDto newGame(String playerId) {
        Player player = findPlayer(playerId);
        Game game = Game.getInstance();
        if(game.getResult().equals("WIN")){
            player.setTotalWins(player.getTotalWins() + 1);
        }
        /*
        M??todo player.calculateWinningPercentage() creado en el domain Player para calcular el porcentaje
        de partidas ganadas.
        */
        if(player.getGames() != null){
            player.getGames().add(game);
        }
        player.setWinningPercentage(player.calculateWinningPercentage());
        playerRepository.save(player); // Update de los valores actualizados de partidas ganadas y porcetanje de ??xito.
        return mapper.toGameDto(game);
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
                .map(player -> mapper.toPlayerDto(player))
                .collect(Collectors.toList());
    }

    @Override
    public List<GameDto> getGamesByPlayerId(String playerId){
        Player player = findPlayer(playerId);
        List<GameDto> gameDtoList = player.getGames().stream()
                .map(game -> mapper.toGameDto(game))
                .collect(Collectors.toList());
        if(gameDtoList.isEmpty()){
            throw new NullPointerException("Game list is empty."); // Lanzamos excepci??n en caso de que la lista est?? vac??a.
        } else {
            return gameDtoList;
        }
    }

    @Override
    public Map<String, Double> getRankingOfAllPlayers() {
        Map<String, Double> rankingMap = new LinkedHashMap<>();
        // Usamos stream en la lista de jugadores para ordenar por porcentaje de acierto antes de pasarla al LinkedHashMap.
        // Usamos LinkedHashMap porque guarda los valores por orden de entrada al map.
        List<Player> playerList = playerRepository.findAll().stream()
                .sorted(Comparator.comparing(Player::getWinningPercentage).reversed())
                .collect(Collectors.toList());
        // Con el forEach pasamos de la List al Map
        playerList.forEach(p -> rankingMap.put("playerId: " + p.getPlayerId(), p.getWinningPercentage()));
        if(playerList.isEmpty()){
            throw new NullPointerException("Player's list is empty."); // Lanzamos excepci??n en caso de que la lista est?? vac??a.
        } else {
            return rankingMap;
        }
    }

    @Override
    public PlayerDto getLoser() {
        Player player = null;
        // Usamos stream para buscar el valor m??nimo usando una functional interface dentro
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
        // Usamos stream para buscar el valor m??ximo usando una functional interface dentro
        // de .mac -> Player::getWinningPercentage
        Optional<Player> playerOptional = playerRepository.findAll().stream()
                 .max(Comparator.comparing(Player::getWinningPercentage));
         if(playerOptional.isPresent()){
            player = playerOptional.get();
         }
         return mapper.toPlayerDto(player);
    }

    @Override
    public Player findPlayer(String playerId){
        Optional<Player> playerOptional = playerRepository.findByPlayerId(playerId);
        Player player = null;
        if(playerOptional.isPresent()){
            player = playerOptional.get();
        } else {
            System.err.println("Player not found.");
        }
        return player;
    }
}
