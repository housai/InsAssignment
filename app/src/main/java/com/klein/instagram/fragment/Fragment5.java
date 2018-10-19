package com.klein.instagram.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.klein.instagram.adapter.HomeAdapter;
import com.klein.instagram.adapter.ProfilePhotoAdapter;
import com.klein.instagram.bean.ActivityFeedBean;
import com.klein.instagram.bean.FollowBean;
import com.klein.instagram.bean.UserBean;
import com.klein.instagram.helpers.UserData;
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
    private List<String> mList;
    private List<FollowBean> followingUserList;
    private List<FollowBean> followerUserList;
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
        posts_number = mView.findViewById(R.id.posts_num);
        following_number = mView.findViewById(R.id.following_num);
        followed_number = mView.findViewById(R.id.followers_num);


        mVRecycler = (RecyclerView) mView.findViewById(R.id.recyclerview_profile);
        mVRecycler.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        mVRecycler.setHasFixedSize(true);
        mVRecycler.setNestedScrollingEnabled(false);
        mList = new ArrayList<>();
        for (int i = 1; i < 17; i++) {
            mList.add("text" + i);

        }
        getFollowingUser();
        getPostNumber();
        getFollowedUser();
        mAdapter = new ProfilePhotoAdapter(this.getContext(), mList);
        mVRecycler.setAdapter(mAdapter);
        return mView;
    }


    private class ImageListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.profile_photo:
                    click = 1;
                    mVRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
                    mList = new ArrayList<>();
                    for (int i = 1; i < 17; i++) {
                        mList.add("text" + i);
                    }
                    mAdapter = new ProfilePhotoAdapter(view.getContext(), mList);
                    mVRecycler.setAdapter(mAdapter);
                    break;
                case R.id.profile_myPhoto:
                    click = 2;
                    mVRecycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
                    mList = new ArrayList<>();
                    for (int i = 1; i < 17; i++) {
                        mList.add("text" + i);
                    }
                    HomeAdapter homeAdapter = new HomeAdapter(view.getContext(), mList);
                    mVRecycler.setAdapter(homeAdapter);
                    break;
                case R.id.following_list:
                    click = 3;
//                   FollowingAdapter followingAdapter = new FollowingAdapter(view.getContext(), followingUserList);
//                   mVRecycler.setAdapter(followingAdapter);
                    getFollowingUser();
                    break;
                case R.id.follower_list:
                    click = 4;
//                    FollowerAdapter followerAdapter = new FollowerAdapter(view.getContext(), followerUserList);
//                    mVRecycler.setAdapter(followerAdapter);
                    getFollowedUser();
                    break;
            }
        }
    }
    public void getFollowedUser() {
        Map<String, String> map = new HashMap<>();
        map.put("followedId", myId.toString());
        OkGoUtil.jsonPost(this.getContext(), "http://10.12.170.91:8080/ssmtest/FollowController/selectFollowByFollowedId", map, true, new JsonCallback() {
            @Override
            public void onSucess(JSONObject jsonObject) {
                try {
                    System.out.println("1111111111");
                    if (jsonObject.getInt("resultCode") == 200) {
                        JSONArray arr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < arr.length(); i++) {
                            FollowBean fol = new Gson().fromJson(arr.getString(i), FollowBean.class);
                            //posts_number.setText();
                            Integer z = arr.length();
                            followed_number.setText(z.toString());
                            followerUserList.add(fol);
                            System.out.println("22222222");
                        }
                        mAdapter.notifyDataSetChanged();
                    }else{
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
        System.out.println("333333");
        Map<String, String> map = new HashMap<>();
        map.put("userId", myId.toString());
        OkGoUtil.jsonPost(this.getContext(), "http://10.12.170.91:8080/ssmtest/FollowController/selectFollowByUserId", map, true, new JsonCallback() {

            @Override
            public void onSucess(JSONObject jsonObject) {
                try {
                    System.out.println("444444444");
                    if (jsonObject.getInt("resultCode") == 200) {
                        // success in getting comments, not empty
                        JSONArray arr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < arr.length(); i++) {
                            FollowBean foll = new Gson().fromJson(arr.getString(i), FollowBean.class);
                            Integer x = arr.length();
                            following_number.setText(x.toString());
                            followingUserList.add(foll);
                            System.out.println("5555555555555");
                        }
                        mAdapter.notifyDataSetChanged();
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
    public void getPostNumber() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", myId.toString());
        OkGoUtil.jsonPost(this.getContext(), "http://10.12.170.91:8080/ssmtest/PostController/selectPostByUserId", map, true, new JsonCallback() {
            @Override
            public void onSucess(JSONObject jsonObject) {
                try {
                    System.out.println("666666666666");
                    if (jsonObject.getInt("resultCode") == 200) {
                        // success in getting comments, not empty
                        JSONArray arr = jsonObject.getJSONArray("data");
                        Integer y = arr.length();
                        posts_number.setText(y.toString());
                        System.out.println("77777777777");
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