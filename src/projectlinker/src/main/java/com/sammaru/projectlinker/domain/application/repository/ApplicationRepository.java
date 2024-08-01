package com.sammaru.projectlinker.domain.application.repository;

import com.sammaru.projectlinker.domain.application.domain.Application;
import com.sammaru.projectlinker.domain.application.domain.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByApplicationIdAndIsDeletedFalse(Long applicatioinId);
    List<Application> findAllByProjectIdAndIsDeletedFalse(Long projectId);
    List<Application> findAllByUserIdAndIsDeletedFalse(Long userId);
    List<Application> findByProjectIdAndStatusAndIsDeletedFalse(Long projectId, ApplicationStatus applicationStatus);
}
