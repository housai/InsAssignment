package com.klein.instagram.network;

public class HttpContent {

    public static String uploadImage = "http://10.12.245.47:8080/userPost/";
    public static String ServerURL = "http://10.12.245.47:8080/ssmtest";


    public static String Login = ServerURL+"/UserController/login";
    public static String SelectAllPost = ServerURL+"/PostController/selectAllPost";

    //"http://10.12.170.91:8080/ssmtest/FollowController/insertFollow"
    public static String InsertFollow = ServerURL+"/FollowController/insertFollow";

    //"http://10.12.170.91:8080/ssmtest/UserController/suggestUserByLike"
    public static String SuggestUserByLike = ServerURL+"/UserController/suggestUserByLike";

    //"http://10.12.245.47:8080/ssmtest/CommentController/selectCommentByPost"
    public static String SelectCommentByPost = ServerURL+"/CommentController/selectCommentByPost";

    //"http://10.12.245.47:8080/ssmtest/CommentController/insertComment"
    public static String InsertComment = ServerURL+"/CommentController/insertComment";

    //"http://10.12.170.91:8080/ssmtest/fileUploadControllerAPI/upload
    public static String Upload = ServerURL+"/fileUploadControllerAPI/upload";

    //"http://10.12.170.91:8080/ssmtest/UserController/register"
    public static String Register = ServerURL+"/UserController/register";

    //"http://10.12.170.91:8080/ssmtest/FollowController/selectFollowByFollowedId"
    public static String SelectFollowByFollowedId = ServerURL+"/FollowController/selectFollowByFollowedId";

    //"http://10.12.170.91:8080/ssmtest/FollowController/selectFollowByUserId"
    public static String SelectFollowByUserId = ServerURL+"/FollowController/selectFollowByUserId";

    //"http://10.12.170.91:8080/ssmtest/PostController/selectPostByUserId"
    public static String SelectPostByUserId = ServerURL+"/PostController/selectPostByUserId";

    //"http://10.12.170.91:8080/ssmtest/PostController/selectAllPostByUserId"
    public static String SelectAllPostByUserId = ServerURL+"/PostController/selectAllPostByUserId";

    //"http://10.12.170.91:8080/ssmtest/FollowController/selectFollowPostByUserId
    public static String SelectFollowPostByUserId = ServerURL+"/FollowController/selectFollowPostByUserId";

    //"http://10.12.170.91:8080/ssmtest/UserController/getUserStats
    public static String GetUserStats = ServerURL+"/UserController/getUserStats";

    //"http://10.12.170.91:8080/ssmtest/FollowController/getFollowActivityByUserId"
    public static String GetFollowActivityByUserId = ServerURL+"/FollowController/getFollowActivityByUserId";

    //"http://10.12.170.91:8080/ssmtest/PostController/selectPostByUserIdSortByTime"
    public static String SelectPostByUserIdSortByTime = ServerURL+"/PostController/selectPostByUserIdSortByTime";

    //"http://10.12.170.91:8080/ssmtest/PostController/selectPostByUserIdSortByLocation"
    public static String SelectPostByUserIdSortByLocation = ServerURL+"/PostController/selectPostByUserIdSortByLocation";

    //"http://10.12.170.91:8080/ssmtest/PostController/selectPostByUserIdSortByLocation"
    public static String InsertLike = ServerURL+"/LikeController/insertLike";
}
