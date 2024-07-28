package com.sammaru.projectlinker.domain.project.domain;

import com.sammaru.projectlinker.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@DynamicUpdate
@Entity
public class Project extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "status", nullable = false)
    private ProjectStatus status;

    @Column(name = "vacancy_num", nullable = false)
    private Integer vacancyNum;

    @Column(name = "current_num", nullable = false)
    private Integer currentNum;

    public static Project create(String title, String content, Long userId,
                                 ProjectStatus status, Integer vacancyNum, Integer currentNum){
        return Project.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .status(status)
                .vacancyNum(vacancyNum)
                .currentNum(currentNum)
                .build();
    }

    public void editProject(String title, String content, Long userId,
                            ProjectStatus status, Integer vacancyNum, Integer currentNum){
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.status = status;
        this.vacancyNum = vacancyNum;
        this.currentNum = currentNum;
    }
}
