package com.sunstar.vegnet.data;

/**
 * Created by louisgeek on 2016/12/6.
 */

public class ApiUrls {
    public static final String SHOWAPI_APPID = "26722";
    public static final String SHOWAPI_SIGN_SIMPLE = "bd0f855549aa4bd6b39cb06af9e41541";

    public static String newsChannelUrl = "http://route.showapi.com/109-34";
    public static String newsListUrl = "http://route.showapi.com/109-35";

    public static String jokeGifImagesListUrl="http://route.showapi.com/341-3";
    public static String jokeImagesListUrl="http://route.showapi.com/341-2";

    /**
     * Version=2&LoginID=gxb1
     * &Password=96E79218965EB72C92A549DD5A330112
     * &RoleType=1
     */
    public static String baseUrl = "http://api.lvseeds.com:8001/lvseeds/";
    public static String userLoginUrl = String.format("%s%s", baseUrl, "User/SearchLoginApp");

}
