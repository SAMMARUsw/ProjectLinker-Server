package com.sammaru.projectlinker.domain.project.domain;

import lombok.Getter;

@Getter
public enum ProjectStatus {
    모집중("recruiting"),
    모집완료("cloosed"),
    종료("end");

    private String status;

    ProjectStatus(String status){
        this.status = status;
    }
}
