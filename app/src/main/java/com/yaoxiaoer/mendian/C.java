package com.yaoxiaoer.mendian;

/**
 * Created by Chenwy on 2018/2/1.
 */

public class C {
    //内网1
//    public static final String BASE_URL = "http://192.168.40.91:8081/";
//    public static final String BASE_URL = "http://1x7448h712.iok.la/";
//    public static final String BASE_URL = "http://192.168.40.82:8080/";
    //内网2
//    public static final String BASE_URL = " http://192.168.60.233:8888/";
    //外网测试地址
    public static final String BASE_URL = "http://183.6.163.226:8888/";
    //域名正式地址
//    public static final String BASE_URL = "http://zf.yxejk.cn";

    public static final int PAGE_SIZE = 15;
    public static final String IS_LOGIN = "is_login";
    public static final String NEW_ORDER = "new_order";


    //百度语音合成
    //旧的appid：11149254
    public static final String BAIDU_SPEECH_APP_ID = "11395873";
    //旧的apikey：R9SkKAjFlBAo9GGi7P962YiW
    public static final String BAIDU_SPEECH_API_KEY = "syjDcSR1gvGv95XhiBV9c9yG";
    //旧的secretkey：d770298903dbddab367e4e71346feced
    public static final String BAIDU_SPEECH_SECRET_KEY = "9RMcKLa5s5WpviFytODDUGZxeae7RsmC";


    public static String RADOM_NUM;
    public static int USER_ID;
    public static int STORE_ID;

    public static final int CODE_HTTP_EXCEPTION = -1;
    public static final int CODE_OTHER_EXCEPTION = -2;
    public static final int CODE_NO_NETWORK_EXCEPTION = -3;
    public static final int CODE_CONNECT_EXCEPTION = -4;

    public static final int CODE_SUCCESS = 200;
    /**
     * 反扫时需要支付密码
     */
    public static final int CODE_NEED_PAY_PASSWORD = 405;
}
