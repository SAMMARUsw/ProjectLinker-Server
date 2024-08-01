package com.sammaru.projectlinker.domain.application.payload.response;

import lombok.Builder;

@Builder
public record ProjectMemberResponse(
        String name,
        String email
) {
}
