package com.klein.service;

import com.klein.model.Follow;
import com.klein.model.Like;

import java.util.ArrayList;

public interface FollowService {
    Follow selectFollowById(Integer followId);
    ArrayList<Follow> selectFollowByUserId(Integer userId);
    Integer insertFollow(Follow follow);
}
