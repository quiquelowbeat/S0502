package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.sql.model.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    public Player(String name){
        this.name = name;
        this.date = LocalDate.now();
    }

    // Static Factory Method - devuelve una instancia del objeto Player.
    public static Player getInstance(String name){
        return new Player(name);
    }


}
