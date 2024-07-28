package com.sammaru.projectlinker.domain.project.payload.request;

import com.sammaru.projectlinker.domain.project.domain.ProjectStatus;

public record CreateProjectRequest(
        String title,
        String content,
        ProjectStatus status,
        Integer vacanctNum,
        Integer currentNum
) {
}
