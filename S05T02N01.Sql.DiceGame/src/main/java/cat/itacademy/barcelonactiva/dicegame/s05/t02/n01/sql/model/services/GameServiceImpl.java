package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.services;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.domains.Game;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.domains.Player;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.dto.GameDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.components.Mapper;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.repositories.GameRepository;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService{

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public GameDto newGame(Long playerId) {
        Player player = findPlayer(playerId);
        Game game;
        game = Game.getInstance(player);
        if(game.getResult().equals("WIN")){
            player.setTotalWins(player.getTotalWins() + 1);
        }
        /*
        Método player.calculateWinningPercentage() creado en el domain Player para calcular el porcentaje
        de partidas ganadas.
        */
        player.setWinningPercentage(player.calculateWinningPercentage());
        return mapper.toGameDto(gameRepository.save(game));
    }

    @Override
    public List<GameDto> getGamesByPlayerId(Long playerId){
        Player player = findPlayer(playerId);
        List<GameDto> gameDtoList = gameRepository.findByPlayer(player).stream()
                .map(game -> mapper.toGameDto(game))
                .collect(Collectors.toList());
        if(gameDtoList.isEmpty()){
            throw new NullPointerException("Empty game list."); // Lanzamos excepción en caso de que la lista esté vacía.
        } else {
            return gameDtoList;
        }
    }

    @Override
    public void deleteAllGamesByPlayerId(Long playerId){
        Player player = findPlayer(playerId);
        // Hacemos una lista con los juegos del jugador en concreto y procedemos a borrarlos.
        List<Game> gameList = gameRepository.findByPlayer(player);
        for(Game game : gameList){
            gameRepository.delete(game);
        }
        // Actualizamos el porcentaje de acierto y las partidas ganadas a 0 y actualizamos al entidad Player.
        player.setWinningPercentage(0.0f);
        player.setTotalWins(0);
        playerRepository.save(player);
    }

    // Método findPlayer - busca en playerRepository Player por ID y devuelve el player encontrado.
    @Override
    public Player findPlayer(Long playerId){
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
