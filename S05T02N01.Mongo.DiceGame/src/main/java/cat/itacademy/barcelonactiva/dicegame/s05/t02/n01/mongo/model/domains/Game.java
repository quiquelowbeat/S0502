package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@Setter
@ToString
// @NoArgsConstructor
//@Document(collection = "games")
public class Game {

    // @Id
    // private String gameId;
    private int dice1;
    private int dice2;
    private String result;
//    @DocumentReference(lazy = true)
//    private Player player;


    public Game(/*Player player*/){
        this.dice1 = getRandomNumberFromOneToSeven();
        this.dice2 = getRandomNumberFromOneToSeven();
        this.result = rollTheDice();
        // this.player = player;
    }

    public int getRandomNumberFromOneToSeven(){
        return (int) (Math.random() * 7) + 1;
    }

    public String rollTheDice(){
        String result;
        if((this.dice1 + this.dice2) == 7){
            result = "WIN";
        } else {
            result = "LOSE";
        }
        return result;
    }

    // Static Factory Method
    public static Game getInstance(/*Player player*/){
        return new Game(/*player*/);
    }

}
