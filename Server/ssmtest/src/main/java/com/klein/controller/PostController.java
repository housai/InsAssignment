package com.klein.controller;

import com.alibaba.fastjson.JSON;
import com.klein.model.Post;
import com.klein.model.User;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;


@Controller
@RequestMapping(value = "/PostController")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/selectPostById", method = RequestMethod.POST)
    public String selectPostById (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String postId = request.getParameter("postId");
        System.out.println(postId);
        Map<String, Object> map = Maps.newHashMap();
        Post post = postService.selectPostById(Integer.parseInt(postId));
        if (post != null){
            map.put("resultCode",200);
            map.put("data",post);
            return JSON.toJSONString(map);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
            return JSON.toJSONString(map);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectPostByUserId", method = RequestMethod.POST)
    public String selectPostByUserId (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Post> postArrayList = postService.selectPostByUserId(Integer.parseInt(userId));
        if (postArrayList != null){
            map.put("resultCode",200);
            map.put("data", postArrayList);
            return JSON.toJSONString(map);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
            return JSON.toJSONString(map);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectPostByTime", method = RequestMethod.POST)
    public String selectLikeByName (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String time = request.getParameter("time");
        Map<String, Object> map = Maps.newHashMap();
        String nowTime = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(time);
        Timestamp date =Timestamp.valueOf(nowTime);
        ArrayList<Post> postArrayList = postService.selectPostByTime(date);
        if (postArrayList != null){
            map.put("resultCode",200);
            map.put("data", postArrayList);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
        }
        return JSON.toJSONString(map);
    }

    @ResponseBody
    @RequestMapping(value = "/selectPostByLocation", method = RequestMethod.POST)
    public String selectPostByLocation (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String location = request.getParameter("location");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Post> postArrayList = postService.selectPostByLocation(location);
        if (postArrayList != null){
            map.put("resultCode",200);
            map.put("data", postArrayList);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
        }

        return JSON.toJSONString(map);
    }

    @ResponseBody
    @RequestMapping(value = "/insertPost", method = RequestMethod.POST)
    public String insertPost (HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        String userId = request.getParameter("userId");
        String photoId = request.getParameter("photoId");
        String location = request.getParameter("location");
        User user = userService.selectUserById(Integer.parseInt(userId));
        if (user != null){
            Post post = new Post(Integer.parseInt(photoId), Integer.parseInt(userId), location);
            int result = postService.insertPost(post);
            if (result==1){
                map.put("resultCode",200);
                map.put("msg","success");
            }
            else {
                map.put("resultCode",400);
                map.put("msg","db failed");
            }

        }
        else {
            map.put("resultCode",400);
            map.put("msg","no such user");
        }

        return JSON.toJSONString(map);

    }

}
