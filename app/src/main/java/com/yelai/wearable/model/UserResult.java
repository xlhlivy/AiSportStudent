package com.yelai.wearable.model;

import java.util.List;

/**
 * Created by wanglei on 2016/12/10.
 */

public class UserResult extends BaseModel {

    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public boolean isNull() {
        return false;
    }

    public static class User {

        private String memberId;
        private String mobile;
        private String nickName;
        private String headImg;
        private String isDone;

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getIsDone() {
            return isDone;
        }

        public void setIsDone(String isDone) {
            this.isDone = isDone;
        }

        @Override
        public String toString() {
            return "User{" +
                    "memberId='" + memberId + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", headImg='" + headImg + '\'' +
                    ", isDone='" + isDone + '\'' +
                    '}';
        }
    }

}
