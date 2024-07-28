package com.sammaru.projectlinker.domain.project.controller;

import com.sammaru.projectlinker.domain.project.payload.request.CreateProjectRequest;
import com.sammaru.projectlinker.domain.project.payload.request.EditProjectRequest;
import com.sammaru.projectlinker.domain.project.payload.response.ViewProjectListResponse;
import com.sammaru.projectlinker.domain.project.payload.response.ViewProjectResponse;
import com.sammaru.projectlinker.domain.project.service.ProjectService;
import com.sammaru.projectlinker.global.component.token.TokenResolver;
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

    @PostMapping
    public ResponseEntity<Void> createProject(@RequestHeader("Authorization") String token
            ,@RequestBody @Valid CreateProjectRequest request){
        Long userId = tokenResolver.getAccessClaims(token);
        projectService.createProject(request, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Void> editProject(@RequestHeader("Authorization") String token
            , @PathVariable @Valid Long projectId, @RequestBody @Valid EditProjectRequest request){
        Long userId = tokenResolver.getAccessClaims(token);
        projectService.editProject(request, projectId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<ViewProjectListResponse>> viewProjectList(){
        return ResponseEntity.ok(projectService.viewProjectList());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ViewProjectResponse> viewProject(@PathVariable @Valid Long projectId){
        return ResponseEntity.ok(projectService.viewProject(projectId));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@RequestHeader("Authorization") String token
            , @PathVariable @Valid Long projectId){
        Long userId = tokenResolver.getAccessClaims(token);
        projectService.deleteProject(projectId, userId);
        return ResponseEntity.ok().build();
    }

}
