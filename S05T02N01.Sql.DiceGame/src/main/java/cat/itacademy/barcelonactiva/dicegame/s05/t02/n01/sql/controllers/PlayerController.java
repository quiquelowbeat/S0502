package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.controllers;

import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.dto.PlayerDto;
import cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
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
    public ResponseEntity<PlayerDto> updatePlayer(@RequestBody PlayerDto playerDto){
        try{
            return new ResponseEntity<>(playerService.updatePlayerName(playerDto), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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

    @GetMapping("/players/ranking")
    public ResponseEntity<Map<String, Float>> getRanking(){
        try{
            return new ResponseEntity<>(playerService.getRankingOfAllPlayers(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/players/ranking/loser")
    public ResponseEntity<PlayerDto> getLooser(){
        try{
            return new ResponseEntity<>(playerService.getLoser(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/players/ranking/winner")
    public ResponseEntity<PlayerDto> getWinner(){
        try{
            return new ResponseEntity<>(playerService.getWinner(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
