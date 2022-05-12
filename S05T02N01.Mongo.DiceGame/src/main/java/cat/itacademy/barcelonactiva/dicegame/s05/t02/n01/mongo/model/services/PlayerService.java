package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.domains.Player;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.dto.GameDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.dto.PlayerDto;

import java.util.List;
import java.util.Map;

public interface PlayerService {

    PlayerDto createPlayer(String name);
    PlayerDto updatePlayerName(PlayerDto playerDto);
    GameDto newGame(String playerId);
    void deleteAllGamesByPlayerId(String playerId);
    List<PlayerDto> getAllPlayers();
    List<GameDto> getGamesByPlayerId(String playerId);
    Map<String, Double> getRankingOfAllPlayers();
    PlayerDto getLoser();
    PlayerDto getWinner();
    Player findPlayer(String playerId);

}
