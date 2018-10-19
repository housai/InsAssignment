package com.klein.controller;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.klein.model.Like;
import com.klein.service.LikeService;
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
	
	@Autowired
    private LikeService likeService;
	
	@ResponseBody
	@RequestMapping(value = "/selectUserByName", method = RequestMethod.POST)
    public String getIndex (HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
	    String username = request.getParameter("username");
        User user = userService.selectUserByName(username);
        if (user != null){
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
    @RequestMapping(value = "/suggestUserByLike", method = RequestMethod.POST)
    public String suggestUserByLike (HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        String username = request.getParameter("username");
        System.out.println(username);
        User user = userService.selectUserByName(username);
        ArrayList<User> suggestedUsers = new ArrayList<User>();
        if (user != null){
//            the like list of posts liked by this user
            ArrayList<Like> likeArrayList = likeService.selectLikeByName(username);
            for (Like like:
                 likeArrayList) {
//                the like list of same post liked by all users
                ArrayList<Like> likeArrayList1 = likeService.selectLikeByPostId(like.getPostId());
                for (Like like1 :
                        likeArrayList1) {
                    if (like1.getUserId() != like.getUserId()){
                        suggestedUsers.add(userService.selectUserById(like1.getUserId()));
                    }
                }
            }
            map.put("resultCode", 200);
            map.put("user", user);
            map.put("data", suggestedUsers);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","user exists");
        }
        System.out.println(JSON.toJSONString(map));
        return JSON.toJSONString(map);
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registUser (HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.selectUserByName(userName);
        if (user == null){
            User newUser = new User(userName,password);
            userService.insertUser(newUser);
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
        if (user != null && password != null) {
            if (password.contentEquals(user.getPassword())) {
                map.put("resultCode", 200);
                map.put("msg", "login success");
                map.put("user", user);
                return JSON.toJSONString(map);
            }

        }
        map.put("resultCode",400);
        map.put("msg","login fail");
        return JSON.toJSONString(map);

    }

}
