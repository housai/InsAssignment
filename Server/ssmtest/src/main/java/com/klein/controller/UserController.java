package com.klein.controller;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.klein.model.Follow;
import com.klein.model.Like;
import com.klein.service.FollowService;
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

	@Autowired
    private FollowService followService;

	@ResponseBody
	@RequestMapping(value = "/selectUserByName", method = RequestMethod.POST)
    public String selectUserByName (HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {
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
            return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/suggestUserByLike", method = RequestMethod.POST)
    public String suggestUserByLike (HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        String username = request.getParameter("username");
        String userId = request.getParameter("userId");
        User user = userService.selectUserByName(username);
        ArrayList<User> suggestedUsers = new ArrayList<User>();
        if (user != null){
            int isFollowing = 0;
            ArrayList<Follow> followArrayList1 = followService.selectFollowByUserId(Integer.parseInt(userId));
            for (Follow follow :
                    followArrayList1) {
                if (follow.getFollowedId() == user.getId()){
                    isFollowing = 1;
                    break;
                }
            }
           //the like list of posts liked by this user
            ArrayList<Like> likeArrayList = likeService.selectLikeByName(username);
            for (Like like:
                 likeArrayList) {
//                the like list of same post liked by all users
                ArrayList<Like> likeArrayList1 = likeService.selectLikeByPostId(like.getPostId());
                for (Like like1 :
                        likeArrayList1) {
                    if (like1.getUserId() != like.getUserId()){
                        User suggestedUser = userService.selectUserById(like1.getUserId());
                        ArrayList<Follow> followArrayList = followService.selectFollowByUserId(user.getId());
                        boolean alreadyFollowed = false;
                        for (Follow follow:
                             followArrayList) {
                            if (suggestedUser.getId() == follow.getFollowedId()){
                                alreadyFollowed = true;
                            }
                        }
                        if (!alreadyFollowed){
                            suggestedUsers.add(suggestedUser);

                        }
                    }
                }
            }

            map.put("resultCode", 200);
            map.put("user", user);
            map.put("data", suggestedUsers);
            map.put("isFollowing", isFollowing);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","user exists");
        }
        System.out.println(JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty));
        return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
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
            int result = userService.insertUser(newUser);
            if (result == 1){
                map.put("resultCode",200);
                map.put("msg","success");
            }else {
                map.put("resultCode",400);
                map.put("msg","failed to insert data to db");
            }

        }
        else {
            map.put("resultCode",400);
            map.put("msg","user exists");
        }

        return JSON.toJSONString(map);
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
        return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);

    }

}
