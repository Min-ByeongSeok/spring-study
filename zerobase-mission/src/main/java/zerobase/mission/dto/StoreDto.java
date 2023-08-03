package zerobase.mission.dto;

import lombok.*;
import zerobase.mission.domain.Store;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {
    private String name;
    private boolean isRegistered;

    public static StoreDto fromEntity(Store store) {
        return StoreDto.builder()
                .name(store.getName())
                .isRegistered(true)
                .build();
    }
}
