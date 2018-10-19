package com.klein.service;

import com.klein.model.Comment;
import com.klein.model.Post;

import java.util.ArrayList;
import java.util.Date;


public interface CommentService {
    Comment selectCommentById(Integer commentId);
    ArrayList<Comment> selectCommentByUser(Integer userId);
    ArrayList<Comment> selectCommentByPost(Integer postId);
    Integer insertComment(Comment comment);
}
