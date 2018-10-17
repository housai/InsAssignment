package com.klein.model;

import java.util.Date;

public class Post {
    private Integer postId;
    private Integer photoId;
    private Integer userId;
    private String location;
    private Date time;

    public Post() {
    }

    public Post(Integer photoId, Integer userId, String location) {
        this.photoId = photoId;
        this.userId = userId;
        this.location = location;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
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
