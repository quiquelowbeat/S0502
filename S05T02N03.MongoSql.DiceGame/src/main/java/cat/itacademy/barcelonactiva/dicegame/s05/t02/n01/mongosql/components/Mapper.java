package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.components;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.domains.Game;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.domains.Player;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.dto.GameDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.dto.PlayerDto;

import org.springframework.stereotype.Component;

@Component
public class Mapper {

    // Método para transformar Dto a Entity
    public Player toPlayerEntity(PlayerDto playerDto){
        Player player = Player.getInstance(playerDto.getName());
        player.setPlayerId(player.getPlayerId());
        player.setDate(playerDto.getDate());
        return player;
    }

    // Método para transformar Entity a Dto
    public PlayerDto toPlayerDto(Player player){
        PlayerDto playerDto = new PlayerDto();
        playerDto.setPlayerId(player.getPlayerId());
        playerDto.setName(player.getName());
        playerDto.setDate(player.getDate());
        playerDto.setWinningPercentage(player.getWinningPercentage());
        return playerDto;
    }

    // Método para transformar Dto a Entity
    public Game toGameEntity(GameDto gameDto) {
        Game game = new Game();
        game.setDice1(game.getDice1());
        game.setDice2(game.getDice2());
        game.setResult(game.getResult());
        return game;
    }

    // Método para transformar Entity a Dto
    public GameDto toGameDto(Game game){
        GameDto gameDto = new GameDto();
        gameDto.setDice1(game.getDice1());
        gameDto.setDice2(game.getDice2());
        gameDto.setResult(game.getResult());
        return gameDto;
    }
}