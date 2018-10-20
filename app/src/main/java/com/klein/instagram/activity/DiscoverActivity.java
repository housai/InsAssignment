package com.klein.instagram.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.klein.instagram.R;
import com.klein.instagram.adapter.DiscoveryAdapter;
import com.klein.instagram.bean.UserBean;
import com.klein.instagram.network.HttpContent;
import com.klein.instagram.network.JsonCallback;
import com.klein.instagram.utils.OkGoUtil;
import com.klein.instagram.utils.UserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscoverActivity extends Activity {
    private EditText searchUser;                        //User name
    private Button mSearchButton;
    private Button mbackButton;
    private ImageView userImage;
    private TextView dis_res_user_name;
    private String sname;
    private List<UserBean> recommendUserList = new ArrayList<UserBean>();
    private DiscoveryAdapter mAdapter;
    private RecyclerView recommendRecyclerView;
    private Button follow1;
    private String follow1Id;
    private int isFollowing;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discovery);
        mSearchButton = (Button) findViewById(R.id.search_btn);
        mbackButton = (Button) findViewById(R.id.dis_but_backward);
        userImage = findViewById(R.id.dis_userImage);
        searchUser = (EditText) findViewById(R.id.dis_input);
        dis_res_user_name = (TextView)findViewById(R.id.dis_res_user_name);
        follow1 = (Button)findViewById(R.id.follow1);
        recommendRecyclerView = (RecyclerView)findViewById(R.id.recommendRecyclerView);
        recommendRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recommendRecyclerView.setHasFixedSize(true);
        recommendRecyclerView.setNestedScrollingEnabled(false);

        mAdapter = new DiscoveryAdapter(DiscoverActivity.this,recommendUserList);
        recommendRecyclerView.setAdapter(mAdapter);

        follow1.setOnClickListener(mListener);
        mSearchButton.setOnClickListener(mListener);
        mbackButton.setOnClickListener(mListener);
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search_btn:                            //Search button
                    sname = searchUser.getText().toString();    //Search by entered name
                    search();
                    searchUser.setText("");
                    closeInputMethod(DiscoverActivity.this,searchUser);
                    break;
                case R.id.dis_but_backward: //Return to main page
                    Intent intent_Discover_to_Main = new Intent(DiscoverActivity.this,MainActivity.class) ;
                    //Switch Intent to main from discover
                    startActivity(intent_Discover_to_Main);
                    finish();
                    break;
                case R.id.follow1: //Return to main page

                    followSearchUser();
                    break;
            }
        }
    };

        public void followSearchUser(){

                Map<String, String> map = new HashMap<>();

                map.put("userId", UserData.getUserId()+"");// user id
                map.put("followedId",follow1Id); // comment
                OkGoUtil.jsonPost(DiscoverActivity.this, HttpContent.InsertFollow, map, true, new JsonCallback() {

                    @Override
                    public void onSucess(JSONObject jsonObject) {

                        try {
                            if (jsonObject.getInt("resultCode") == 200) {
                                Toast.makeText(DiscoverActivity.this, "followed", Toast.LENGTH_LONG).show();
                                follow1.setVisibility(View.INVISIBLE);

                            } else {
                                Toast.makeText(DiscoverActivity.this, "Error", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(DiscoverActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                });
            }
        public void search(){
            Map<String, String> map = new HashMap<>();
            map.put("username", sname);
            map.put("userId", UserData.getUserId()+"");
            if(isUserNameValid()) {
                OkGoUtil.jsonPost(DiscoverActivity.this, HttpContent.SuggestUserByLike, map, true, new JsonCallback() {

                    @Override
                    public void onSucess(JSONObject jsonObject) {

                        try {
                            if (jsonObject.getInt("resultCode") == 200) {
                                UserBean user = new Gson().fromJson(jsonObject.getString("user"), UserBean.class);
                                if (user.getProfilephoto().equals("") || user.getProfilephoto() == null) {
                                    Glide.with(getApplicationContext()).load("http://goo.gl/gEgYUd").into(userImage);
                                }
                                if(jsonObject.getInt("isFollowing")==0){
                                    isFollowing = jsonObject.getInt("isFollowing");
                                    follow1.setVisibility(View.VISIBLE);
                                }else{
                                    follow1.setVisibility(View.INVISIBLE);
                                }

                                follow1Id = user.getId()+"";
                                dis_res_user_name.setText(sname);
                                JSONArray arr = jsonObject.getJSONArray("data");
                                recommendUserList = new ArrayList<UserBean>();
                                for (int i = 0; i < arr.length(); i++) {

                                    UserBean userRecommend = new Gson().fromJson(arr.getString(i), UserBean.class);
                                    recommendUserList.add(userRecommend);
                                }
                                mAdapter = new DiscoveryAdapter(DiscoverActivity.this,recommendUserList);
                                recommendRecyclerView.setAdapter(mAdapter);
                            } else {
                                Toast.makeText(DiscoverActivity.this, "Error", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(DiscoverActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                });
            }
        }
        public boolean isUserNameValid() {
            if (sname.isEmpty()) {
                Toast.makeText(this, getString(R.string.account_empty),
                        Toast.LENGTH_SHORT).show();
                return false;
            }return true;
        }
        protected void onStart () {
            super.onStart();
            // The activity is about to become visible.
        }

        protected void onResume () {
            super.onResume();
            // The activity has become visible (it is now "resumed").
        }

        protected void onPause () {
            super.onPause();
            // Another activity is taking focus (this activity is about to be "paused").
        }

        protected void onStop () {
            super.onStop();
            // The activity is no longer visible (it is now "stopped")
        }

        protected void onDestroy () {
            super.onDestroy();
            // The activity is about to be destroyed.
        }

    public static void closeInputMethod(Context context, EditText tv_works_name) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if(isOpen) {
            imm.hideSoftInputFromWindow(tv_works_name.getWindowToken(), 0); //强制隐藏键盘
            isOpen=false;
        }
    }

}

