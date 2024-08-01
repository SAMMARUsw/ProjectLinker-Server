package com.sammaru.projectlinker.domain.application.controller;

import com.sammaru.projectlinker.domain.application.payload.request.CheckProjectApplyRequest;
import com.sammaru.projectlinker.domain.application.payload.request.CreateApplicationRequest;
import com.sammaru.projectlinker.domain.application.payload.response.ProjectApplyResponse;
import com.sammaru.projectlinker.domain.application.payload.response.ProjectMemberResponse;
import com.sammaru.projectlinker.domain.application.service.ApplicationService;
import com.sammaru.projectlinker.global.component.token.TokenResolver;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apply/api/v1")
@RequiredArgsConstructor
@Validated
public class ApplicatioinController {
    private final ApplicationService applicationService;
    private final TokenResolver tokenResolver;

    @Tag(name = "지원서 작성", description = "지원서를 작성하는 api. AccessToken 필요, {ProjectId}는 지원서를 작성하고자 하는 모집글의 id")
    @PostMapping("/{projectId}")
    public ResponseEntity<Void> createApplication(@PathVariable @Valid Long projectId,
                                                  @RequestBody @Valid CreateApplicationRequest request,
                                                  @RequestHeader("Authorization") String token){
        Long userId = tokenResolver.getAccessClaims(token);
        applicationService.createApplication(userId, projectId, request);
        return ResponseEntity.ok().build();
    }

    @Tag(name = "지원서 심사", description = "지원서를 심사하는 api. 수락 혹은 거절 입력을 통해 해당 지원서를" +
            "모집하는 팀장이 심사함. AccessToken 필요, {applicationId}는 심사하고자 하는 지원서의 id")
    @PostMapping("/{applicatioinId}/check")
    public ResponseEntity<Void> checkProjectApply(@PathVariable @Valid Long applicatioinId,
            @RequestBody @Valid CheckProjectApplyRequest request, @RequestHeader("Authorization") String token){
        Long userId = tokenResolver.getAccessClaims(token);
        applicationService.checkProjectApply(request, applicatioinId, userId);
        return ResponseEntity.ok().build();
    }

    @Tag(name = "해당 모집글의 지원서 조회", description = "해당 모집글의 지원서를 조회하는 api.")
    @GetMapping("/{projectId}")
    public ResponseEntity<List<ProjectApplyResponse>> viewProjectApply(@PathVariable @Valid Long projectId){
        return ResponseEntity.ok(applicationService.viewProjectApply(projectId));
    }

    @Tag(name = "내가 쓴 지원서 목록 조회", description = "내가 작성한 지원서 목록을 조회하는 api. AccessToken 필요")
    @GetMapping("/me")
    public ResponseEntity<List<ProjectApplyResponse>> viewMyApply(@RequestHeader("Authorization") String token){
        Long userId = tokenResolver.getAccessClaims(token);
        return ResponseEntity.ok(applicationService.viewMyApply(userId));
    }

    @GetMapping("/{projectId}/member")
    public ResponseEntity<List<ProjectMemberResponse>> viewProjectMembers(@PathVariable @Valid Long projectId){
        return ResponseEntity.ok(applicationService.viewProjectMembers(projectId));
    }

}
