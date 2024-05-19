package bg.tuvarna.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reader")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reader_id")
    private UUID id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String phoneNumber;
    @Column
    private String email;
    @OneToMany(mappedBy = "reader")
    private List<BookItem> bookItemList;
    @Column
    @Min(1)
    @Max(5)
    private Integer readerRating;
}
