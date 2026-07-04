package cl.sanos_y_salvos.ms_base.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@Entity
@Table(name = "pet_types")
@AllArgsConstructor
@NoArgsConstructor

public class PetType {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_type", length = 30, nullable = false)
    private String nameType;

    @Column(name = "breed", length = 255)
    private String breed;

}
