package com.ash;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class KeywordRouter {

    private static String [][] keywordList;
    private static String[] stringMethodList;

    public static void locateAndExecuteKeywords() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        //Start a cycle to iterate over the keywords which were fetched from Excel test case file
        for (int j = 0; j < XLSFileReader.numberOfKeywords; j++) {

            //System.out.println("Number of keywords to process : " + XLSFileReader.numberOfKeywords);
            //Check if keyword name from Excel is found in KeywordActions
            if ( (Arrays.toString(stringMethodList).matches("(?i).*" + keywordList[3][j + 1] + ".*"))) {
                System.out.println("Processing keyword: " + keywordList[3][j + 1]);

                    //If implementation is found - start one more cycle to identify particular Java method within KeywordActions
                    for (String aStringMethodList : stringMethodList) {

                        //Find a particular method in KeywordActions which corresponds to keyword from Excel
                        if (aStringMethodList.contains(keywordList[3][j + 1])) {

                            //Check if keyword name from Excel is having Run flag =Y
                            //if (keywordList[2][j + 1] == "Y") {

                                //Get the indexes of parts of full method name represented by String i.e. "public static someMethod (java.Lang.String)"
                                int nameMatchIndex = aStringMethodList.indexOf(keywordList[3][j + 1]);
                                int signatureParamPartStartIndex = aStringMethodList.indexOf("(java");

                                //Get exact method name to pass it to reflection mechanism for invocation
                                String method = aStringMethodList.substring(nameMatchIndex, signatureParamPartStartIndex);

                                //If 1 param is  passed from Excel - invoke method
                                if (/*keywordList[2][j + 1] == "Y" && */keywordList[4][j + 1] != "" && keywordList[5][j + 1] == "" && keywordList[6][j + 1] == "") {

                                    Method methodToInvoke = KeywordActions.class.getDeclaredMethod(method, String.class);
                                    System.out.println("Executing method: " + methodToInvoke.toString() + " with arguments: " + keywordList[4][j + 1]);
                                    methodToInvoke.invoke(null, (keywordList[4][j + 1]));


                                    //If 2 params are passed from excel - invoke method
                                } else if (/*keywordList[2][j + 1] == "Y" && */keywordList[4][j + 1] != "" && keywordList[5][j + 1] != "" && keywordList[6][j + 1] == "") {

                                    Method methodToInvoke = KeywordActions.class.getDeclaredMethod(method, String.class, String.class);
                                    System.out.println("Executing method: " + methodToInvoke.toString() + " with arguments: " + keywordList[4][j + 1] + " ; " + keywordList[5][j + 1]);
                                    methodToInvoke.invoke(null, (keywordList[4][j + 1]), (keywordList[5][j + 1]));


                                    //If 3 params are passed from excel - invoke method
                                } else if (/*keywordList[2][j + 1] == "Y" && */keywordList[4][j + 1] != "" && keywordList[5][j + 1] != "" && keywordList[6][j + 1] != "") {

                                    Method methodToInvoke = KeywordActions.class.getDeclaredMethod(method, String.class, String.class, String.class);
                                    System.out.println("Executing method: " + methodToInvoke.toString() + " with arguments: " + keywordList[4][j + 1] + " ; " + keywordList[5][j + 1] + " ; " + keywordList[6][j + 1]);
                                    methodToInvoke.invoke(null, (keywordList[4][j + 1]), (keywordList[5][j + 1]), (keywordList[6][j + 1]));

                                /*
                                //If no params are passed from excel - invoke method
                                } else if (keywordList[4][j + 1] == "" && keywordList[5][j + 1] == "" && keywordList[6][j + 1] == "") {

                                    Method methodToInvoke = KeywordActions.class.getDeclaredMethod(method);
                                    System.out.println("Executing method: " + methodToInvoke.toString());
                                    methodToInvoke.invoke(null, null);
                                */

                            //}
                            //else {
                            //        System.out.println("Keyword :" + keywordList[3][j + 1] + " is set not to be run");
                            //    }

                        } else continue;
                    }
                }
            } else {
                System.out.println("Implementation of keyword :" + keywordList[3][j + 1] + " was not found or it is marked to be not executed");
            }
        }
    }

        //Read keywords and parameters from Excel file
        public static void readKeywords () {

            XLSFileReader reader = new XLSFileReader();
            reader.setInputFile("C:/Users/ASH/IdeaProjects/Selenium/src/main/resources/SampleTC.xls");

            try {
                keywordList = reader.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Read names of available methods within KeywordActions class
        public static void readActions () {

            Method[] methodList = KeywordActions.class.getDeclaredMethods();
            stringMethodList = new String[methodList.length];
            stringMethodList = methodsToStringArray(methodList, stringMethodList);

        }

        //Convert available method names from KeywordActions from Method to String format
        private static String[] methodsToStringArray (Method[]array1, String[]array2){
            for (int i = 0; i < array1.length; i++) {
                array2[i] = array1[i].toString();
            }
            return array2;
        }
    }