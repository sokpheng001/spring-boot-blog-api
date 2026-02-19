package sokpheng.com.blogapi.utils;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ResponseTemplate<T>(
        String sign,
        String status,
        LocalDate date,
        String message,
        T data
) {
}
