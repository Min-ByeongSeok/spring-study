package zerobase.mission.dto;

import lombok.*;
import org.springframework.data.domain.Page;
import zerobase.mission.domain.Store;
import zerobase.mission.dto.member.ManagerDto;
import zerobase.mission.type.Role;

import java.time.LocalTime;
import java.util.List;

public class StoreListDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String name;
        private String description;
        private LocalTime openTime;
        private LocalTime closeTime;
        private int thumbsUp;

        public Page<Response> fromEntity(Page<Store> store) {
            return store.map(m -> Response.builder()
                    .name(m.getName())
                    .description(m.getDescription())
                    .openTime(m.getOpenTime())
                    .closeTime(m.getCloseTime())
                    .thumbsUp(m.getThumbsUp())
                    .build());
        }

        public static Response fromEntity(Store store) {
            return Response.builder()
                    .name(store.getName())
                    .description(store.getDescription())
                    .openTime(store.getOpenTime())
                    .closeTime(store.getCloseTime())
                    .thumbsUp(store.getThumbsUp())
                    .build();
        }
    }
}
