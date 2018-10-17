package com.klein.model;

import java.util.Date;

public class Post {
    private Integer postId;
    private String photourl;
    private Integer userId;
    private String location;
    private Date time;

    public Post() {
    }

    public Post(String photourl, Integer userId, String location) {
        this.photourl = photourl;
        this.userId = userId;
        this.location = location;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getphotourl() {
        return photourl;
    }

    public void setphotourl(String photourl) {
        this.photourl = photourl;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
