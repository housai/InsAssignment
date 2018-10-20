package com.klein.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.klein.model.Follow;
import com.klein.model.Post;
import com.klein.model.User;
import com.klein.model.common.UserPost;
import com.klein.service.FollowService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;


@Controller
@RequestMapping(value = "/PostController")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private FollowService followService;

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
        System.out.println(JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty));
        return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
    }

    @ResponseBody
    @RequestMapping(value = "/selectPostByUserIdSortByLocation", method = RequestMethod.POST)
    public String selectPostByUserIdSortByLocation (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Follow> followArrayList = followService.selectFollowByUserId(Integer.parseInt(userId));
        ArrayList<Post> posts = new ArrayList<Post>();
        ArrayList<UserPost> upList = new ArrayList<UserPost>();
        ArrayList<Post> postArrayList = postService.selectPostByUserId(Integer.parseInt(userId));
        posts.addAll(postArrayList);
        if (followArrayList != null){
            for (Follow follow:followArrayList) {
                postArrayList = postService.selectPostByUserId(follow.getFollowedId());
                posts.addAll(postArrayList);
            }

        }
        if (posts != null){
            Collections.reverse(posts);
            for(int i = 0;i<posts.size();i++) {
                UserPost up = new UserPost();
                Post post = posts.get(i);
                User user = userService.selectUserById(post.getUserId());
                up.setContent(post.getContent());
                up.setLocation(post.getLocation());
                up.setPassword(user.getPassword());
                up.setPhotourl(post.getPhotourl());
                up.setPostId(post.getPostId());
                up.setProfilephoto(user.getProfilephoto());
                up.setUserId(post.getUserId());
                up.setUsername(user.getUsername());
                upList.add(up);

            }
            map.put("resultCode",200);
            map.put("data", upList);
        }
        else {
            map.put("resultCode",400);
            map.put("msg","fail");

        }
        return JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty);
    }

    @ResponseBody
    @RequestMapping(value = "/selectPostByUserIdSortByTime", method = RequestMethod.POST)
    public String selectPostByUserIdSortByTime (HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        Map<String, Object> map = Maps.newHashMap();
        ArrayList<Follow> followArrayList = followService.selectFollowByUserId(Integer.parseInt(userId));
        ArrayList<Post> posts = new ArrayList<Post>();
        ArrayList<UserPost> upList = new ArrayList<UserPost>();
        ArrayList<Post> postArrayList = postService.selectPostByUserId(Integer.parseInt(userId));
        posts.addAll(postArrayList);
        if (followArrayList != null){
            for (Follow follow:followArrayList) {
                postArrayList = postService.selectPostByUserId(follow.getFollowedId());
                posts.addAll(postArrayList);
            }

        }
        if (posts != null){
            Collections.sort(posts, new Comparator<Post>() {
                public int compare(Post o1, Post o2) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date date1 = format.parse(o1.getTime());
                        Date date2 = format.parse(o2.getTime());
                        if(date1.after(date2)){
                            return 1;
                        } else if (date1.before(date2)){
                            return -1;
                        } else {
                            return 0;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }

                }
            });
            for(int i = 0;i<posts.size();i++) {
                UserPost up = new UserPost();
                Post post = posts.get(i);
                User user = userService.selectUserById(post.getUserId());
                up.setContent(post.getContent());
                up.setLocation(post.getLocation());
                up.setPassword(user.getPassword());
                up.setPhotourl(post.getPhotourl());
                up.setPostId(post.getPostId());
                up.setProfilephoto(user.getProfilephoto());
                up.setUserId(post.getUserId());
                up.setUsername(user.getUsername());
                upList.add(up);

            }
            map.put("resultCode",200);
            map.put("data", upList);
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
