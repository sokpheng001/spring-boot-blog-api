package sokpheng.com.blogapi.utils;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;

@Builder
public record ResponseTemplate<T>(
        String sign,
        String status,
        Date date,
        String message,
        T data
) {
}
