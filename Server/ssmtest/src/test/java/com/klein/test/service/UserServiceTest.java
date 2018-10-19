package com.klein.test.service;

import org.junit.Test;  
import org.springframework.beans.factory.annotation.Autowired;

import com.klein.model.User;
import com.klein.service.UserService;
import com.klein.test.SpringTestCase;

public class UserServiceTest extends SpringTestCase {

    @Autowired  
    private UserService userService; 

    @Test  
    public void selectUserByIdTest(){  
        User user = userService.selectUserById(1);  
        System.out.println(user.getUsername() + ":" + user.getPassword());
    }  
}
