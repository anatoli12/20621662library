package bg.tuvarna.persistence.entity;

import bg.tuvarna.api.BookGenre;
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
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "book_id", nullable = false)
    private UUID id;
    @Column
    private String title;
    @Column
    private String isbn;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
    @Column
    @Enumerated(EnumType.STRING)
    private BookGenre bookGenre;
    @OneToMany(mappedBy = "book")
    private List<BookItem> bookItems;
}
