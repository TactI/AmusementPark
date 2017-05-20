package com.example.xiaoli.amusementpark.entity;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.entity
 *    文件名：    PalacePriceInfo
 *    创建者：    XiaoLi
 *    创建时间：  2017/5/20  14:52
 *    描述：       景点价格详情
 */

public class PalacePriceInfo {
    private int resultCode;
    private String message;
    private PalaceBean datas;
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

    public PalaceBean getDatas() {
        return datas;
    }

    public void setDatas(PalaceBean datas) {
        this.datas = datas;
    }


    public class PalaceBean{
        private String palace_name;
        private String palace_location;
        private String img_url;
        private String introduce_url;
        private String palace_adult_morning;
        private String palace_adult_night;
        private String palace_student_morning;
        private String palace_child_morning;
        private String adult_morning_price;
        private String adult_night_price;
        private String student_morning_price;
        private String child_morning_price;
        public String getPalace_name() {
            return palace_name;
        }

        public void setPalace_name(String palace_name) {
            this.palace_name = palace_name;
        }

        public String getPalace_location() {
            return palace_location;
        }

        public void setPalace_location(String palace_location) {
            this.palace_location = palace_location;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getIntroduce_url() {
            return introduce_url;
        }

        public void setIntroduce_url(String introduce_url) {
            this.introduce_url = introduce_url;
        }

        public String getPalace_adult_morning() {
            return palace_adult_morning;
        }

        public void setPalace_adult_morning(String palace_adult_morning) {
            this.palace_adult_morning = palace_adult_morning;
        }

        public String getPalace_adult_night() {
            return palace_adult_night;
        }

        public void setPalace_adult_night(String palace_adult_night) {
            this.palace_adult_night = palace_adult_night;
        }

        public String getPalace_student_morning() {
            return palace_student_morning;
        }

        public void setPalace_student_morning(String palace_student_morning) {
            this.palace_student_morning = palace_student_morning;
        }

        public String getPalace_child_morning() {
            return palace_child_morning;
        }

        public void setPalace_child_morning(String palace_child_morning) {
            this.palace_child_morning = palace_child_morning;
        }

        public String getAdult_morning_price() {
            return adult_morning_price;
        }

        public void setAdult_morning_price(String adult_morning_price) {
            this.adult_morning_price = adult_morning_price;
        }

        public String getAdult_night_price() {
            return adult_night_price;
        }

        public void setAdult_night_price(String adult_night_price) {
            this.adult_night_price = adult_night_price;
        }

        public String getStudent_morning_price() {
            return student_morning_price;
        }

        public void setStudent_morning_price(String student_morning_price) {
            this.student_morning_price = student_morning_price;
        }

        public String getChild_morning_price() {
            return child_morning_price;
        }

        public void setChild_morning_price(String child_morning_price) {
            this.child_morning_price = child_morning_price;
        }


    }
}
