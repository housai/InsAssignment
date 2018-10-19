package com.klein.instagram.bean;

public class FollowBean extends UserBean {
    private Integer followedNum;
    private Integer followingNum;

    public FollowBean(String username, String password, Integer id, Integer followedNum, Integer followingNum) {
        super(username, password);
        this.followedNum = followedNum;
        this.followingNum =followingNum;
    }

    public Integer getFollowedNum() {
        return followedNum;
    }

    public void setFollowedNum(Integer followedNum) {
        this.followedNum = followedNum;
    }


    public Integer getFollowingNum() {
        return followingNum;
    }

    public void setFollowingNum(Integer followingNum) {
        this.followingNum = followingNum;
    }
}