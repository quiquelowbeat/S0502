package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.domains;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@Table(name = "players")
@Entity
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long playerId;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date date;
    private int totalWins;
    private Float winningPercentage;
    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Game> games;

    public Player(String name){
        this.name = name;
        this.date = Calendar.getInstance().getTime();
        totalWins = 0;
        this.winningPercentage = 0.0f;
    }

    public float calculateWinningPercentage(){
        float result;
        result = (totalWins / (float) games.size()) * 100;
        if(Float.isNaN(result) || games.size() == 0){
            result = 0.0f;
        }
        return result;
    }

    // Static Factory Method - devuelve una instancia del objeto Player.
    public static Player getInstance(String name){
        return new Player(name);
    }
}
