package com.lzl_rjkx.doctor.utils;

/**
 * Created by lzl_os on 16/3/17.
 */
public class StringUtils {

    public final static String getString(String str) {

        return str.replace("<br>", "").replace("</br>", "").replace("<p>", "").replace("</p>", "");
    }
}
