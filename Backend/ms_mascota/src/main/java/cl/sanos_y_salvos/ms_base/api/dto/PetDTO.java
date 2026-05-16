package cl.sanos_y_salvos.ms_base.api.dto;

import lombok.Data;
import java.util.Date;


@Data

public class PetDTO {
    
    private Long id;
    private String name;
    private String ageCategory;
    private String typeId;
    private Long userId;
    private String lastSeenLocation;
    private Date lastSeenDate;
    private String color;
    private String description;
    private String status;

}
