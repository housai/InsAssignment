package com.klein.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klein.dao.UserDao;
import com.klein.model.User;
import com.klein.service.UserService;

@Service 
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	public User selectUserById(Integer userId) {
		return userDao.selectUserById(userId);

	}

	public User selectUserByName(String Name) {
		return userDao.selectUserByName(Name);
	}

	public Integer insertUser(User user) {
		return userDao.insertUser(user);
	}

}
