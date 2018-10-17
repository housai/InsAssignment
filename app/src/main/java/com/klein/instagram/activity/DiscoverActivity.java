package com.klein.instagram.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.klein.instagram.R;
import com.klein.instagram.network.JsonCallback;
import com.klein.instagram.utils.OkGoUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DiscoverActivity extends Activity {
    private EditText searchUser;                        //用户名编辑
    private Button mSearchButton;
    private String sname;
    private String spwd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discovery);
        Toast.makeText(DiscoverActivity.this,"哈哈哈哈",Toast.LENGTH_LONG).show();
        mSearchButton = (Button) findViewById(R.id.search_btn);
        mSearchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }
        public void search(){
            Map<String, String> map = new HashMap<>();
            map.put("username", sname);
            map.put("password", spwd);

            OkGoUtil.jsonPost(DiscoverActivity.this, "http://10.12.170.91:8080/ssmtest/UserController/login", map, true, new JsonCallback() {

                @Override
                public void onSucess(JSONObject jsonObject) {
                    Toast.makeText(DiscoverActivity.this,jsonObject.toString(),Toast.LENGTH_LONG).show();
                    try {
                        if (jsonObject.getInt("resultCode") == 200){
                            Toast.makeText(DiscoverActivity.this,"哈哈哈哈",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(DiscoverActivity.this,"飒飒的是",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Exception e) {
                    Toast.makeText(DiscoverActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            });
        }
        public boolean isUserNameAndPwdValid() {
            if (searchUser.getText().toString().trim().equals("")) {
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

}

