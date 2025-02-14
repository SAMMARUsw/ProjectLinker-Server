package com.sammaru.projectlinker.domain.contest.controller;

import com.sammaru.projectlinker.domain.contest.payload.ContestResponse;
import com.sammaru.projectlinker.domain.contest.service.ContestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contest/api/v1")
@RequiredArgsConstructor
@Validated
public class ContestController {
    private final ContestService contestService;

    @Tag(name = "공모전 글 목록 조회", description = "공모전 글 목록을 조회하는 api, 10개 반환")
    @GetMapping("/list")
    public ResponseEntity<List<ContestResponse>> viewContestList(){
        return ResponseEntity.ok(contestService.viewContestList());
    }
}
