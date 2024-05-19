package bg.tuvarna.persistence.entity;

import bg.tuvarna.api.BookStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookitems")
public class BookItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "bookitem_id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    private Reader reader;

    @Column
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
}
