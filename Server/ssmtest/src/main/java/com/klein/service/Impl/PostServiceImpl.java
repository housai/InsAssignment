package com.klein.service.Impl;

import com.klein.dao.PostDao;
import com.klein.model.Post;
import com.klein.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao PostDao;

    public Post selectPostById(Integer postId) {
        return PostDao.selectPostById(postId);
    }

    public ArrayList<Post> selectPostByUserId(Integer userId) {
        return PostDao.selectPostByUserId(userId);
    }

    public ArrayList<Post> selectPostByTime(Date time) {
        return PostDao.selectPostByTime(time);
    }

    public ArrayList<Post> selectPostByLocation(String location) {
        return PostDao.selectPostByLocation(location);
    }

    public Integer insertPost(Post post) {
        return PostDao.insertPost(post);
    }
}
