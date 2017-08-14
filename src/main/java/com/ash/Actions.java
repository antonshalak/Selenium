package com.ash;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;


public class Actions {


    public static WebDriver chrome;

    public static void openApp (String url) {

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        capabilities.setCapability("chrome.binary","C://Program Files (x86)//Google//Chrome//Application//chrome.exe");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);


        chrome = new ChromeDriver(capabilities);
        //chrome.get("http://google.com");
        String newurl = url;
        chrome.get(newurl);

    }

    public static void testClass1 (String text, String text2) {

        String newtext = text;
        String newtext2 = text2;

        System.out.println(newtext+"  "+newtext2);

    }

    public void testClass2 (String url) {

        WebDriver chrome = new ChromeDriver();
        chrome.get(url);

    }


}
