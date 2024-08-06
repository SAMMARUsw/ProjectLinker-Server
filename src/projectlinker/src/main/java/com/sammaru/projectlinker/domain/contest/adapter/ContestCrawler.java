package com.sammaru.projectlinker.domain.contest.adapter;

import com.sammaru.projectlinker.domain.contest.config.WebDriverConfig;
import com.sammaru.projectlinker.domain.contest.payload.CrawlingResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContestCrawler {

    private final WebDriverConfig webDriverConfig;

    @Value("${crawling.url}")
    private String baseUrl;
    @Value("${crawling.script.scroll}")
    private String windowScroll;
    @Value("${crawling.target.title}")
    private String titleTarget;
    @Value("${crawling.target.company}")
    private String companyTarget;
    @Value("${crawling.target.image}")
    private String imageTarget;
    @Value("${crawling.attribute.image}")
    private String imageAttribute;

    public List<CrawlingResult> crawlingContest() {

        WebDriver webDriver = new WebDriverConfig().webDriver();

        List<CrawlingResult> contestList = new ArrayList<>();
        try {
            log.info("Try Crawling Task");

            webDriver.get(baseUrl);

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(titleTarget)));

            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript(windowScroll, "");

            List<WebElement> titleElement = webDriver.findElements(By.cssSelector(titleTarget));

            List<WebElement> companyElements = webDriver.findElements(By.cssSelector(companyTarget));

            List<WebElement> imageElements = webDriver.findElements(By.cssSelector(imageTarget));

            for (int i = 0; i < titleElement.size(); i++) {
                String title = titleElement.get(i).getText();
                String company = companyElements.size() > i ? companyElements.get(i).getText() : "N/A";
                String imageUrl = imageElements.size() > i ? imageElements.get(i).getAttribute(imageAttribute) : "N/A";

                CrawlingResult crawlingResult = new CrawlingResult(title, company, imageUrl);
                contestList.add(crawlingResult);
            }
            log.info("Finish Crawling Task");

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Crawling Failure");
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }

        return contestList;
    }
}
