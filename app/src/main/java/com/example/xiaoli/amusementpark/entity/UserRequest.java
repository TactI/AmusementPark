package com.example.xiaoli.amusementpark.entity;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.entity
 *    文件名：    UserRequest
 *    创建者：    XiaoLi
 *    创建时间：  2017/5/10  16:24
 *    描述：       用户数据
 */

public class UserRequest {
    private int resultCode;
    private String message;
    private UserBean datas;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserBean getDatas() {
        return datas;
    }

    public void setDatas(UserBean datas) {
        this.datas = datas;
    }
    public class UserBean {
        private String user_id;
        private String user_name;
        private String user_password;
        private String user_number;
        private String  real_name;

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_password() {
            return user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

        public String getUser_number() {
            return user_number;
        }

        public void setUser_number(String user_number) {
            this.user_number = user_number;
        }

        private String nick_name;
        private String icon_url;

        public String getUser_name() {
        return user_name;
    }

        public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

        public String getNick_name() {
        return nick_name;
    }

        public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

        public String getIcon_url() {
             return icon_url;
        }

        public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }
    }
}
