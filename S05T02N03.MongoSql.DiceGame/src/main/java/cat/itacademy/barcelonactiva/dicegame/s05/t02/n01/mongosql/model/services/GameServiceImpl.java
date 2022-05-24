package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.components.Mapper;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.domains.Game;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.domains.Player;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.dto.GameDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.repositories.GameRepository;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    @Autowired
    private final PlayerRepository playerRepository;

    @Autowired
    private final GameRepository gameRepository;

    @Autowired
    private final PlayerServiceImpl playerService;

    @Autowired
    private final Mapper mapper;

    @Override
    public GameDto newGame(String playerId) {
        Player player = playerService.findPlayer(playerId);
        Game game = Game.getInstance();
        if(game.getResult().equals("WIN")) player.setTotalWins(player.getTotalWins() + 1);
        /*
        Método player.calculateWinningPercentage() creado en el domain Player para calcular el porcentaje
        de partidas ganadas.
        */
        player.getGames().add("ID: " + gameRepository.save(game).getId() + " - Result: " + game.getResult());
        player.setWinningPercentage(player.calculateWinningPercentage());
        playerRepository.save(player);

        // Update de los valores actualizados de partidas ganadas y porcetanje de éxito.
        return mapper.toGameDto(game);
    }

    @Override
    public List<String> getGamesByPlayerId(String playerId){
        Player player = playerService.findPlayer(playerId);
        /*
        List<Long> gameDtoList = player.getGames().stream()
                .map(mapper::toGameDto) // game -> mapper.toGameDto(game)
                .collect(Collectors.toList());
        */
        if(player.getGames().isEmpty()) throw new NullPointerException("Game list is empty."); // Lanzamos excepción en caso de que la lista esté vacía.
        return player.getGames();
    }
}
