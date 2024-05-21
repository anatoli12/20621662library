package bg.tuvarna.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "author_id", nullable = false)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Book> books;
}
