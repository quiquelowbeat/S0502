package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongosecurity.model.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PlayerDto implements Serializable {

    private String playerId;
    private String name;
    private Date date;
    private Double winningPercentage;

}


