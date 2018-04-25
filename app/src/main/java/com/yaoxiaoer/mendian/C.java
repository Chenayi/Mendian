package com.yaoxiaoer.mendian;

/**
 * Created by Chenwy on 2018/2/1.
 */

public class C {
    public static final String BASE_URL = "http://192.168.40.233:8080/";
    //内网
//    public static final String BASE_URL = " http://192.168.60.233:8888/";
    //外网
//    public static final String BASE_URL = "http://183.6.163.226:8888";
    public static final int PAGE_SIZE = 15;
    public static final String IS_LOGIN = "is_login";
    public static final String NEW_ORDER = "new_order";

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
