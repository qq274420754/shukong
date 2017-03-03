package com.lzm.shukong.modul.bean.unsplash;

/**
 * Created by Administrator on 2017/2/27.
 */

public class ImageListBean {

    /**
     * author : Alejandro Escamilla
     * author_url : https://unsplash.com/@alejandroescamilla
     * filename : 0000_yC-Yzbqy7PY.jpeg
     * format : jpeg
     * height : 3744
     * id : 0
     * post_url : https://unsplash.com/photos/yC-Yzbqy7PY
     * width : 5616
     */

    public String author;
    public String author_url;
    public String filename;
    public String format;
    public int height;
    public int id;
    public String post_url;
    public int width;

    @Override
    public String toString() {
        return "ImageListBean{" +
                "author='" + author + '\'' +
                ", author_url='" + author_url + '\'' +
                ", filename='" + filename + '\'' +
                ", format='" + format + '\'' +
                ", height=" + height +
                ", id=" + id +
                ", post_url='" + post_url + '\'' +
                ", width=" + width +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_url() {
        return author_url;
    }

    public void setAuthor_url(String author_url) {
        this.author_url = author_url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
