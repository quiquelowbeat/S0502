package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.controllers;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.dto.GameDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/players/{id}/games")
    public ResponseEntity<GameDto> newGame(@PathVariable Long id){
        try{
            return new ResponseEntity<>(gameService.newGame(id), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/players/{id}/games")
    public ResponseEntity<PlayerDto> deleteAllGamesByPlayerId(@PathVariable Long id){
        try{
            gameService.deleteAllGamesByPlayerId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/players/{id}/games")
    public ResponseEntity<List<GameDto>> getGamesByPlayerId(@PathVariable Long id){
        try{
            return new ResponseEntity<>(gameService.getGamesByPlayerId(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
