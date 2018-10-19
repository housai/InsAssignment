package com.klein.model;

public class Follow {
    private Integer followId;
    private Integer userId;
    private Integer followedId;

    public Follow() {
    }

    public Follow(Integer userId, Integer followedId) {
        this.userId = userId;
        this.followedId = followedId;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Integer followedId) {
        this.followedId = followedId;
    }
}
