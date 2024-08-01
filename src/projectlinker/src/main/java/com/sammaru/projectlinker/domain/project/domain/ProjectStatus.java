package com.sammaru.projectlinker.domain.project.domain;

import lombok.Getter;

@Getter
public enum ProjectStatus {
    RECRUITING("recruiting"),
    CLOSED("closed"),
    END("end");

    private String status;

    ProjectStatus(String status){
        this.status = status;
    }
}
