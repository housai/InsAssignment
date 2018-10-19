package com.klein.dao;

import com.klein.model.User;

public interface UserDao {
	User selectUserById(Integer userId);
	User selectUserByName(String Name);
	Integer insertUser(User user);
}
