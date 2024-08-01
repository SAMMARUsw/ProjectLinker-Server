package com.sammaru.projectlinker.domain.contest.payload;

import lombok.Builder;

@Builder
public record ContestResponse(
        String title,
        String company,
        String imageUrl
) {
}
