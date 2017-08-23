package com.ash;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class KeywordRunner {

    public static void main (String [] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        //Setting chrome driver location
        System.setProperty("webdriver.chrome.driver", "C://Users//ASH//IdeaProjects//Selenium//chromedriver_win32//chromedriver.exe");

        //Read keywords and params from Excel test case file
        KeywordRouter.readKeywords();

        //Read method names for keywords which are implemented within KeywordActions class
        KeywordRouter.readActions();

        //Execute keywords
        KeywordRouter.locateAndExecuteKeywords();

        //Exit chrome upon end of execution
        KeywordActions.exitApp();
    }
}
