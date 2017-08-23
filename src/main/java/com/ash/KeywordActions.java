package com.ash;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class KeywordActions {


    private static ChromeDriver chrome;


    public static void openApp (String url) {

        //Running chrome driver process

        ChromeDriverService.Builder builder = new
                ChromeDriverService.Builder();
        ChromeDriverService srvc = builder.usingDriverExecutable(new
                File("C://Users//ASH//IdeaProjects//Selenium//chromedriver_win32//chromedriver.exe"))
                .usingPort(65423).build();
        try {
            srvc.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        chrome = new ChromeDriver(srvc);
        chrome.get(url);
        chrome.manage().window().maximize();
    }

    public static void searchJobsByLocAndTitle (String jobTitle, String jobLocation) throws InterruptedException {

        //Setting explicit wait
        WebDriverWait wait = new WebDriverWait(chrome, 50);

        //Clearing up and setting Title search criteria
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='keyword']")));
        chrome.findElement(By.xpath("//*[@id='keyword']")).clear();
        chrome.findElement(By.xpath("//*[@id='keyword']")).sendKeys(jobTitle);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='loc_placeholder']")));
        chrome.findElement(By.xpath("//*[@id='loc_placeholder']")).click();

        //Checking if any locations are already selected and clearing them
        List<WebElement> selectedLocations = chrome.findElements(By.className("search-choice-close"));
        if  (selectedLocations.size() != 0) {
            for (WebElement searchChoiceClose : selectedLocations) {
                searchChoiceClose.click();
            }
        }

        //Setting Location search criteria
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='chzn-results']/li")));
        List<WebElement> options = chrome.findElements(By.xpath("//ul[@class='chzn-results']/li"));
        for (WebElement option : options) {
            if (((option.getText()).matches("(?i).*" + jobLocation + ".*"))) {
                option.click();
            }
        }

        //Hitting Search button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='jSearchSubmit']")));
        chrome.findElement(By.xpath("//*[@id='jSearchSubmit']")).click();

        //Waiting for search results to load
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='job_search_filters']")));
        }
        catch (Exception e) {
            System.out.println("Page was not loaded properly after search or markup has changed");
            e.printStackTrace();
        }

        //Checking the loaded results
        if (chrome.findElements(By.partialLinkText(jobTitle)).size() !=0) {
            System.out.println(chrome.findElement(By.className("number_of_results")).getText() + " found in '" +
                    jobLocation + "' location for '" + jobTitle + "' job title");
        }
        else {
            System.out.println("No results found in " + jobLocation + " for " + jobTitle + " job title");
        }

    }

    public static void searchJobsByTitle (String jobTitle) throws InterruptedException {

        //Setting explicit wait
        WebDriverWait wait = new WebDriverWait(chrome, 50);

        //Clearing up and setting Title search criteria
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='keyword']")));
        chrome.findElement(By.xpath("//*[@id='keyword']")).clear();
        chrome.findElement(By.xpath("//*[@id='keyword']")).sendKeys(jobTitle);

        //Clearing up Location search  criteria if any
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='loc_placeholder']")));
        chrome.findElement(By.xpath("//*[@id='loc_placeholder']")).click();

        List<WebElement> selectedLocations = chrome.findElements(By.className("search-choice-close"));

        if  (selectedLocations.size() != 0) {
            for (WebElement searchChoiceClose : selectedLocations) {
                searchChoiceClose.click();
            }
        }

        //Hitting Search button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='jSearchSubmit']")));
        chrome.findElement(By.xpath("//*[@id='jSearchSubmit']")).click();

        //Waiting for page to load search results
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='job_search_filters']")));
        }
        catch (Exception e) {
            System.out.println("Page was not loaded properly after search or markup has changed");
            e.printStackTrace();
        }

        //Checking search results
        if (chrome.findElements(By.partialLinkText(jobTitle)).size() !=0) {
            System.out.println(chrome.findElement(By.className("number_of_results")).getText() + " found" +
                    " for '" + jobTitle + "' job title");
        }
        else {
            System.out.println("No results found for '" + jobTitle + "' job title");
        }

        chrome.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    public static void exitApp () {
        chrome.close();
    }
}