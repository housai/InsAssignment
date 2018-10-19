package com.klein.instagram.bean;


/**
 * Created by Kaven Peng on 19/10/18
 */
public class ActivityFeedBean extends UserBean {
    private Integer likeCount;
    private Boolean isFollower = false;

    public ActivityFeedBean(String username, String password, Integer id, Integer likeCount) {
        super(username, password);
        this.likeCount = likeCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Boolean getFollower() {
        return isFollower;
    }

    public void setFollower(Boolean follower) {
        isFollower = follower;
    }
}
