package kz.abuova.FinalProjectOct.antities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<TypeOfMeat> typeOfMeats;

}
