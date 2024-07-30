package com.sammaru.projectlinker.domain.application.payload.request;

import com.sammaru.projectlinker.domain.application.domain.ApplicationStatus;

public record CheckProjectApplyRequest(
        ApplicationStatus status
) {
}
