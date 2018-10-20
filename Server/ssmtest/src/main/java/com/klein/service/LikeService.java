package com.klein.service;

import com.klein.model.Like;

import java.util.ArrayList;

public interface LikeService {
    Like selectLikeById(Integer likeId);
    ArrayList<Like> selectLikeByName(String username);
    ArrayList<Like> selectLikeByPostId(Integer postId);
    ArrayList<Like> selectLikeByUserId(Integer userId);
    Integer insertLike(Like like);
    ArrayList<Like> checkLike(Integer userId, Integer postId);
}
