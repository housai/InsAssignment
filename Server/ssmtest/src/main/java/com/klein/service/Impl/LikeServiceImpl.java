package com.klein.service.Impl;

import com.klein.dao.LikeDao;
import com.klein.model.Like;
import com.klein.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeDao likeDao;
    public Like selectLikeById(Integer likeId) {
        return likeDao.selectLikeById(likeId);
    }

    public ArrayList<Like> selectLikeByName(String username) {
        return likeDao.selectLikeByName(username);
    }

    public ArrayList<Like> selectLikeByPostId(Integer postId) {
        return likeDao.selectLikeByPostId(postId);
    }

    public ArrayList<Like> selectLikeByUserId(Integer userId) {
        return likeDao.selectLikeByUserId(userId);
    }

    public Integer insertLike(Like like) {
        return likeDao.insertLike(like);
    }
    public ArrayList<Like> checkLike(Integer userId, Integer postId) {
        return likeDao.checkLike(userId,postId);
    }
}
