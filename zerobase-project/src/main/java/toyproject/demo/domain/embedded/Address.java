package toyproject.demo.domain.embedded;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Address {
    @NotBlank
    private String city;
    @NotBlank
    private String street;
}
