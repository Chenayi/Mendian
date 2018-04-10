package com.yaoxiaoer.mendian.mvp.entity;

import java.io.Serializable;

/**
 * Created by Chenwy on 2018/3/1.
 */

public class ApkInfoEntity implements Serializable {
    public Detail detail;

    public static class Detail implements Serializable {
        /**
         * id : 1
         * versionCode : 1
         * upgradeContent : 666
         * downloadUrl : 5
         * versionName : 1.0.0
         * packetSize : 6
         * isMust : 0
         * createtimeString : 2018-03-02 11:17:08
         */

        private int id;
        private String versionCode;
        private String upgradeContent;
        private String downloadUrl;
        private String versionName;
        private String packetSize;
        private String isMust;
        private String createtimeString;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getUpgradeContent() {
            return upgradeContent;
        }

        public void setUpgradeContent(String upgradeContent) {
            this.upgradeContent = upgradeContent;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getPacketSize() {
            return packetSize;
        }

        public void setPacketSize(String packetSize) {
            this.packetSize = packetSize;
        }

        public String getIsMust() {
            return isMust;
        }

        public void setIsMust(String isMust) {
            this.isMust = isMust;
        }

        public String getCreatetimeString() {
            return createtimeString;
        }

        public void setCreatetimeString(String createtimeString) {
            this.createtimeString = createtimeString;
        }
    }
}
