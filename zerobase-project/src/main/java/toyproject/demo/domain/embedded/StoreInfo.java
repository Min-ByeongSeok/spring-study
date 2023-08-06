package toyproject.demo.domain.embedded;

import lombok.*;
import toyproject.demo.domain.Store;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class StoreInfo {
    @NotBlank
    private String name;

    private String description;

    @Embedded
    private Address address;

    private int thumbsUp;

    public static StoreInfo fromEntity(Store store) {
        return StoreInfo.builder()
                .name(store.getStoreInfo().getName())
                .description(store.getStoreInfo().getDescription())
                .address(store.getStoreInfo().getAddress())
                .thumbsUp(store.getStoreInfo().getThumbsUp())
                .build();
    }
}
