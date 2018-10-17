package com.klein.instagram.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    private ImageView mbackButton;
    private String sname;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discovery);
        Toast.makeText(DiscoverActivity.this,"这个是搜索页面",Toast.LENGTH_LONG).show();
        mSearchButton = (Button) findViewById(R.id.search_btn);
        mbackButton = (ImageView) findViewById(R.id.button_backward);
        searchUser = (EditText) findViewById(R.id.dis_input);
        mSearchButton.setOnClickListener(mListener);
        mbackButton.setOnClickListener(mListener);
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search_btn:                            //搜索界面
                    sname = searchUser.getText().toString();    //根据输入内容进行搜索
                    search();
                    break;
                case R.id.button_backward: //回撤到主界面
                    Intent intent_Discover_to_Main = new Intent(DiscoverActivity.this,MainActivity.class) ; //切换discover界面到main界面
                    startActivity(intent_Discover_to_Main);
                    finish();
                    break;
            }
        }
    };


        public void search(){
            Map<String, String> map = new HashMap<>();
            map.put("username", sname);

            OkGoUtil.jsonPost(DiscoverActivity.this, "http://10.12.170.91:8080/ssmtest/UserController/selectUserByName", map, true, new JsonCallback() {

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
        public boolean isUserNameValid() {
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

