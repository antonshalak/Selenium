package com.ash;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;


public class KeywordActions {


    public static WebDriver chrome;

    public static void openApp (String url) {

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        capabilities.setCapability("chrome.binary","C://Program Files (x86)//Google//Chrome//Application//chrome.exe");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

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
        String urlToRun = url;
        chrome.get(urlToRun);

    }

    public static void Search (String text, String text2) {

        String newtext = text;
        String newtext2 = text2;

        System.out.println(newtext+"  "+newtext2);

    }

    public void testClass2 (String url) {

        WebDriver chrome = new ChromeDriver();
        chrome.get(url);

    }


}
