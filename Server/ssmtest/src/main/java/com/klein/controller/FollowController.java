package com.klein.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.klein.model.Follow;
import com.klein.model.Like;
import com.klein.model.Post;
import com.klein.model.User;
import com.klein.service.FollowService;
import com.klein.service.LikeService;
import com.klein.service.PostService;
import com.klein.service.UserService;
import com.klein.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;


@Controller
@RequestMapping(value = "/FollowController")
public class FollowController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @ResponseBody
    @RequestMapping(value = "/selectFollowById", method = RequestMethod.POST)
    public String selectFollowById (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String followId = request.getParameter("followId");
        Map<String, Object> map = Maps.newHashMap();
        Follow follow = followService.selectFollowById(Integer.parseInt(followId));
        if (follow != null){
            map.put("resultCode",200);
            map.put("data",follow);
            return JSON.toJSONString(map);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
            return JSON.toJSONString(map);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectFollowByUserId", method = RequestMethod.POST)
    public String selectFollowByUserId (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Follow> followArrayList = followService.selectFollowByUserId(Integer.parseInt(userId));
        if (followArrayList != null){
            ArrayList<User> users = new ArrayList<User>();
            for (Follow follow :
                    followArrayList) {
                users.add(userService.selectUserById(follow.getFollowedId()));
            }
            map.put("resultCode",200);
            map.put("data",users);
            return JSON.toJSONString(map);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
            return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectFollowByFollowedId", method = RequestMethod.POST)
    public String selectFollowByFollowedId (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String followedId = request.getParameter("followedId");
        System.out.println(followedId);
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Follow> followArrayList = followService.selectFollowByFollwedId(Integer.parseInt(followedId));
        if (followArrayList != null){
            ArrayList<User> users = new ArrayList<User>();
            for (Follow follow :
                    followArrayList) {
                users.add(userService.selectUserById(follow.getUserId()));

            }
            map.put("resultCode",200);
            map.put("data",users);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");

        }
        return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
    }

    @ResponseBody
    @RequestMapping(value = "/getFollowActivityByUserId", method = RequestMethod.POST)
    public String getFollowActivityByUserId (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Follow> followArrayList = followService.selectFollowByUserId(Integer.parseInt(userId));
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<Map> result = new ArrayList<Map>();
        if (followArrayList != null){
            for (Follow follow:followArrayList) {
                users.add(userService.selectUserById(follow.getFollowedId()));
            }

            for (User user :
                    users) {
                Map<String, Object> userMap = Maps.newHashMap();
                userMap.put("username", user.getUsername());
                userMap.put("userId", user.getId());
                userMap.put("profilephoto", user.getProfilephoto());
                int likeCount = likeService.selectLikeByUserId(user.getId()).size();
                userMap.put("likeCount", likeCount);
                result.add(userMap);
            }

        }
        if (result != null){
            map.put("resultCode",200);
            map.put("data",result);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
        }
        return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
    }

    @ResponseBody
    @RequestMapping(value = "/selectFollowPostByUserId", method = RequestMethod.POST)
    public String selectFollowPostByUserId (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Follow> followArrayList = followService.selectFollowByUserId(Integer.parseInt(userId));
        ArrayList<Post> posts = new ArrayList<Post>();
        ArrayList<Post> postArrayList = postService.selectPostByUserId(Integer.parseInt(userId));
        posts.addAll(postArrayList);
        if (followArrayList != null){
            for (Follow follow:followArrayList) {
                postArrayList = postService.selectPostByUserId(follow.getFollowedId());
                posts.addAll(postArrayList);
            }

        }
        if (posts != null){
            map.put("resultCode",200);
            map.put("data",posts);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
        }
        return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
    }

    @ResponseBody
    @RequestMapping(value = "/insertFollow", method = RequestMethod.POST)
    public String insertFollow (HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        String userId = request.getParameter("userId");
        String followedId = request.getParameter("followedId");
        ArrayList<Follow> followArrayList = followService.selectFollowByUserId(Integer.parseInt(userId));
        boolean alreadyFollowed = false;
        for (Follow follow :
                followArrayList) {
            if (Integer.parseInt(userId) == follow.getFollowedId()){
                alreadyFollowed = true;
            }
        }

        if (!alreadyFollowed && Integer.parseInt(userId) != Integer.parseInt(followedId)){
            Follow follow = new Follow(Integer.parseInt(userId), Integer.parseInt(followedId));
            int result = followService.insertFollow(follow);
            if (result ==1){
                map.put("resultCode",200);
                map.put("msg","success");
            }else {

                map.put("resultCode",400);
                map.put("msg","db failed");
            }
        }else {
            map.put("resultCode",400);
            map.put("msg","already followed");
        }
        return JSON.toJSONString(map);
    }



}
