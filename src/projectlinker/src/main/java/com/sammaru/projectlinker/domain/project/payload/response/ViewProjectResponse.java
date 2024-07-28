package com.sammaru.projectlinker.domain.project.payload.response;

import com.sammaru.projectlinker.domain.project.domain.ProjectStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ViewProjectResponse(
        Long projectId,
        String title,
        String content,
        Long userId,
        ProjectStatus status,
        Integer vacancyNum,
        Integer currentNum,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
