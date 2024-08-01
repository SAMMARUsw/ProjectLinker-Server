package com.sammaru.projectlinker.domain.contest.domain;

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
public class Contest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contest_id")
    private Long contestId;

    @Column(name = "title")
    private String title;

    @Column(name = "company")
    private String company;

    @Column(name = "image_url")
    private String imageUrl;

    public static Contest createContest(String title, String company, String imageUrl){
        return Contest.builder()
                .title(title)
                .company(company)
                .imageUrl(imageUrl)
                .build();
    }
}
