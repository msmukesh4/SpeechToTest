package com.example.speechtotest.util;

/**
 * Created by mukesh on 05/02/19
 */
public class StringUtils {

    public static String replaceAppText(String s){

        s = s.replace("first", "1st");
        s = s.replace("second", "2nd");
        s = s.replace("third", "3rd");
        s = s.replace("fourth", "4th");
        s = s.replace("xx", "Twenty");

        return s;
    }


}
