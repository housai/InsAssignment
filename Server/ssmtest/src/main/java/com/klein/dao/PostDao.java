package com.klein.dao;

import com.klein.model.Post;

import java.util.ArrayList;
import java.util.Date;

public interface PostDao {
    Post selectPostById(Integer postId);
    ArrayList<Post> selectPostByUserId(Integer userId);
    ArrayList<Post> selectPostByTime(Date time);
    ArrayList<Post> selectPostByLocation(String location);
    Integer insertPost(Post post);
    ArrayList<Post> selectAllPost();
}
