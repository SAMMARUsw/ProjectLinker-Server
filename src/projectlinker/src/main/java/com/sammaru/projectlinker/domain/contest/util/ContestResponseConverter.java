package com.sammaru.projectlinker.domain.contest.util;

import com.sammaru.projectlinker.domain.application.domain.Application;
import com.sammaru.projectlinker.domain.application.payload.response.ProjectApplyResponse;
import com.sammaru.projectlinker.domain.contest.domain.Contest;
import com.sammaru.projectlinker.domain.contest.payload.ContestResponse;

public class ContestResponseConverter {
    public static ContestResponse from(Contest contest){
        return ContestResponse.builder()
                .title(contest.getTitle())
                .company(contest.getCompany())
                .imageUrl(contest.getImageUrl())
                .build();
    }
}
