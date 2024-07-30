package com.sammaru.projectlinker.domain.application.controller;

import com.sammaru.projectlinker.domain.application.payload.request.CheckProjectApplyRequest;
import com.sammaru.projectlinker.domain.application.payload.request.CreateApplicationRequest;
import com.sammaru.projectlinker.domain.application.payload.response.ProjectApplyResponse;
import com.sammaru.projectlinker.domain.application.service.ApplicationService;
import com.sammaru.projectlinker.global.component.token.TokenResolver;
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

    @PostMapping("/{projectId}")
    public ResponseEntity<Void> createApplication(@PathVariable @Valid Long projectId,
                                                  @RequestBody @Valid CreateApplicationRequest request,
                                                  @RequestHeader("Authorization") String token){
        Long userId = tokenResolver.getAccessClaims(token);
        applicationService.createApplication(userId, projectId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{applicatioinId}/check")
    public ResponseEntity<Void> checkProjectApply(@PathVariable @Valid Long applicatioinId,
            @RequestBody @Valid CheckProjectApplyRequest request, @RequestHeader("Authorization") String token){
        Long userId = tokenResolver.getAccessClaims(token);
        applicationService.checkProjectApply(request, applicatioinId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<List<ProjectApplyResponse>> viewProjectApply(@PathVariable @Valid Long projectId){
        return ResponseEntity.ok(applicationService.viewProjectApply(projectId));
    }

    @GetMapping("/{userId}/me")
    public ResponseEntity<List<ProjectApplyResponse>> viewMyApply(@PathVariable @Valid Long userId){
        return ResponseEntity.ok(applicationService.viewMyApply(userId));
    }

}
