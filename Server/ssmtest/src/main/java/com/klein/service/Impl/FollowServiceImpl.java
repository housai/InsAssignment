package com.klein.service.Impl;

import com.klein.dao.FollowDao;
import com.klein.dao.LikeDao;
import com.klein.model.Follow;
import com.klein.model.Like;
import com.klein.service.FollowService;
import com.klein.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowDao followDao;


    public Follow selectFollowById(Integer followId) {
        return followDao.selectFollowById(followId);
    }

    public ArrayList<Follow> selectFollowByUserId(Integer userId) {
        return followDao.selectFollowByUserId(userId);
    }

    public ArrayList<Follow> selectFollowByFollwedId(Integer followedId) {
        return followDao.selectFollowByFollwedId(followedId);
    }

    public Integer insertFollow(Follow follow) {
        return followDao.insertFollow(follow);
    }
}
