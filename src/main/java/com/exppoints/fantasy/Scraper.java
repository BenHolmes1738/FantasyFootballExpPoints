package com.exppoints.fantasy;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Scraper {
    public List<String> scrape(String player) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.bettingpros.com/nfl/odds/player-futures/" + player + "/");
            Thread.sleep(5000);

            List<WebElement> tdElement = driver.findElements(By.cssSelector("span.typography.odds-cell__line, span.typography.odds-market-label"));
            
            List<String> odds = new java.util.ArrayList<>();
            //for (WebElement n:tdElement) {
            for (int i = 0; i < tdElement.size(); i++) {
                odds.add(tdElement.get(i).getText());
            }
            
            if (odds != null) return odds;
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return null;
    }

}