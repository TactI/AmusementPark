package com.example.xiaoli.amusementpark.entity;
/*
 *    项目名：    AmusementPark
 *    包名：      com.example.xiaoli.amusementpark.entity
 *    文件名：    Palace
 *    创建者：    XiaoLi
 *    创建时间：  2017/5/15  13:53
 *    描述：       TODO.
 */

import java.util.List;

public class Palace {
    private String resultCode;
    private List<palace> datas;
    public static class palace{
        private String palace_name;
        private String palace_desc;
        private String location;
        private String video_title;
        private String img_url;
        private String video_url;
        private String video_image;
        private String palace_price;
        private String palace_opentime;

        public String getPalace_opentime() {
            return palace_opentime;
        }

        public void setPalace_opentime(String palace_opentime) {
            this.palace_opentime = palace_opentime;
        }

        public String getPalace_name() {
            return palace_name;
        }

        public void setPalace_name(String palace_name) {
            this.palace_name = palace_name;
        }

        public String getPalace_desc() {
            return palace_desc;
        }

        public void setPalace_desc(String palace_desc) {
            this.palace_desc = palace_desc;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getVideo_title() {
            return video_title;
        }

        public void setVideo_title(String video_title) {
            this.video_title = video_title;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getVideo_image() {
            return video_image;
        }

        public void setVideo_image(String video_image) {
            this.video_image = video_image;
        }

        public String getPalace_price() {
            return palace_price;
        }

        public void setPalace_price(String palace_price) {
            this.palace_price = palace_price;
        }

    }
    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public List<palace> getDatas() {
        return datas;
    }

    public void setDatas(List<palace> datas) {
        this.datas = datas;
    }
}
