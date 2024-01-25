package kz.abuova.FinalProjectOct.antities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "votes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long Id;
    private int upvote;
    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;
    @ManyToOne(fetch = FetchType.EAGER)
    private Users user;
}
