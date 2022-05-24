package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.controllers;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.dto.GameDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/private", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/players/{id}/games")
    public ResponseEntity<GameDto> newGame(@PathVariable String id){
        try{
            return new ResponseEntity<>(gameService.newGame(id), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/players/{id}/games")
    public ResponseEntity<List<GameDto>> getGamesByPlayerId(@PathVariable String id){
        try{
            return new ResponseEntity<>(gameService.getGamesByPlayerId(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
}
