package com.steve.router.processor;

/**
 * Created by SteveYan on 2017/12/27.
 */

public class StringUtil {

    public static boolean isEmpty(String s) {
        if (s == null) {
            return true;
        }
        return s.length() == 0;
    }

}
