package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.domains.Player;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.dto.PlayerDto;

import java.util.List;
import java.util.Map;

public interface PlayerService {

    PlayerDto createPlayer(String name);
    PlayerDto updatePlayerName(PlayerDto playerDto);
    void deleteAllGamesByPlayerId(String playerId);
    List<PlayerDto> getAllPlayers();
    Map<String, Double> getRankingOfAllPlayers();
    PlayerDto getLoser();
    PlayerDto getWinner();
    Player findPlayer(String playerId);

}
