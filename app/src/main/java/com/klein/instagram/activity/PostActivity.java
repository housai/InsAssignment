package com.klein.instagram.activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

/* Created by Kaven Peng 16/10/18
**/

public class PostActivity extends AppCompatActivity {
        private EditText editText;                        //Edit post
        private Button mPostButton;
        private Button mbackButton;
        private ImageView postImage;
        private String postText;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_post);
            mPostButton = (Button) findViewById(R.id.post_button_forward);
            mbackButton = (Button) findViewById(R.id.post_button_backward);
            postImage = findViewById(R.id.post_image);
            editText = (EditText) findViewById(R.id.postText);
            mPostButton.setOnClickListener(mListener);
            mbackButton.setOnClickListener(mListener);

            Intent intent = getIntent();
            String post_image_path = intent.getStringExtra("imagePath");
            Uri imageUri = Uri.parse(post_image_path);
            postImage.setImageURI(imageUri);
        }
        View.OnClickListener mListener = new View.OnClickListener() {                  //Set listeners
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.post_button_forward:                            //Post
                        postText = editText.getText().toString();    //Get post text
                        post();
                        Intent intent_Post_to_Main = new Intent(PostActivity.this,MainActivity.class) ; //Post done, return to main
                        startActivity(intent_Post_to_Main);
                        break;
                    case R.id.post_button_backward: //Return
                        Intent intent_back_Post_to_Main = new Intent(PostActivity.this,MainActivity.class) ; //Go back
                        startActivity(intent_back_Post_to_Main);
                        finish();
                        break;
                }
            }
        };
        public void post(){
            Map<String, String> map = new HashMap<>();
            map.put("postText", postText);
            OkGoUtil.jsonPost(PostActivity.this, "http://10.12.170.91:8080/ssmtest/UserController/suggestUserByLike", map, true, new JsonCallback() {
                @Override
                public void onSucess(JSONObject jsonObject) {
                    try {
                        if (jsonObject.getInt("resultCode") == 200){
                            UserBean user =  new Gson().fromJson(jsonObject.getString("user"), UserBean.class);
                            if(user.getProfilephoto().equals("") || user.getProfilephoto() == null){
                                Glide.with(getApplicationContext()).load("http://goo.gl/gEgYUd").into(postImage);
                            }
                            JSONArray arr = jsonObject.getJSONArray("data");
                            Toast.makeText(PostActivity.this,arr.length()+"hahaha",Toast.LENGTH_LONG).show();
                            for (int i = 0; i < arr.length(); i++) {
                                UserBean userRecommend = new Gson().fromJson(arr.getString(i), UserBean.class);
                                Toast.makeText(PostActivity.this,userRecommend.getUsername()+"heheh",Toast.LENGTH_LONG).show();
                                //recommendUserList.add(userRecommend);
                            }
                            //mAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(PostActivity.this,"Error",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Exception e) {
                    Toast.makeText(PostActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        @Override
        protected void onStart() {
            super.onStart();
        }

        @Override
        protected void onResume() {
            super.onResume();
            // The activity has become visible (it is now "resumed").
        }
        @Override
        protected void onPause() {
            super.onPause();
            // Another activity is taking focus (this activity is about to be "paused").
        }
        @Override
        protected void onStop() {
            super.onStop();
            // The activity is no longer visible (it is now "stopped")
        }
        @Override
        protected void onDestroy() {
            super.onDestroy();
            // The activity is about to be destroyed.
        }
}