package com.burhanrashid52.imageeditor.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.burhanrashid52.imageeditor.R;
import com.burhanrashid52.imageeditor.network.HttpContent;
import com.burhanrashid52.imageeditor.network.JsonCallback;
import com.burhanrashid52.imageeditor.utils.OkGoUtil;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {                 //登录界面活动
    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private Button mRegisterButton;                   //注册按钮
    private Button mLoginButton;                      //登录按钮

    private SharedPreferences login_sp;

    private View loginView;                           //登录
    private View loginSuccessView;
    private TextView loginSuccessShow;
    private EditText editName;
    private EditText editPw;
    private String name;
    private String pwd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //通过id找到相应的控件
        mRegisterButton = (Button) findViewById(R.id.login_btn_register);
        mLoginButton = (Button) findViewById(R.id.login_btn_login);
        loginView=findViewById(R.id.login_view);
        loginSuccessView=findViewById(R.id.login_success_view);
        loginSuccessShow=(TextView) findViewById(R.id.login_success_show);
        editName = (EditText) findViewById(R.id.login_edit_account);
        editPw = (EditText) findViewById(R.id.login_edit_pwd);
        login_sp = getSharedPreferences("userInfo", 0);
        mRegisterButton.setOnClickListener(mListener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
        mLoginButton.setOnClickListener(mListener);
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_register:                            //登录界面的注册按钮
                    Intent intent_Login_to_Register = new Intent(LoginActivity.this,RegisterActivity.class) ;    //切换Login Activity至Register Activity
                    startActivity(intent_Login_to_Register);
                    finish();
                    break;
                case R.id.login_btn_login:
                    //登录界面的登录按钮
                    name = editName.getText().toString();
                    pwd = editPw.getText().toString();
                    login();
                    break;
            }
        }
    };

    public void login() {

        Map<String, String> map = new HashMap<>();
        map.put("username", name);
        map.put("password", pwd);

        OkGoUtil.jsonPost(LoginActivity.this, "http://10.12.170.91:8080/ssmtest/UserController/login", map, true, new JsonCallback() {


            @Override
            public void onSucess(JSONObject jsonObject) {
                Toast.makeText(LoginActivity.this,jsonObject.toString(),Toast.LENGTH_LONG).show();
                try {
                    if (jsonObject.getInt("resultCode") == 200){
                        Intent intent_login_success = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent_login_success);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"飒飒的是",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }

        });

    }

    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

   // @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

}

