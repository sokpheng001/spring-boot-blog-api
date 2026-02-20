package sokpheng.com.blogapi.utils;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record TokenTemplate(
        String accessToken,
        LocalDateTime accessTokenExpireDate,
        String refreshToken,
        LocalDateTime refreshTokenExpireDate
) { }
