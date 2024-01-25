package kz.abuova.FinalProjectOct.antities;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    private Timestamp date;
    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;
    @ManyToOne(fetch = FetchType.EAGER)
    private Users users;



}
