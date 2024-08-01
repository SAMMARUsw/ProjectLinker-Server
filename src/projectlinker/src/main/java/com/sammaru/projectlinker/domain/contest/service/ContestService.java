package com.sammaru.projectlinker.domain.contest.service;

import com.sammaru.projectlinker.domain.contest.domain.Contest;
import com.sammaru.projectlinker.domain.contest.payload.ContestResponse;
import com.sammaru.projectlinker.domain.contest.payload.CrawlingResult;
import com.sammaru.projectlinker.domain.contest.repository.ContestRepository;
import com.sammaru.projectlinker.domain.contest.adapter.ContestCrawler;
import com.sammaru.projectlinker.domain.contest.util.ContestResponseConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ContestService {
    private final ContestRepository contestRepository;
    private final ContestCrawler contestCrawler;

    @Value("${schedule.activate}")
    private boolean activateCrawling;

    @Scheduled(cron = "${schedule.cron}")
    public void createContest(){
        try {
            if(activateCrawling) {
                List<CrawlingResult> contestList = contestCrawler.crawlingContest();

                for (CrawlingResult crawlingResult : contestList) {
                    contestRepository.save(Contest.createContest(
                            crawlingResult.title(),
                            crawlingResult.company(),
                            crawlingResult.imageUrl()
                    ));
                }
            }
        } catch (Exception e){
            log.error("공모전 생성이 실패하였습니다.");
        }
    }

    public List<ContestResponse> viewContestList(){
        List<Contest> result = contestRepository.findTop10ByOrderByCreatedAtDesc();
        return result.stream()
                .map(ContestResponseConverter::from)
                .collect(Collectors.toList());
    }
}
