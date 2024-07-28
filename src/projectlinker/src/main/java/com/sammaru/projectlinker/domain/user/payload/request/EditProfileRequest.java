package com.sammaru.projectlinker.domain.user.payload.request;

public record EditProfileRequest(
        String nickname,
        String phnum
) {
}
