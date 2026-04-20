package com.exppoints.fantasy;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;


public class Scraper {
    // scrape player odds from bettingpros
    public List<String> scrape(String player, String link) {

        if (!player.contains("-")) {
            return null;
        }

        // setup headless chrome driver
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");

        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=900,900");

        WebDriver driver = new ChromeDriver(options);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // scrape!
        try {
            driver.get(link + player + "/");
            Thread.sleep(5000);

            for (int i = 0; i < 150; i++) {
                js.executeScript("window.scrollBy(0, 10);");
                Thread.sleep(10);
            }

            List<WebElement> element = driver.findElements(By.cssSelector("span.typography.odds-cell__line, span.typography.odds-market-label, span.typography.odds-cell__cost"));

            List<String> odds = new java.util.ArrayList<>();
            for (int i = 0; i < element.size(); i++) {
                odds.add(element.get(i).getText());
            }
            
            return odds;
            
        } catch (InterruptedException e) {
        } finally {
            driver.quit();
        }

        return null;
    }

}