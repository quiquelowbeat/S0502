package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.domains;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "games")
//@EnableJpaRepositories(basePackageClasses=Game.class)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private int dice1;
    private int dice2;
    private String result;

    public Game(){
        this.dice1 = getRandomNumberFromOneToSeven();
        this.dice2 = getRandomNumberFromOneToSeven();
        this.result = rollTheDice();
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
    public static Game getInstance(){
        return new Game();
    }

}
