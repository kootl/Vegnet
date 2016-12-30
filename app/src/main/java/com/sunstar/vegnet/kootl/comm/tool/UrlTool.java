package com.sunstar.vegnet.kootl.comm.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by louisgeek on 2016/12/8.
 */

public class UrlTool {
    public static String encodeUrl(String str) {
        try {
            str = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
