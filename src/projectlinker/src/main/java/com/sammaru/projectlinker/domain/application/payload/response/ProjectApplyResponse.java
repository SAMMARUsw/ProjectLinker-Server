package com.sammaru.projectlinker.domain.application.payload.response;

import com.sammaru.projectlinker.domain.application.domain.ApplicationStatus;
import lombok.Builder;

@Builder
public record ProjectApplyResponse(
        String message,
        Long userId,
        Long projectId,
        ApplicationStatus status
) {
}
