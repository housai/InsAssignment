package com.klein.service;

import com.klein.model.User;

public interface UserService {
    User selectUserById(Integer userId);
	User selectUserByName(String Name);
    Integer insertUser(User user);
}
