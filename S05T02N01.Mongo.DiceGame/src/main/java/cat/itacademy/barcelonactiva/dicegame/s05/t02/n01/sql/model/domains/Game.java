package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Table(name = "games")
@Entity
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameId;
    private int dice1;
    private int dice2;
    private String result;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    @ToString.Exclude
    private Player player;

    public Game(Player player){
        this.dice1 = getRandomNumberFromOneToSeven();
        this.dice2 = getRandomNumberFromOneToSeven();
        this.result = rollTheDice();
        this.player = player;
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
    public static Game getInstance(Player player){
        return new Game(player);
    }

}
