package bg.tuvarna.api.operations.util;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Integer rating;
}
