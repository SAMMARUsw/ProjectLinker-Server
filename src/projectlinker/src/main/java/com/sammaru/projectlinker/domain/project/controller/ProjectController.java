package com.sammaru.projectlinker.domain.project.controller;

import com.sammaru.projectlinker.domain.project.payload.request.CreateProjectRequest;
import com.sammaru.projectlinker.domain.project.payload.request.EditProjectRequest;
import com.sammaru.projectlinker.domain.project.payload.response.ViewProjectListResponse;
import com.sammaru.projectlinker.domain.project.payload.response.ViewProjectResponse;
import com.sammaru.projectlinker.domain.project.service.ProjectService;
import com.sammaru.projectlinker.global.component.token.TokenResolver;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/api/v1")
@RequiredArgsConstructor
@Validated
public class ProjectController {
    private final ProjectService projectService;
    private final TokenResolver tokenResolver;

    @Tag(name = "프로젝트 모집글 생성", description = "프로젝트 모집글 생성 api, AccessToken 필요")
    @PostMapping
    public ResponseEntity<Void> createProject(@RequestHeader("Authorization") String token
            ,@RequestBody @Valid CreateProjectRequest request){
        Long userId = tokenResolver.getAccessClaims(token);
        projectService.createProject(request, userId);
        return ResponseEntity.ok().build();
    }

    @Tag(name = "프로젝트 모집글 수정", description = "프로젝트 모집글 수정 api, AccessToken 필요")
    @PutMapping("/{projectId}")
    public ResponseEntity<Void> editProject(@RequestHeader("Authorization") String token
            , @PathVariable @Valid Long projectId, @RequestBody @Valid EditProjectRequest request){
        Long userId = tokenResolver.getAccessClaims(token);
        projectService.editProject(request, projectId, userId);
        return ResponseEntity.ok().build();
    }

    @Tag(name = "프로젝트 모집글 리스트 조회", description = "프로젝트 모집글 리스트 조회 api")
    @GetMapping()
    public ResponseEntity<List<ViewProjectListResponse>> viewProjectList(){
        return ResponseEntity.ok(projectService.viewProjectList());
    }

    @Tag(name = "프로젝트 모집글 상세 조회", description = "프로젝트 모집글 상제 조회 api (한 게시글만 조회)")
    @GetMapping("/{projectId}")
    public ResponseEntity<ViewProjectResponse> viewProject(@PathVariable @Valid Long projectId){
        return ResponseEntity.ok(projectService.viewProject(projectId));
    }

    @Tag(name = "나의 프로젝트 모집글 조회", description = "자신이 작성한 프로젝트 모집글을 조회하는 api, AccessToken 필요")
    @GetMapping("/me")
    public ResponseEntity<List<ViewProjectListResponse>> viewMyProject(@RequestHeader("Authorization") String token){
        Long userId = tokenResolver.getAccessClaims(token);
        return ResponseEntity.ok(projectService.viewMyProjectList(userId));
    }

    @Tag(name = "프로젝트 모집글 삭제", description = "자신이 작성한 프로젝트 모집글을 삭제하는 api, AccessToken 필요")
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@RequestHeader("Authorization") String token
            , @PathVariable @Valid Long projectId){
        Long userId = tokenResolver.getAccessClaims(token);
        projectService.deleteProject(projectId, userId);
        return ResponseEntity.ok().build();
    }

}
