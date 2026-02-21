package sokpheng.com.blogapi.model.dto;

import lombok.Builder;

@Builder
public record TokenTemplate(
        String accessToken,
        Long accessTokenExpireInMilliSecond,
        String refreshToken,
        Long refreshTokenExpireInMilliSecond
) { }
