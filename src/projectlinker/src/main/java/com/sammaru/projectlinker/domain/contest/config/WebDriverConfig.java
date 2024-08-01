package com.sammaru.projectlinker.domain.contest.config;

import org.openqa.selenium.chrome.ChromeDriverService;
import org.springframework.context.annotation.Configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@Configuration
@Scope("singleton")
public class WebDriverConfig {

    @Bean
    public WebDriver webDriver() {
        // Set the path to your ChromeDriver executable
        ChromeDriverService service = new ChromeDriverService.Builder().build();
        // Configure ChromeOptions if needed
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run Chrome in headless mode
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.20 Safari/537.36");

        // Create and return a new instance of ChromeDriver
        return new ChromeDriver(service,options);
    }
}
