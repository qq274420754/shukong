package com.lzm.shukong.modul.bean.meipai;

/**
 * Created by Administrator on 2017/2/18.
 */

public class MeipaiBean {
    /**
     * id : 10000000002
     * user_id : 100000
     * url : http://www.meipai.com/media/10000000002
     * cover_pic : http://mvimg1.meitudata.com/xxxxxxxxx.jpg!thumb320
     * screen_name : meipai02
     * caption : 美拍开放平台
     * avatar : http://mvavatar4.meitudata.com/xxxxxxxxx.jpg
     * plays_count : 100
     * comments_count : 100
     * likes_count : 100
     * created_at : 1459307395
     */

    public long id;
    public int user_id;
    public String url;
    public String cover_pic;
    public String screen_name;
    public String caption;
    public String avatar;
    public int plays_count;
    public int comments_count;
    public int likes_count;
    public int created_at;

    @Override
    public String toString() {
        return "MeipaiBean{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", url='" + url + '\'' +
                ", cover_pic='" + cover_pic + '\'' +
                ", screen_name='" + screen_name + '\'' +
                ", caption='" + caption + '\'' +
                ", avatar='" + avatar + '\'' +
                ", plays_count=" + plays_count +
                ", comments_count=" + comments_count +
                ", likes_count=" + likes_count +
                ", created_at=" + created_at +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPlays_count() {
        return plays_count;
    }

    public void setPlays_count(int plays_count) {
        this.plays_count = plays_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }
}
