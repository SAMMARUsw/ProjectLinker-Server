package com.sammaru.projectlinker.domain.application.util;

import com.sammaru.projectlinker.domain.application.domain.Application;
import com.sammaru.projectlinker.domain.application.payload.response.ProjectApplyResponse;

public class ProjectApplyResponseConverter {
    public static ProjectApplyResponse from(Application application){
        return ProjectApplyResponse.builder()
                .projectId(application.getProjectId())
                .message(application.getMessage())
                .status(application.getStatus())
                .userId(application.getUserId())
                .build();
    }
}
