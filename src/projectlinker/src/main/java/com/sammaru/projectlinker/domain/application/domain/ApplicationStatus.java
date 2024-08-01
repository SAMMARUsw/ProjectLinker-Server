package com.sammaru.projectlinker.domain.application.domain;

import lombok.Getter;

@Getter
public enum ApplicationStatus {
    ON_REVIEW("on review"),
    ACCEPT("accept"),
    REJECT("reject");

    private String status;

    ApplicationStatus(String status){
        this.status = status;
    }
}
