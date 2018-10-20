package com.klein.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.klein.model.Post;
import com.klein.model.User;
import com.klein.model.common.UserPost;
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
import java.util.Collections;
import java.util.Date;
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
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
        }
        return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
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
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");

        }
        return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
    }

    @ResponseBody
    @RequestMapping(value = "/selectPostByUserIdSortByLocation", method = RequestMethod.POST)
    public String selectPostByUserIdSortByLocation (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Post> postArrayList = postService.selectPostByUserId(Integer.parseInt(userId));
        if (postArrayList != null){
            Collections.reverse(postArrayList);
            for (int i = 0; i < postArrayList.size(); i++){
                Post post = postArrayList.get(i);
                Date timestamp = post.getTime();
                post.setTime(timestamp);
            }
            map.put("resultCode",200);
            map.put("data", postArrayList);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");

        }
        return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
    }

    @ResponseBody
    @RequestMapping(value = "/selectPostByTime", method = RequestMethod.POST)
    public String selectPostByTime (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
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

        return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
    }

    @ResponseBody
    @RequestMapping(value = "/selectAllPost", method = RequestMethod.POST)
    public String selectAllPost (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("进来了～～～～");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Post> allPost = postService.selectAllPost();
        ArrayList<UserPost> upList = new ArrayList<UserPost>();

        if (allPost != null){
            for(int i = 0;i<allPost.size();i++) {
                UserPost up = new UserPost();
                Post post = allPost.get(i);
                User user = userService.selectUserById(post.getUserId());
                up.setContent(post.getContent());
                up.setLocation(post.getLocation());
                up.setPassword(user.getPassword());
                up.setPhotourl(post.getPhotourl());
                up.setPostId(post.getPostId());
                up.setProfilephoto(user.getProfilephoto());
//        			up.setTime(new Date(post.getTime()));
                up.setUserId(post.getUserId());
                up.setUsername(user.getUsername());
                upList.add(up);

            }
            map.put("resultCode",200);
            map.put("data", upList);
            System.out.println(JSON.toJSONString(map));
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
        }

        return JSON.toJSONString(map);
    }

    @ResponseBody
    @RequestMapping(value = "/selectAllPostByUserId", method = RequestMethod.POST)
    public String selectAllPostByUserId (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("进来了～～～～");
        Map<String, Object> map = Maps.newHashMap();
        String userId = request.getParameter("userId");
        ArrayList<Post> allPost = postService.selectPostByUserId(Integer.parseInt(userId));
        ArrayList<UserPost> upList = new ArrayList<UserPost>();

        if (allPost != null){
            for(int i = 0;i<allPost.size();i++) {
                UserPost up = new UserPost();
                Post post = allPost.get(i);
                User user = userService.selectUserById(post.getUserId());
                up.setContent(post.getContent());
                up.setLocation(post.getLocation());
                up.setPassword(user.getPassword());
                up.setPhotourl(post.getPhotourl());
                up.setPostId(post.getPostId());
                up.setProfilephoto(user.getProfilephoto());
//        			up.setTime(new Date(post.getTime()));
                up.setUserId(post.getUserId());
                up.setUsername(user.getUsername());
                upList.add(up);

            }
            map.put("resultCode",200);
            map.put("data", upList);
            System.out.println(JSON.toJSONString(map));
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
        }

        return JSON.toJSONString(map);
    }
}
