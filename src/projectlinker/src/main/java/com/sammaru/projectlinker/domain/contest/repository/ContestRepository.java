package com.sammaru.projectlinker.domain.contest.repository;

import com.sammaru.projectlinker.domain.contest.domain.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {
    List<Contest> findTop10ByOrderByCreatedAtDesc();
}
