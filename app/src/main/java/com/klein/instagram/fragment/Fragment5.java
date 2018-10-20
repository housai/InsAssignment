package com.klein.instagram.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.klein.instagram.R;
import com.klein.instagram.adapter.FollowerAdapter;
import com.klein.instagram.adapter.FollowingAdapter;
import com.klein.instagram.adapter.HomeAdapter;
import com.klein.instagram.adapter.ProfilePhotoAdapter;
import com.klein.instagram.bean.FollowBean;
import com.klein.instagram.bean.Post;
import com.klein.instagram.bean.UserBean;
import com.klein.instagram.bean.UserPost;
import com.klein.instagram.utils.UserData;
import com.klein.instagram.network.HttpContent;
import com.klein.instagram.network.JsonCallback;
import com.klein.instagram.utils.OkGoUtil;
import com.klein.instagram.utils.XCRoundImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Fragment5 extends Fragment {
    private View mView;
    private TextView posts_number;
    private TextView following_number;
    private TextView followed_number;
    private TextView userName;

    private RecyclerView mVRecycler;
    private ProfilePhotoAdapter mAdapter;
    private HomeAdapter homeAdapter;
    private FollowingAdapter fAdapter;
    private FollowerAdapter fedAdapter;


    private ArrayList<Post> fisrtList;
    private ArrayList<UserPost> secondList;
    private List<UserBean> thirdList;
    private List<UserBean> fourthList;
    private int click = 1;
    private Integer myId = UserData.getUserId();
    ImageView profile_photo;
    ImageView profile_myPhoto;
    ImageView following_uesr;//你关注别人
    ImageView follower_user;//别人关注你


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment5, container, false);
        XCRoundImageView imageview = mView.findViewById(R.id.roundImageView);
        profile_photo = mView.findViewById(R.id.profile_photo);
        profile_myPhoto = mView.findViewById(R.id.profile_myPhoto);
        following_uesr = mView.findViewById(R.id.following_list);
        follower_user = mView.findViewById(R.id.follower_list);
        profile_photo.setOnClickListener(new ImageListener());
        profile_myPhoto.setOnClickListener(new ImageListener());
        following_uesr.setOnClickListener(new ImageListener());
        follower_user.setOnClickListener(new ImageListener());


        userName = mView.findViewById(R.id.this_username);
        userName.setText(UserData.getUsername());
        posts_number = mView.findViewById(R.id.posts_num);
        following_number = mView.findViewById(R.id.following_num);
        followed_number = mView.findViewById(R.id.followers_num);


        mVRecycler = (RecyclerView) mView.findViewById(R.id.recyclerview_profile);
        mVRecycler.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        mVRecycler.setHasFixedSize(true);
        mVRecycler.setNestedScrollingEnabled(false);

        init();
//        getFollowingUser();
        SelectPostByUserId();
//        getFollowedUser();
        return mView;
    }


    private class ImageListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.profile_photo:
                    click = 1;
                    SelectPostByUserId();
                    break;
                case R.id.profile_myPhoto:
                    click = 2;
                    selectAllPostByUserId();
                    break;
                case R.id.following_list:
                    click = 3;
                    getFollowingUser();
                    break;
                case R.id.follower_list:
                    click = 4;
                    getFollowedUser();
                    break;
            }
        }
    }

    public void getFollowedUser() {
        Map<String, String> map = new HashMap<>();
        map.put("followedId", UserData.getUserId()+"");
        OkGoUtil.jsonPost(this.getContext(), HttpContent.SelectFollowByFollowedId, map, true, new JsonCallback() {
            @Override
            public void onSucess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("resultCode") == 200) {
                        fourthList = new ArrayList<UserBean>();
                        JSONArray arr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < arr.length(); i++) {
                            UserBean userBean = new Gson().fromJson(arr.getString(i), UserBean.class);

                            fourthList.add(userBean);
                        }
                        mVRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        FollowerAdapter followingAdapter = new FollowerAdapter(getContext(), fourthList);
                        mVRecycler.setAdapter(followingAdapter);
                    }else{
                        Toast.makeText(getContext(), "Fail to connect!",Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
            }

        });

    }
    public void getFollowingUser() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", UserData.getUserId()+"");
        OkGoUtil.jsonPost(this.getContext(), HttpContent.SelectFollowByUserId, map, true, new JsonCallback() {

            @Override
            public void onSucess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("resultCode") == 200) {
                        // success in getting comments, not empty
                        thirdList = new ArrayList<UserBean>();
                        JSONArray arr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < arr.length(); i++) {
                            UserBean userBean = new Gson().fromJson(arr.getString(i), UserBean.class);

                            thirdList.add(userBean);
                        }
                        mVRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        FollowingAdapter followingAdapter = new FollowingAdapter(getContext(), thirdList);
                        mVRecycler.setAdapter(followingAdapter);
                    }else{
                        //no following, exist, do nothing
                        Toast.makeText(getContext(), "Fail to connect!",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
            }

        });

    }
    public void SelectPostByUserId() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", UserData.getUserId()+"");
        OkGoUtil.jsonPost(this.getContext(), HttpContent.SelectPostByUserId, map, true, new JsonCallback() {
            @Override
            public void onSucess(JSONObject jsonObject) {
                try {

                    if (jsonObject.getInt("resultCode") == 200) {
                        // success in getting comments, not empty
                        fisrtList = new ArrayList<Post>();
                        JSONArray arr = jsonObject.getJSONArray("data");
                        Log.d("哈哈哈哈",arr.toString());
                        for(int i = 0; i<arr.length();i++){
                            Post post = new Gson().fromJson(arr.getString(i), Post.class);
                            fisrtList.add(post);
                        }
                        mVRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        mAdapter = new ProfilePhotoAdapter(getContext(), fisrtList);
                        mVRecycler.setAdapter(mAdapter);

                    }else{
                        //no following, exist, do nothing
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
            }

        });

    }

    public void selectAllPostByUserId() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", myId.toString());
        OkGoUtil.jsonPost(this.getContext(), HttpContent.SelectAllPostByUserId, map, true, new JsonCallback() {
            @Override
            public void onSucess(JSONObject jsonObject) {
                try {

                    if (jsonObject.getInt("resultCode") == 200) {
                        // success in getting comments, not empty
                        secondList = new ArrayList<UserPost>();
                        JSONArray arr = jsonObject.getJSONArray("data");
                        for(int i = 0; i<arr.length();i++){
                            UserPost userPost = new Gson().fromJson(arr.getString(i), UserPost.class);
                            secondList.add(userPost);
                        }
                        mVRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        HomeAdapter homeAdapter = new HomeAdapter(getContext(), secondList);
                        mVRecycler.setAdapter(homeAdapter);
                    }else{
                        //no following, exist, do nothing
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
            }

        });

    }


    public void init() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", UserData.getUserId()+"");
        OkGoUtil.jsonPost(this.getContext(), HttpContent.GetUserStats, map, true, new JsonCallback() {
            @Override
            public void onSucess(JSONObject jsonObject) {
                try {

                    if (jsonObject.getInt("resultCode") == 200) {
                        // success in getting comments, not empty
                        following_number.setText(jsonObject.getInt("follow")+"");
                        followed_number.setText(jsonObject.getInt("followed")+"");
                        posts_number.setText(jsonObject.getInt("post")+"");

                    }else{
                        //no following, exist, do nothing
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
            }

        });

    }
}