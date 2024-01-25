package kz.abuova.FinalProjectOct.antities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeOfMeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
