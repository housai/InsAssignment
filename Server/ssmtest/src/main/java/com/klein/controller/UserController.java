package com.klein.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.klein.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.klein.model.User;
import com.klein.service.UserService;

@Controller
@RequestMapping(value = "/UserController")
public class UserController {
	
	@Autowired
    private UserService userService; 
	
	@ResponseBody
	@RequestMapping(value = "/getUser", method = RequestMethod.POST)
    public String getIndex (HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {      
		String userId = request.getParameter("id");
        User user = userService.selectUserById(Integer.parseInt(userId)); 
        System.out.println(user.getUsername());
//        return user.getName(); 
        request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request,response);
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registUser (HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.selectUserByName(userName);
        //System.out.println(user.getUsername());
        if (user == null){
            User newUser = new User(userName,password);
            userService.insertUser(newUser);
            System.out.println(newUser.getId());
            map.put("resultCode",200);
            map.put("msg","success");
            return JSON.toJSONString(map);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","user exists");
            return JSON.toJSONString(map);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login (HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.selectUserByName(userName);
        if (user != null && (password.contentEquals(user.getPassword()))){
            map.put("resultCode",200);
            map.put("msg","login success");
            return JSON.toJSONString(map);
        }
        else{
            map.put("resultCode",400);
            map.put("msg","login fail");
            return JSON.toJSONString(map);
        }
    }

}
