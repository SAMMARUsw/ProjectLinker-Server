package com.sammaru.projectlinker.domain.application.domain;

import lombok.Getter;

@Getter
public enum ApplicationStatus {
    심사중("onreview"),
    수락("accept"),
    거절("reject");

    private String status;

    ApplicationStatus(String status){
        this.status = status;
    }
}
