package com.sammaru.projectlinker.domain.project.service;

import com.sammaru.projectlinker.domain.project.domain.Project;
import com.sammaru.projectlinker.domain.project.exception.AlreadyDeletedException;
import com.sammaru.projectlinker.domain.project.exception.UnAuthorizationException;
import com.sammaru.projectlinker.domain.project.payload.request.CreateProjectRequest;
import com.sammaru.projectlinker.domain.project.payload.request.EditProjectRequest;
import com.sammaru.projectlinker.domain.project.payload.response.ViewProjectListResponse;
import com.sammaru.projectlinker.domain.project.payload.response.ViewProjectResponse;
import com.sammaru.projectlinker.domain.project.repository.ProjectRepository;
import com.sammaru.projectlinker.domain.project.util.ViewProjectResponseConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    public void createProject(CreateProjectRequest request, Long userId){
        projectRepository.save(
                Project.create(
                        request.title(),
                        request.content(),
                        userId,
                        request.status(),
                        request.vacanctNum(),
                        request.currentNum()
                )
        );
    }
    public ViewProjectResponse viewProject(Long projectId){
        Project result = projectRepository.findByProjectIdAndIsDeletedFalse(projectId)
                .orElseThrow(() -> new NoSuchElementException("요청에 대한 응답을 찾을 수 없습니다."));
        return ViewProjectResponseConverter.fromProject(result);
    }
    public List<ViewProjectListResponse> viewProjectList(){
        List<Project> result = projectRepository.findByIsDeletedFalse();
        return result.stream()
                .map(ViewProjectResponseConverter::from)
                .collect(Collectors.toList());
    }
    public void editProject(EditProjectRequest request, Long projectId, Long userId){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("요청에 대한 응답을 찾을 수 없습니다."));
        if (project.isDeleted()) {
            throw new AlreadyDeletedException("이미 삭제된 리뷰입니다.");
        }

        if (!project.getUserId().equals(userId)) {
            throw new UnAuthorizationException("접근 권한이 없습니다.");
        }

        project.editProject(
                request.title(),
                request.content(),
                userId,
                request.status(),
                request.vacancyNum(),
                request.vacancyNum()
        );
    }
    public void deleteProject(Long projectId, Long userId){
        Project project = projectRepository.findById(projectId).orElseThrow(()
                -> new NoSuchElementException("요청에 대한 응답을 찾을 수 없습니다."));
        if (!project.getUserId().equals(userId)){
            throw new UnAuthorizationException("접근 권한이 없습니다.");
        }
        if (project.isDeleted()){
            throw new AlreadyDeletedException("이미 삭제된 글 입니다.");
        }

        project.delete();
    }
}
