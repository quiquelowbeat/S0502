package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GameDto implements Serializable {

    // private String gameId;
    private int dice1;
    private int dice2;
    private String result;

}
