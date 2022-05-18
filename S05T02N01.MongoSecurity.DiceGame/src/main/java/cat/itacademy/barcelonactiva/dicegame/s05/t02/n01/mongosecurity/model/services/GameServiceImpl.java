package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.components.Mapper;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.domains.Game;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.domains.Player;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.dto.GameDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    @Autowired
    private final PlayerRepository playerRepository;

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
        if(player.getGames() != null) player.getGames().add(game);
        player.setWinningPercentage(player.calculateWinningPercentage());
        playerRepository.save(player); // Update de los valores actualizados de partidas ganadas y porcetanje de éxito.
        return mapper.toGameDto(game);
    }

    @Override
    public List<GameDto> getGamesByPlayerId(String playerId){
        Player player = playerService.findPlayer(playerId);
        List<GameDto> gameDtoList = player.getGames().stream()
                .map(mapper::toGameDto) // game -> mapper.toGameDto(game)
                .collect(Collectors.toList());
        if(gameDtoList.isEmpty()) throw new NullPointerException("Game list is empty."); // Lanzamos excepción en caso de que la lista esté vacía.
        return gameDtoList;
    }
}
