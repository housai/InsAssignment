package com.klein.instagram.utils;

/**
 * Created Kaven Peng 19/10/18
 */

public class UserData {
    private static Integer userId;
    private static String username;
    private static String profilephoto;

    public UserData() {
    }

    public static Integer getUserId() {
        return userId;
    }

    public static void setUserId(Integer userId) {
        UserData.userId = userId;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserData.username = username;
    }

    public static String getProfilephoto() {
        return profilephoto;
    }

    public static void setProfilephoto(String profilephoto) {
        UserData.profilephoto = profilephoto;
    }
}
