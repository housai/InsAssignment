package com.klein.instagram.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.klein.instagram.R;
import com.klein.instagram.bean.UserBean;
import com.klein.instagram.network.JsonCallback;
import com.klein.instagram.utils.OkGoUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class SortActivity extends Activity{

    private Button sortbyTime;
    private Button sortbyLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort);

        // 设置sort相关

        sortbyTime = (Button) findViewById(R.id.but_sortByTime);
        sortbyLocation = (Button) findViewById(R.id.but_sortByLocation);



        sortbyTime.setOnClickListener(mListener);
        sortbyLocation.setOnClickListener(mListener);

    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.but_sortByTime:
                    sortByTime();
                    finish();
                    break;
                case R.id.but_sortByLocation: //Return to main page
                    sortByLocation();
                    finish();
                    break;
            }
        }
    };
    public void sortByTime(){
        Map<String, String> map = new HashMap<>();
            OkGoUtil.jsonPost(SortActivity.this, "http://10.12.170.91:8080/ssmtest/PostController/sortByPostId", map, true, new JsonCallback() {

                @Override
                public void onSucess(JSONObject jsonObject) {
                    try {
                        if (jsonObject.getInt("resultCode") == 200) {
//                            if (user.getProfilephoto().equals("") || user.getProfilephoto() == null) {
//                                Glide.with(getApplicationContext()).load("http://goo.gl/gEgYUd").into(userImage);
//                            }
//                            dis_res_user_name.setText(user.getUsername());
//                            JSONArray arr = jsonObject.getJSONArray("data");
//                            Toast.makeText(DiscoverActivity.this, arr.length() + "Success setText", Toast.LENGTH_LONG).show();
//                            for (int i = 0; i < arr.length(); i++) {
//                                UserBean userRecommend = new Gson().fromJson(arr.getString(i), UserBean.class);
//                                Toast.makeText(DiscoverActivity.this, userRecommend.getUsername() + "Success getUsername", Toast.LENGTH_LONG).show();
//                                recommendUserList.add(userRecommend);
//                            }
//                            mAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(SortActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(SortActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            });

    }
    public void sortByLocation(){

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

}
