package com.jiajun.demo.moudle.homepage.entities;

import java.util.List;

/**
 * Created by dan on 2017/11/20/020.
 */

public class GuideImagesBean {

    /**
     * count : 1
     * images : [{"imagePath":"http://localhost:8080/upload/newfile20160612172757875.png"}]
     */

    private String count;
    private List<ImagesBean> images;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
        /**
         * imagePath : http://localhost:8080/upload/newfile20160612172757875.png
         */

        private String imagePath;

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }
    }
}
