package com.klein.instagram.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.klein.instagram.R;
import com.klein.instagram.adapter.CommentAdapter;
import com.klein.instagram.adapter.DiscoveryAdapter;
import com.klein.instagram.bean.UserBean;
import com.klein.instagram.network.JsonCallback;
import com.klein.instagram.utils.OkGoUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends Activity {
    private EditText editComment;                        //User name
    private Button mCommentButton;
    private Button mbackButton;
    private ImageView userImage;
    private TextView com_host_name;
    private TextView com_com_name;
    private String comment;
    private String com_name;

    private List<UserBean> commentList = new ArrayList<UserBean>();
    private CommentAdapter mAdapter;
    private RecyclerView commentRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
        mCommentButton = (Button) findViewById(R.id.send_comment);
        mbackButton = (Button) findViewById(R.id.com_button_backward);
        userImage = findViewById(R.id.com_userImage);
        editComment = (EditText) findViewById(R.id.com_txt);
        com_host_name = (TextView)findViewById(R.id.com_host_name);
        com_com_name = (TextView)findViewById(R.id.com_com_name);

        commentRecyclerView = (RecyclerView)findViewById(R.id.commentRecyclerView);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        commentRecyclerView.setHasFixedSize(true);
        commentRecyclerView.setNestedScrollingEnabled(false);

        mCommentButton.setOnClickListener(mListener);
        mbackButton.setOnClickListener(mListener);



        mAdapter = new CommentAdapter(CommentActivity.this,commentList);
        commentRecyclerView.setAdapter(mAdapter);
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search_btn:                            //Search button
                    comment = editComment.getText().toString();    //Search by entered name
                    comment();
                    break;
                case R.id.com_button_backward: //Return to main page
                    Intent intent_Com_to_Main = new Intent(CommentActivity.this,MainActivity.class) ;
                    //Switch Intent to main from discover
                    startActivity(intent_Com_to_Main);
                    finish();
                    break;
            }
        }
    };
    public void comment(){
        Map<String, String> map = new HashMap<>();
        map.put("username", com_name);
        map.put("comment",comment);
        if(isCommentValid()){
            OkGoUtil.jsonPost(CommentActivity.this, "http://10.12.170.91:8080/ssmtest/UserController/suggestUserByLike", map, true, new JsonCallback() {

            @Override
            public void onSucess(JSONObject jsonObject) {

                try {
                    if (jsonObject.getInt("resultCode") == 200){
                        UserBean user =  new Gson().fromJson(jsonObject.getString("user"), UserBean.class);
                        if(user.getProfilephoto().equals("") || user.getProfilephoto() == null){
                            Glide.with(getApplicationContext()).load("http://goo.gl/gEgYUd").into(userImage);
                        }
                        com_com_name.setText(user.getUsername());
                        JSONArray arr = jsonObject.getJSONArray("data");
                        Toast.makeText(CommentActivity.this,arr.length()+"Success setText",Toast.LENGTH_LONG).show();
                        for (int i = 0; i < arr.length(); i++) {

                            UserBean comList = new Gson().fromJson(arr.getString(i), UserBean.class);
                            Toast.makeText(CommentActivity.this,comList.getUsername()+"Success getUsername",Toast.LENGTH_LONG).show();

                            commentList.add(comList);
                        }
                        mAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(CommentActivity.this,"Error",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(CommentActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }

            });
        }
    }
    public boolean isCommentValid() {
        if (comment.isEmpty()) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
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
