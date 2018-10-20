package com.klein.service;

import com.klein.model.Like;
import com.klein.model.Post;

import java.util.ArrayList;
import java.util.Date;


public interface PostService {
    Post selectPostById(Integer postId);
    ArrayList<Post> selectPostByUserId(Integer userId);
    ArrayList<Post> selectPostByTime(Date time);
    ArrayList<Post> selectPostByLocation(String location);
    ArrayList<Post> selectAllPost();
    Integer insertPost(Post post);
}
