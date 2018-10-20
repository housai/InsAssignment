package com.klein.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    private Integer postId;
    private String photourl;
    private Integer userId;
    private String location;
    private Date time;
    private String content;
    public Post() {
    }

    public Post(String photourl, Integer userId, String location, String content) {
        this.photourl = photourl;
        this.userId = userId;
        this.location = location;
        this.content = content;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(this.time);
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
