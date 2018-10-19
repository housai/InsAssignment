package com.klein.service.Impl;

import com.klein.dao.CommentDao;
import com.klein.dao.PostDao;
import com.klein.model.Comment;
import com.klein.model.Post;
import com.klein.service.CommentService;
import com.klein.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    public Comment selectCommentById(Integer commentId) {
        return commentDao.selectCommentById(commentId);
    }

    public ArrayList<Comment> selectCommentByUser(Integer userId) {
        return commentDao.selectCommentByUser(userId);
    }

    public ArrayList<Comment> selectCommentByPost(Integer postId) {
        return commentDao.selectCommentByPost(postId);
    }

    public Integer insertComment(Comment comment) {
        return commentDao.insertComment(comment);
    }
}
