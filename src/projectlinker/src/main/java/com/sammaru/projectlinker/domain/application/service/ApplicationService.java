package com.sammaru.projectlinker.domain.application.service;

import com.sammaru.projectlinker.domain.application.domain.Application;
import com.sammaru.projectlinker.domain.application.payload.request.CheckProjectApplyRequest;
import com.sammaru.projectlinker.domain.application.payload.request.CreateApplicationRequest;
import com.sammaru.projectlinker.domain.application.payload.response.ProjectApplyResponse;
import com.sammaru.projectlinker.domain.application.repository.ApplicationRepository;
import com.sammaru.projectlinker.domain.application.util.ProjectApplyResponseConverter;
import com.sammaru.projectlinker.domain.project.exception.AlreadyDeletedException;
import com.sammaru.projectlinker.domain.project.exception.UnAuthorizationException;
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
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public void createApplication(Long userId, Long projectId, CreateApplicationRequest request){
        applicationRepository.save(
                Application.create(
                        request.message(),
                        userId,
                        projectId
                )
        );
    }

    public void checkProjectApply(CheckProjectApplyRequest request, Long applicationId, Long userId){
        Application target = applicationRepository.findByApplicationIdAndIsDeletedFalse(applicationId)
                .orElseThrow(()-> new NoSuchElementException("요청에 대한 응답을 찾을 수 없습니다."));
        if (target.isDeleted()) {
            throw new AlreadyDeletedException("이미 삭제된 리뷰입니다.");
        }

        if (!target.getUserId().equals(userId)) {
            throw new UnAuthorizationException("접근 권한이 없습니다.");
        }

        target.checkApply(request.status());
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


}
