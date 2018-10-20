package com.klein.instagram.bean;

import java.util.Date;

public class Comment {
    private Integer commentId;
    private String content;
    private Integer userId;
    private Integer postId;

    public Comment() {
    }

    public Comment(String content, Integer userId, Integer postId) {
        this.content = content;
        this.userId = userId;
        this.postId = postId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
