package com.klein.dao;

import com.klein.model.Like;

import java.util.ArrayList;

public interface LikeDao {
    Like selectLikeById(Integer postId);
    ArrayList<Like> selectLikeByName(String username);
    ArrayList<Like> selectLikeByPostId(Integer postId);
    ArrayList<Like> selectLikeByUserId(Integer postId);
    Integer insertLike(Like like);
}
