package com.ash;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class KeywordRouter {

    static String [][] keywordList;
    static String[] stringMethodList;
    static Method[] methodList;

    public static void locateAndExecuteKeywords() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        //Start a cycle to iterate over the keywords which were fetched from Excel test case file
        for (int j = 0; j < XLSTestCaseReader.numberOfKeywords; j++) {

            //Check if keyword name from Excel is found in KeywordActions at all
            if (Arrays.toString(stringMethodList).matches("(?i).*" + keywordList[3][j + 1] + ".*")) {
                System.out.println("Processing keyword: " + keywordList[3][j + 1]);

                //If implementation is found - start one more cycle to identify particular Java method within KeywordActions
                for (int c = 0; c < stringMethodList.length; c++) {

                    //Find a particular method in KeywordActions which corresponds to keyword from Excel
                    if (stringMethodList[c].contains(keywordList[3][j + 1])) {

                        //Get the indexes of parts of full method name represented by String i.e. "public static someMethod (java.Lang.String)"
                        int nameMatchIndex = stringMethodList[c].indexOf(keywordList[3][j + 1]);
                        int signatureParamPartStartIndex = stringMethodList[c].indexOf("(java");

                        //Get exact method name to pass it to reflection mechanism for invocation
                        String method = stringMethodList[c].substring(nameMatchIndex, signatureParamPartStartIndex);

                        //If 1 param is  passed from Excel - invoke method
                        if (keywordList[4][j + 1] != "" && keywordList[5][j + 1] == "" && keywordList[6][j + 1] == "") {

                            Method methodToInvoke = KeywordActions.class.getDeclaredMethod(method, String.class);
                            System.out.println("Executing method: " + methodToInvoke.toString());
                            methodToInvoke.invoke(null, (keywordList[4][j + 1]));

                        //If 2 params are passed from excel - invoke method
                        } else if (keywordList[4][j + 1] != "" && keywordList[5][j + 1] != "" && keywordList[6][j + 1] == "") {

                            Method methodToInvoke = KeywordActions.class.getDeclaredMethod(method, String.class, String.class);
                            System.out.println("Executing method: " + methodToInvoke.toString());
                            methodToInvoke.invoke(null, (keywordList[4][j + 1]), (keywordList[5][j + 1]));

                        //If 3 params are passed from excel - invoke method
                        } else if (keywordList[4][j + 1] != "" && keywordList[5][j + 1] != "" && keywordList[6][j + 1] != "") {

                            Method methodToInvoke = KeywordActions.class.getDeclaredMethod(method, String.class, String.class, String.class);
                            System.out.println("Executing method: " + methodToInvoke.toString());
                            methodToInvoke.invoke(null, (keywordList[4][j + 1]), (keywordList[5][j + 1]), (keywordList[6][j + 1]));

                        //If no params are passed from excel - invoke method
                        } else if (keywordList[4][j + 1] == "" && keywordList[5][j + 1] == "" && keywordList[6][j + 1] == "") {

                            Method methodToInvoke = KeywordActions.class.getDeclaredMethod(method, null);
                            System.out.println("Executing method: " + methodToInvoke.toString());
                            methodToInvoke.invoke(null, null);

                        }

                    } else continue;
                }
            } else {
                System.out.println("Implementation of keyword :" + keywordList[3][j + 1] + "was not found");
            }
        }
    }

        //Read keywords and parameters from Excel file
        public static void readKeywords () {

            XLSTestCaseReader reader = new XLSTestCaseReader();
            reader.setInputFile("C:/Users/ASH/IdeaProjects/Selenium/src/TestCases/SampleTC.xls");

            try {
                keywordList = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Read names of available methods within KeywordActions class
        public static void readActions () {

            methodList = KeywordActions.class.getDeclaredMethods();
            stringMethodList = new String[methodList.length];
            stringMethodList = methodsToStringArray(methodList, stringMethodList);

        }

        //Convert available method names from KeywordActions from Method to String format
        public static String[] methodsToStringArray (Method[]array1, String[]array2){
            Method[] methodList = array1;
            String[] stringMethodList = array2;
            for (int i = 0; i < methodList.length; i++) {
                stringMethodList[i] = methodList[i].toString();
            }
            return stringMethodList;
        }
    }