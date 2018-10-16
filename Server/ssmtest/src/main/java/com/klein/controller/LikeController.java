package com.klein.controller;
import java.util.ArrayList;
import java.util.HashSet;
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

import com.klein.model.Like;
import com.klein.service.LikeService;


@Controller
@RequestMapping(value = "/LikeController")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @ResponseBody
    @RequestMapping(value = "/getUserByPostId", method = RequestMethod.POST)
    public String getUserByPostId (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String postId = request.getParameter("postId");
        System.out.println(postId);
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Like> likeList = likeService.selectLikeByPostId(Integer.parseInt(postId));
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
    @RequestMapping(value = "/getPostByUserId", method = RequestMethod.POST)
    public String getPostByUserId (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    @RequestMapping(value = "/addLike", method = RequestMethod.POST)
    public String addLike (HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        String userId = request.getParameter("userId");
        String postId = request.getParameter("postId");
        String username = request.getParameter("username");
        ArrayList<Like> likeArrayList = likeService.selectLikeByName(username);
        ArrayList<Like> likeArrayList2 = likeService.selectLikeByPostId(Integer.parseInt(postId));
        ArrayList<Like> likeArrayList3 = likeService.selectLikeByUserId(Integer.parseInt(userId));
        boolean duplicate = true;
        if (likeArrayList2 != null){
            likeArrayList.addAll(likeArrayList2);
        }
        if (likeArrayList3 != null){
            likeArrayList.addAll(likeArrayList3);
        }
        if (likeArrayList != null){
            HashSet hashSet = new HashSet(likeArrayList);
            if (hashSet == null){
                Like like = new Like(username, Integer.parseInt(postId), Integer.parseInt(userId));
                likeService.insertLike(like);
                map.put("resultCode",200);
                map.put("msg","success");
                return JSON.toJSONString(map);
            }else {
                map.put("resultCode",400);
                map.put("msg","user exists");
                return JSON.toJSONString(map);
            }
        }else{
            Like like = new Like(username, Integer.parseInt(postId), Integer.parseInt(userId));
            likeService.insertLike(like);
            map.put("resultCode",200);
            map.put("msg","success");
            return JSON.toJSONString(map);
        }

    }

}
