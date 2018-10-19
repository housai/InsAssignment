package com.klein.instagram.bean;

public class CommentBean {

    private Integer id;
    private Integer userId;
    private String username;
    private Integer postId;
    private String content;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public CommentBean(Integer userId, Integer postId, String username, String content) {
        this.userId = userId;
//        this.username = username;
        this.postId = postId;
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

