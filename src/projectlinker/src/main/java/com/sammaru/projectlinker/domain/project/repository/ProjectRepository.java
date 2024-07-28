package com.sammaru.projectlinker.domain.project.repository;

import com.sammaru.projectlinker.domain.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByIsDeletedFalse();
    Optional<Project> findByProjectIdAndIsDeletedFalse(Long projectId);
}
