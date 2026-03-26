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
        // setup headless chrome driver
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");

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

            List<WebElement> tdElement = driver.findElements(By.cssSelector("span.typography.odds-cell__line, span.typography.odds-market-label, span.typography.odds-cell__cost"));
            
            List<String> odds = new java.util.ArrayList<>();
            //for (WebElement n:tdElement) {
            for (int i = 0; i < tdElement.size(); i++) {
                odds.add(tdElement.get(i).getText());
            }
            
            return odds;
            
        } catch (InterruptedException e) {
        } finally {
            driver.quit();
        }

        return null;
    }

}