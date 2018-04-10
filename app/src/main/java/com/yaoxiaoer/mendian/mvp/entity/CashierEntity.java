package com.yaoxiaoer.mendian.mvp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chenwy on 2018/2/26.
 */

public class CashierEntity implements Serializable {
    private static final long serialVersionUID = 4806565356817229995L;
    public List<NickNameList> nicknameList;

    public static class NickNameList implements Serializable{
        private static final long serialVersionUID = 7556461386334357566L;
        public String u_nickname;
        public String u_id;
    }
}
