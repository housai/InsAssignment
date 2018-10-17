package com.klein.controller;

import com.alibaba.fastjson.JSON;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;


@Controller
@RequestMapping(value = "/FileController")
public class FileController {



//    @ResponseBody
//    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//    public String logsUpload(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String pathRoot = request.getSession().getServletContext().getRealPath("/");
//    }





}
