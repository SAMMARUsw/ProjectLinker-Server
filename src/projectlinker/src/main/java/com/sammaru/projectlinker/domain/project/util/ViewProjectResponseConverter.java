package com.sammaru.projectlinker.domain.project.util;

import com.sammaru.projectlinker.domain.project.domain.Project;
import com.sammaru.projectlinker.domain.project.payload.response.ViewProjectListResponse;
import com.sammaru.projectlinker.domain.project.payload.response.ViewProjectResponse;

public class ViewProjectResponseConverter {
    public static ViewProjectListResponse from(Project project){
        return ViewProjectListResponse.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .content(project.getContent())
                .userId(project.getUserId())
                .status(project.getStatus())
                .vacancyNum(project.getVacancyNum())
                .currentNum(project.getCurrentNum())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }

    public static ViewProjectResponse fromProject(Project project){
        return ViewProjectResponse.builder()
                .projectId(project.getProjectId())
                .title(project.getTitle())
                .content(project.getContent())
                .userId(project.getUserId())
                .status(project.getStatus())
                .vacancyNum(project.getVacancyNum())
                .currentNum(project.getCurrentNum())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}
