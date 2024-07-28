package com.sammaru.projectlinker.domain.user.payload.token;

import lombok.Builder;

@Builder
public record MemberInfo(
        String email,
        String name
) {
}
