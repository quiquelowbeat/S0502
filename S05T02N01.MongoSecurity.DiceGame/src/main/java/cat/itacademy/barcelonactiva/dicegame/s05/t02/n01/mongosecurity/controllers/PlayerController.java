package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.controllers;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.dto.GameDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/private", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping({"/players", "/players/{name}"})
    public ResponseEntity<PlayerDto> createPlayer(@PathVariable(required = false) String name){
        try{
            return new ResponseEntity<>(playerService.createPlayer(name), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/players")
    public ResponseEntity<PlayerDto> updatePlayerName(@RequestBody PlayerDto playerDto){
        try{
            return new ResponseEntity<>(playerService.updatePlayerName(playerDto), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/players/{id}/games")
    public ResponseEntity<GameDto> newGame(@PathVariable String id){
        try{
            return new ResponseEntity<>(playerService.newGame(id), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/players/{id}/games")
    public ResponseEntity<PlayerDto> deleteAllGamesByPlayerId(@PathVariable String id){
        try{
            playerService.deleteAllGamesByPlayerId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/players")
    public ResponseEntity<List<PlayerDto>> getAllPlayers(){
        List<PlayerDto> playerDtoList = new ArrayList<>(playerService.getAllPlayers());
        if(playerDtoList.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(playerDtoList, HttpStatus.OK);
        }
    }


    @GetMapping("/players/{id}/games")
    public ResponseEntity<List<GameDto>> getGamesByPlayerId(@PathVariable String id){
        try{
            return new ResponseEntity<>(playerService.getGamesByPlayerId(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/players/ranking")
    public ResponseEntity<Map<String, Double>> getRankingOfAllPlayers(){
        try{
            return new ResponseEntity<>(playerService.getRankingOfAllPlayers(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/players/ranking/loser")
    public ResponseEntity<PlayerDto> getLoser(){
        try{
            return new ResponseEntity<>(playerService.getLoser(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/players/ranking/winner")
    public ResponseEntity<PlayerDto> getWinner(){
        try{
            return new ResponseEntity<>(playerService.getWinner(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
}
