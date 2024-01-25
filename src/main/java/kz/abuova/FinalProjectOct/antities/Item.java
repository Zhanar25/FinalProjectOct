package kz.abuova.FinalProjectOct.antities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private int amount;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Category> categories;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Company> companies;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment>comments;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Users>users;


}
