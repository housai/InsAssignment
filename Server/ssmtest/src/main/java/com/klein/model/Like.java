package com.klein.model;

public class Like {
    private Integer likeId;
    private String username;
    private Integer postId;
    private Integer userId;

    public Like() {
    }

    public Like(String username, Integer postId, Integer userId) {
        this.username = username;
        this.postId = postId;
        this.userId = userId;
    }

    public Integer getLikeId() {
        return likeId;
    }

    public void setLikeId(Integer likeId) {
        this.likeId = likeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
