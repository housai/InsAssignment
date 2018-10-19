package com.klein.dao;

import com.klein.model.Follow;
import com.klein.model.Like;

import java.util.ArrayList;

public interface FollowDao {
    Follow selectFollowById(Integer followId);
    ArrayList<Follow> selectFollowByUserId(Integer userId);
    Integer insertFollow(Follow follow);
}
