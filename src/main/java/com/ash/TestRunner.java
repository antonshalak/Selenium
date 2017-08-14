package com.ash;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;


public class TestRunner {

    static String [][] keywordList;
    //static Class clazz;
    static String[] stringMethodList;
    static Method[] methodList;


    public static void main (String [] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        System.setProperty("webdriver.chrome.driver", "C://Users//ASH//IdeaProjects//Selenium//chromedriver_win32//chromedriver.exe");
        readKeywords();
        int numberOfKeywords = XLSTestCaseReader.numberOfKeywords;
        readActions();

        //System.out.println(Arrays.toString(stringMethodList));
        //System.out.println(keywordList[3][1]);
        //System.out.println(keywordList[3][2]);
        //System.out.println(keywordList[3][3]);

        for (int j=0; j<numberOfKeywords; j++) {

            if (Arrays.toString(stringMethodList).matches("(?i).*"+keywordList[3][j+1]+".*")) {
                System.out.println("processing method: "+keywordList[3][j+1]);

                for (int c = 0; c < stringMethodList.length; c++) {

                    if (stringMethodList[c].contains(keywordList[3][j+1])) {

                        //System.out.println(stringMethodList[c]);
                        int matchIndex = stringMethodList[c].indexOf(keywordList[3][j+1]);
                        int firstIndex = stringMethodList[c].indexOf("(java");
                        int lastIndex = stringMethodList[c].indexOf(")");
                        //System.out.println(matchIndex);
                        //System.out.println(firstIndex);
                        //System.out.println(lastIndex);


                        //String params = stringMethodList[c].substring(firstIndex+1,lastIndex);
                        String method = stringMethodList[c].substring(matchIndex,firstIndex);



                        //System.out.println(method);
                        //System.out.println(params);

                        if (keywordList[4][j+1] != "" && keywordList[5][j+1] == "" && keywordList[6][j+1] == "") {

                            Method methodToInvoke = Actions.class.getDeclaredMethod(method, String.class);
                            System.out.println("Executing: "+methodToInvoke.toString());
                            //System.out.println("Running method: "+keywordList[3][j+1]);
                            methodToInvoke.invoke(null, (keywordList[4][j+1]));

                        }

                        else if (keywordList[4][j+1] != "" && keywordList[5][j+1] != "" && keywordList[6][j+1] == "") {

                            Method methodToInvoke = Actions.class.getDeclaredMethod(method, String.class, String.class);
                            System.out.println("Executing: "+methodToInvoke.toString());
                            //System.out.println("Running method: "+keywordList[3][j+1]);
                            methodToInvoke.invoke(null,  (keywordList[4][j+1]), (keywordList[5][j+1]));

                        }

                        else if (keywordList[4][j+1] != "" && keywordList[5][j+1] != "" && keywordList[6][j+1] != "") {

                            Method methodToInvoke = Actions.class.getDeclaredMethod(method, String.class, String.class, String.class);
                            System.out.println("Executing: "+methodToInvoke.toString());
                            //System.out.println("Running method: "+keywordList[3][j+1]);
                            methodToInvoke.invoke(null,  (keywordList[4][j+1]),  (keywordList[5][j+1]),  (keywordList[6][j+1]));

                        }

                    }
                    else continue;
                }
            }
            else {
                System.out.println("Method processing complete!");
            }
        }

        //ChromeDriver chrome = new ChromeDriver();

    }


    public static void readKeywords() {

        XLSTestCaseReader reader = new XLSTestCaseReader();
        reader.setInputFile("C:/Users/ASH/IdeaProjects/Selenium/src/TestCases/SampleTC.xls");

        try {
            keywordList = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void readActions() {

        TestRunner.methodList = Actions.class.getDeclaredMethods();
        stringMethodList = new String[methodList.length];
        stringMethodList = methodsToStringArray(methodList, stringMethodList);
        //System.out.println(stringMethodList[0]);

    }



    /*
    public static Method[] getAccessibleMethods(Class clazz) {
            List<Method> result = new ArrayList<Method>();

            while (clazz != null) {
                for (Method method : clazz.getDeclaredMethods()) {
                    int modifiers = method.getModifiers();
                    if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
                        result.add(method);
                    }
                }
                clazz = clazz.getSuperclass();
            }
            return result.toArray(new Method[result.size()]);
    }
    */


    public static String[] methodsToStringArray (Method[] array1, String[] array2) {
            Method[] methodList = array1;
            String[] stringMethodList = array2;
            for (int i = 0; i < methodList.length; i++) {
                stringMethodList[i] = methodList[i].toString();
            }
            return stringMethodList;
    }
}
