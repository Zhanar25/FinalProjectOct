package kz.abuova.FinalProjectOct.antities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="users_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;

    private int quantity;
}
