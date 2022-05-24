package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosql.model.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class PlayerDto implements Serializable {

    private String playerId;
    private String name;
    private Date date;
    private Double winningPercentage;
    private List<String> games;

}


