package com.ash;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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



    static String extentReportFile = System.getProperty("user.dir")
            + "\\reports\\extentReportFile.html";
    /*static String extentReportImage = System.getProperty("user.dir")
            + "\\extentReportImage.png";*/

    // Create object of extent report and specify the report file path.
    static ExtentReports extent = new ExtentReports(extentReportFile, false);

    // Start the test using the ExtentTest class object.
    static ExtentTest extentTest = extent.startTest("Keywords test",
            "Keywords test");

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

        extentTest.log(LogStatus.INFO, "Browser Launched");
        extentTest.log(LogStatus.PASS, "Navigated to " + url);

        chrome.manage().window().maximize();
    }

    public static void searchJobsByLocAndTitle (String jobTitle, String jobLocation) throws InterruptedException {

        //Setting explicit wait
        WebDriverWait wait = new WebDriverWait(chrome, 50);

        //Clearing up and setting Title search criteria
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='keyword']")));
        chrome.findElement(By.xpath("//*[@id='keyword']")).clear();
        chrome.findElement(By.xpath("//*[@id='keyword']")).sendKeys(jobTitle);

        extentTest.log(LogStatus.INFO, "Job value title set to "+ jobTitle);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='loc_placeholder']")));
        chrome.findElement(By.xpath("//*[@id='loc_placeholder']")).click();

        //Checking if any locations are already selected and clearing them
        List<WebElement> selectedLocations = chrome.findElements(By.className("search-choice-close"));
        if  (selectedLocations.size() != 0) {
            for (WebElement searchChoiceClose : selectedLocations) {
                searchChoiceClose.click();
            }
            extentTest.log(LogStatus.INFO, "Location selections cleared");
        }

        //Setting Location search criteria
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='chzn-results']/li")));
        List<WebElement> options = chrome.findElements(By.xpath("//ul[@class='chzn-results']/li"));
        for (WebElement option : options) {
            if (((option.getText()).matches("(?i).*" + jobLocation + ".*"))) {
                option.click();
                extentTest.log(LogStatus.INFO, "Location selections added : " + jobLocation);
            }
        }

        //Hitting Search button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='jSearchSubmit']")));
        chrome.findElement(By.xpath("//*[@id='jSearchSubmit']")).click();

        //Waiting for search results to load
        try {
            chrome.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='jSearchSubmit']")));
            extentTest.log(LogStatus.INFO, "Search results loaded");
        }
        catch (Exception e) {
            System.out.println("Page was not loaded properly after search or markup has changed");
            extentTest.log(LogStatus.INFO,"Error Snapshot : " + extentTest.addScreenCapture(takeScreenshot()));
            e.printStackTrace();


        }

        //Checking the loaded results
        if (chrome.findElements(By.partialLinkText(jobTitle)).size() !=0) {
            System.out.println(chrome.findElement(By.className("number_of_results")).getText() + " found in '" +
                    jobLocation + "' location for '" + jobTitle + "' job title");

            extentTest.log(LogStatus.PASS,"Search results found : " + extentTest.addScreenCapture(takeScreenshot()));

        }
        else {
            System.out.println("No results found in " + jobLocation + " for " + jobTitle + " job title");
            extentTest.log(LogStatus.PASS,"No search results found : " + extentTest.addScreenCapture(takeScreenshot()));
        }
    }



    public static void searchJobsByTitle (String jobTitle) throws InterruptedException {

        //Setting explicit wait
        WebDriverWait wait = new WebDriverWait(chrome, 50);

        //Clearing up and setting Title search criteria
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='keyword']")));
        chrome.findElement(By.xpath("//*[@id='keyword']")).clear();
        chrome.findElement(By.xpath("//*[@id='keyword']")).sendKeys(jobTitle);

        extentTest.log(LogStatus.INFO, "Job value title set to "+ jobTitle);

        //Clearing up Location search  criteria if any
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='loc_placeholder']")));
        chrome.findElement(By.xpath("//*[@id='loc_placeholder']")).click();

        List<WebElement> selectedLocations = chrome.findElements(By.className("search-choice-close"));

        if  (selectedLocations.size() != 0) {
            for (WebElement searchChoiceClose : selectedLocations) {
                searchChoiceClose.click();
            }
            extentTest.log(LogStatus.INFO, "Location selections cleared");
        }

        //Hitting Search button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='jSearchSubmit']")));
        chrome.findElement(By.xpath("//*[@id='jSearchSubmit']")).click();

        //Waiting for page to load search results
        try {
            chrome.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='jSearchSubmit']")));
            extentTest.log(LogStatus.INFO, "Search results loaded");
        }
        catch (Exception e) {
            System.out.println("Page was not loaded properly after search or markup has changed");
            e.printStackTrace();
            extentTest.log(LogStatus.INFO,"Error Snapshot : " + extentTest.addScreenCapture(takeScreenshot()));
        }

        //Checking search results
        if (chrome.findElements(By.partialLinkText(jobTitle)).size() !=0) {
            System.out.println(chrome.findElement(By.className("number_of_results")).getText() + " found" +
                    " for '" + jobTitle + "' job title");
            extentTest.log(LogStatus.PASS,"Search results found : " + extentTest.addScreenCapture(takeScreenshot()));
        }
        else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='job_search_filters']")));
            System.out.println("No results found for '" + jobTitle + "' job title");
            extentTest.log(LogStatus.PASS,"No search results found : " + extentTest.addScreenCapture(takeScreenshot()));
        }

        chrome.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    public static String takeScreenshot (){

        File src = ((TakesScreenshot)chrome).getScreenshotAs(OutputType.FILE);
        Long currentTimeMillis = System.currentTimeMillis();

        try {


            // now copy the  screenshot to desired location using copyFile //method

            FileUtils.copyFile(src, new File(System.getProperty("user.dir")
                    + "\\reports\\images\\extentReportImage_" + currentTimeMillis + ".png"));

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        String extentReportImage = System.getProperty("user.dir")
                + "\\reports\\images\\extentReportImage_" + currentTimeMillis + ".png";
        return extentReportImage;

    }

    public static void exitApp () {

        chrome.close();
        extentTest.log(LogStatus.INFO, "Browser closed");
        // close report.
        extent.endTest(extentTest);
        // writing everything to document.
        extent.flush();
    }
}