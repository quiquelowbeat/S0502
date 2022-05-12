package cat.itacademy.barcelonactiva.dicegame.s05.t02.n01.mongo.model.dto;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
public class PlayerDto implements Serializable {

    private String playerId;
    private String name;
    private Date date;
    private Double winningPercentage;

}


