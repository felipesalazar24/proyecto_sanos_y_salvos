package cl.sanos_y_salvos.ms_base.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "pets")
@AllArgsConstructor
@NoArgsConstructor

public class Pet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "age_category", nullable = false)
    private String ageCategory;

    @Column(name = "type_id", nullable = false)
    private String typeId;

    @Column(name = "last_Seen_Location", length = 100)
    private String lastSeenLocation;

    @Column(name = "last_Seen_Date", nullable = false)
    private Date lastSeenDate;

    @Column (name = "color", length = 30)
    private String color;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "status", nullable = false)
    private String status;

}
