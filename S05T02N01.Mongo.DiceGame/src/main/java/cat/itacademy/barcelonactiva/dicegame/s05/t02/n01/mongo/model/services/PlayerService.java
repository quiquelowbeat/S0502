package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.domains.Player;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.dto.GameDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.dto.PlayerDto;

import java.util.List;
import java.util.Map;

public interface PlayerService {

    PlayerDto createPlayer(String name);
    PlayerDto updatePlayerName(PlayerDto playerDto);
    List<PlayerDto> getAllPlayers();
    Map<String, Double> getRankingOfAllPlayers();
    PlayerDto getLoser();
    PlayerDto getWinner();
    GameDto newGame(String playerId);
    Player findPlayer(String playerId);
    // List<GameDto> getGamesByPlayerId(String playerId);
    // void deleteAllGamesByPlayerId(String playerId);

}
