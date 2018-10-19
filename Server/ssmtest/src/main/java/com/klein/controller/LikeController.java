package com.klein.controller;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.klein.model.Like;
import com.klein.service.LikeService;


@Controller
@RequestMapping(value = "/LikeController")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/selectLikeById", method = RequestMethod.POST)
    public String selectLikeById (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String likeId = request.getParameter("likeId");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Like> likeList = likeService.selectLikeByPostId(Integer.parseInt(likeId));
        if (likeList != null){
            map.put("resultCode",200);
            map.put("data",likeList);
            return JSON.toJSONString(map);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
            return JSON.toJSONString(map);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectLikeByPostId", method = RequestMethod.POST)
    public String selectLikeByPostId (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String postId = request.getParameter("postId");
        System.out.println(postId);
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Like> likeList = likeService.selectLikeByPostId(Integer.parseInt(postId));
        //Post post = postService.selectPostById(Integer.parseInt(postId));
        if (likeList != null){
            map.put("resultCode",200);
            map.put("data",likeList);
            return JSON.toJSONString(map);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
            return JSON.toJSONString(map);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectLikeByUserId", method = RequestMethod.POST)
    public String selectLikeByUserId (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Like> likeList = likeService.selectLikeByUserId(Integer.parseInt(userId));
        if (likeList != null){
            map.put("resultCode",200);
            map.put("data", likeList);
            return JSON.toJSONString(map);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
            return JSON.toJSONString(map);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectLikeByName", method = RequestMethod.POST)
    public String selectLikeByName (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Like> likeList = likeService.selectLikeByName(username);
        if (likeList != null){
            map.put("resultCode",200);
            map.put("data", likeList);
            return JSON.toJSONString(map);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
            return JSON.toJSONString(map);
        }
    }
    @ResponseBody
    @RequestMapping(value = "/insertLike", method = RequestMethod.POST)
    public String insertLike (HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        String userId = request.getParameter("userId");
        String postId = request.getParameter("postId");
        String username = request.getParameter("username");
        ArrayList<Like> likeArrayList = likeService.selectLikeByName(username);
        ArrayList<Like> likeArrayList2 = likeService.selectLikeByPostId(Integer.parseInt(postId));
        ArrayList<Like> likeArrayList3 = likeService.selectLikeByUserId(Integer.parseInt(userId));

        likeArrayList.addAll(likeArrayList2);
        likeArrayList.addAll(likeArrayList3);
        HashSet hashSet = new HashSet(likeArrayList);
        Post post = postService.selectPostById(Integer.parseInt(postId));
        User user = userService.selectUserById(Integer.parseInt(userId));
        System.out.println(hashSet.size());
        if (hashSet.size()==0 && user != null && post != null){
            Like like = new Like(username, Integer.parseInt(postId), Integer.parseInt(userId));
            int result =  likeService.insertLike(like);
            if (result ==1){
                map.put("resultCode",200);
                map.put("msg","success");
            }else {

                map.put("resultCode",400);
                map.put("msg","db failed");
            }
        }else {
            map.put("resultCode",400);
            map.put("msg","already liked");
        }
        return JSON.toJSONString(map);
    }

}
