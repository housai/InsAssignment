package com.klein.controller;

import com.alibaba.fastjson.JSON;
import com.klein.model.Comment;
import com.klein.model.Post;
import com.klein.model.User;
import com.klein.service.CommentService;
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
@RequestMapping(value = "/CommentController")
public class CommentController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/selectCommentById", method = RequestMethod.POST)
    public String selectCommentById (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String commentId = request.getParameter("commentId");
        Map<String, Object> map = Maps.newHashMap();
        Comment comment = commentService.selectCommentById(Integer.parseInt(commentId));
        if (comment != null){
            map.put("resultCode",200);
            map.put("data",comment);
            return JSON.toJSONString(map);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
            return JSON.toJSONString(map);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectCommentByUser", method = RequestMethod.POST)
    public String selectPostByUserId (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Comment> commentArrayList = commentService.selectCommentByUser(Integer.parseInt(userId));
        if (commentArrayList != null){
            map.put("resultCode",200);
            map.put("data", commentArrayList);
            return JSON.toJSONString(map);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
            return JSON.toJSONString(map);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/selectCommentByPost", method = RequestMethod.POST)
    public String selectCommentByPost (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String postId = request.getParameter("postId");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Comment> commentArrayList = commentService.selectCommentByUser(Integer.parseInt(postId));
        if (commentArrayList != null){
            ArrayList<Map> mapsArrayList = new ArrayList<Map>();
            for (Comment comment :
                    commentArrayList) {
                Map<String, Object> commentMap = Maps.newHashMap();
                User user = userService.selectUserById(comment.getUserId());
                commentMap.put("user", user);
                commentMap.put("comment", comment);
                mapsArrayList.add(commentMap);
            }
            map.put("resultCode",200);
            map.put("data", mapsArrayList);
            return JSON.toJSONString(map);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");
            return JSON.toJSONString(map);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/insertComment", method = RequestMethod.POST)
    public String insertComment (HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        String userId = request.getParameter("userId");
        String postId = request.getParameter("postId");
        String content = request.getParameter("content");
        User user = userService.selectUserById(Integer.parseInt(userId));
        Post post = postService.selectPostById(Integer.parseInt(postId));
        if (user != null && post != null){
            Comment comment = new Comment(content, Integer.parseInt(userId), Integer.parseInt(postId));
            int result = commentService.insertComment(comment);
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
