package com.yaoxiaoer.mendian.mvp.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Chenwy on 2018/2/5.
 */

public class User extends DataSupport implements Serializable {
    /**
     * u_status : 1
     * createTime : 2018-02-05 09:41:00
     * lastPage : true
     * loginTime : 2018-02-05 10:52:55
     * u_mobile : 13710094001
     * nextPage : 1
     * u_nickname : apptest
     * u_roldid : 2
     * pageSize : 10
     * u_id : 2
     * u_login_time : 1517799175551
     * u_linkman : 黄生
     * prePage : 1
     * u_create_time : 1517794860000
     * totalCount : 0
     * pageNo : 1
     * u_category : 2
     * firstPage : true
     * startRowNum : 0
     * randomNum : 5712405852034328
     * totalPage : 1
     * u_username : apptest
     */

    private long localCreateOrUpdateTime;
    private int u_status;
    private String createTime;
    private boolean lastPage;
    private String loginTime;
    private String u_mobile;
    private int nextPage;
    private String u_nickname;
    private int u_roldid;
    private int pageSize;
    private int u_id;
    private long u_login_time;
    private String u_linkman;
    private int prePage;
    private long u_create_time;
    private int totalCount;
    private int pageNo;
    private int u_category;
    private boolean firstPage;
    private int startRowNum;
    private String randomNum;
    private int totalPage;
    private String u_username;
    private int u_sid;

    public long getLocalCreateOrUpdateTime() {
        return localCreateOrUpdateTime;
    }

    public void setLocalCreateOrUpdateTime(long localCreateOrUpdateTime) {
        this.localCreateOrUpdateTime = localCreateOrUpdateTime;
    }

    public int getU_status() {
        return u_status;
    }

    public void setU_status(int u_status) {
        this.u_status = u_status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getU_mobile() {
        return u_mobile;
    }

    public void setU_mobile(String u_mobile) {
        this.u_mobile = u_mobile;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }

    public int getU_roldid() {
        return u_roldid;
    }

    public void setU_roldid(int u_roldid) {
        this.u_roldid = u_roldid;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public long getU_login_time() {
        return u_login_time;
    }

    public void setU_login_time(long u_login_time) {
        this.u_login_time = u_login_time;
    }

    public String getU_linkman() {
        return u_linkman;
    }

    public void setU_linkman(String u_linkman) {
        this.u_linkman = u_linkman;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public long getU_create_time() {
        return u_create_time;
    }

    public void setU_create_time(long u_create_time) {
        this.u_create_time = u_create_time;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getU_category() {
        return u_category;
    }

    public void setU_category(int u_category) {
        this.u_category = u_category;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public int getStartRowNum() {
        return startRowNum;
    }

    public void setStartRowNum(int startRowNum) {
        this.startRowNum = startRowNum;
    }

    public String getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(String randomNum) {
        this.randomNum = randomNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getU_username() {
        return u_username;
    }

    public void setU_username(String u_username) {
        this.u_username = u_username;
    }

    public int getU_sid() {
        return u_sid;
    }

    public void setU_sid(int u_sid) {
        this.u_sid = u_sid;
    }
}
