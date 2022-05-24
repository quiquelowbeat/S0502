package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.dto.GameDto;

import java.util.List;

public interface GameService {

    GameDto newGame(String playerId);
    List<GameDto> getGamesByPlayerId(String playerId);

}
