package com.sammaru.projectlinker.domain.project.payload.response;

import com.sammaru.projectlinker.domain.project.domain.ProjectStatus;
import jakarta.persistence.Column;
import lombok.Builder;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Builder
public record ViewProjectListResponse(
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
