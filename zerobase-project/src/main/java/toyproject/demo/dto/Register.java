package toyproject.demo.dto;

import lombok.*;
import toyproject.demo.domain.embedded.StoreInfo;

public class Register {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private StoreInfo storeInfo;
        private String managerId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String message;
    }
}
