package com.sammaru.projectlinker.domain.project.payload.request;

import com.sammaru.projectlinker.domain.project.domain.ProjectStatus;

public record EditProjectRequest(
        String title,
        String content,
        ProjectStatus status,
        Integer vacancyNum,
        Integer currentNum
) {
}
