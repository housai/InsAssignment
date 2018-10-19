package com.klein.instagram.bean;


/**
 * Created by Kaven Peng on 19/10/18
 */
public class ActivityFeedBean extends UserBean {
    private Integer likeCount;

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
}
