package com.klein.instagram.bean;

public class ActivityFeedBean {
    private Integer followerId;
    private String followerUsername;
    private Integer likeCount;

    public ActivityFeedBean(Integer followerId, String followerUsername, Integer likeCount) {
        this.followerId = followerId;
        this.followerUsername = followerUsername;
        this.likeCount = likeCount;
    }

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public String getFollowerUsername() {
        return followerUsername;
    }

    public void setFollowerUsername(String followerUsername) {
        this.followerUsername = followerUsername;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}
