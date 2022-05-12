package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.domains.Player;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.dto.GameDto;

import java.util.List;

public interface GameService {

    GameDto newGame(Long playerId);
    List<GameDto> getGamesByPlayerId(Long playerId);
    void deleteAllGamesByPlayerId(Long playerId);
    Player findPlayer(Long playerId); // Método que usamos en GameServiceImpl para buscar Players en repo de jugadores.

}
