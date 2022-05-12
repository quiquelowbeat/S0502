package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.dto.PlayerDto;

import java.util.List;
import java.util.Map;

public interface PlayerService {

    PlayerDto createPlayer(String name);
    PlayerDto updatePlayerName(PlayerDto playerDto);
    List<PlayerDto> getAllPlayers();
    Map<String, Float> getRankingOfAllPlayers();
    PlayerDto getLoser();
    PlayerDto getWinner();

}
