package com.sammaru.projectlinker.domain.application.service;

import com.sammaru.projectlinker.domain.application.domain.Application;
import com.sammaru.projectlinker.domain.application.domain.ApplicationStatus;
import com.sammaru.projectlinker.domain.application.payload.request.CheckProjectApplyRequest;
import com.sammaru.projectlinker.domain.application.payload.request.CreateApplicationRequest;
import com.sammaru.projectlinker.domain.application.payload.response.ProjectApplyResponse;
import com.sammaru.projectlinker.domain.application.payload.response.ProjectMemberResponse;
import com.sammaru.projectlinker.domain.application.repository.ApplicationRepository;
import com.sammaru.projectlinker.domain.application.util.ProjectApplyResponseConverter;
import com.sammaru.projectlinker.domain.project.domain.Project;
import com.sammaru.projectlinker.domain.project.exception.AlreadyDeletedException;
import com.sammaru.projectlinker.domain.project.exception.UnAuthorizationException;
import com.sammaru.projectlinker.domain.project.repository.ProjectRepository;
import com.sammaru.projectlinker.domain.user.domain.User;
import com.sammaru.projectlinker.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public void createApplication(Long userId, Long projectId, CreateApplicationRequest request){
        Application application = applicationRepository.save(
                Application.create(
                        request.message(),
                        userId,
                        projectId
                )
        );

        log.info("Create Application Post ID: {}", application.getApplicationId());
    }

    public void checkProjectApply(CheckProjectApplyRequest request, Long applicationId, Long userId){
        Application target = applicationRepository.findByApplicationIdAndIsDeletedFalse(applicationId)
                .orElseThrow(()-> new NoSuchElementException("요청에 대한 응답을 찾을 수 없습니다."));
        if (target.isDeleted()) {
            log.info("Failure User for checkProjectApply USER_ID: {}", userId);
            throw new AlreadyDeletedException("이미 삭제된 리뷰입니다.");
        }

        if (!target.getUserId().equals(userId)) {
            log.info("UnAuthorization for check project apply USER_ID: {}", userId);
            throw new UnAuthorizationException("접근 권한이 없습니다.");
        }

        target.checkApply(request.status());

        Project targetProject = projectRepository.findByProjectIdAndIsDeletedFalse(target.getProjectId())
                .orElseThrow(() -> new NoSuchElementException());

        targetProject.incCurrentNum();

        log.info("Check Project Apply APPLICATION_ID: {}", target.getApplicationId());
    }

    public List<ProjectApplyResponse> viewProjectApply(Long projectId){
        List<Application> result = applicationRepository.findAllByProjectIdAndIsDeletedFalse(projectId);
        return result.stream()
                .map(ProjectApplyResponseConverter::from)
                .collect(Collectors.toList());
    }

    public List<ProjectApplyResponse> viewMyApply(Long userId){
        List<Application> result = applicationRepository.findAllByUserIdAndIsDeletedFalse(userId);
        return result.stream()
                .map(ProjectApplyResponseConverter::from)
                .collect(Collectors.toList());
    }

    public List<ProjectMemberResponse> viewProjectMembers(Long projectId){
        List<Application> result = applicationRepository.findByProjectIdAndStatusAndIsDeletedFalse(projectId, ApplicationStatus.ACCEPT);
        List<ProjectMemberResponse> resultResponse = new ArrayList<>();
        for(Application target : result){
            User targetUser = userRepository.findByUserIdAndIsDeletedIsFalse(target.getUserId())
                    .orElseThrow(()-> new NoSuchElementException());
            resultResponse.add(ProjectMemberResponse.builder()
                            .name(targetUser.getName())
                            .email(targetUser.getEmail())
                    .build());
        }
        return resultResponse;
    }


}
