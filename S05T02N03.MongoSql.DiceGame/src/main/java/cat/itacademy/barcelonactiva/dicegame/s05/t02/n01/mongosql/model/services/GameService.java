package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.dto.GameDto;

import java.util.List;

public interface GameService {

    GameDto newGame(String playerId);
    List<String> getGamesByPlayerId(String playerId);

}
