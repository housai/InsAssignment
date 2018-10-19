package com.klein.instagram.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.klein.instagram.R;

import com.klein.instagram.bean.UserBean;
import com.klein.instagram.network.JsonCallback;
import com.klein.instagram.utils.OkGoUtil;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import helpers.UserData;

public class LoginActivity extends Activity {                 //Login page
    private EditText mAccount;                        //Set username
    private EditText mPwd;                            //Set password
    private Button mRegisterButton;                   //register button
    private Button mLoginButton;                      //login button

    private SharedPreferences login_sp;

    private View loginView;                           //Login
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
        //Use id to find layout
        mRegisterButton = (Button) findViewById(R.id.login_btn_register);
        mLoginButton = (Button) findViewById(R.id.login_btn_login);
        loginView=findViewById(R.id.login_view);
        loginSuccessView=findViewById(R.id.login_success_view);
        loginSuccessShow=(TextView) findViewById(R.id.login_success_show);
        editName = (EditText) findViewById(R.id.login_edit_account);
        editPw = (EditText) findViewById(R.id.login_edit_pwd);
        login_sp = getSharedPreferences("userInfo", 0);
        mRegisterButton.setOnClickListener(mListener);
        //Set onClickListeners
        mLoginButton.setOnClickListener(mListener);
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_register:
                    //Register button takes us to register page
                    Intent intent_Login_to_Register = new Intent(LoginActivity.this,RegisterActivity.class) ;    //切换Login Activity至Register Activity
                    startActivity(intent_Login_to_Register);
                    finish();
                    break;
                case R.id.login_btn_login:
                    //Login button tries to login with entered fields
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
        if (isUserNameAndPwdValid()) {
            OkGoUtil.jsonPost(LoginActivity.this, "http://10.12.170.91:8080/ssmtest/UserController/login", map, true, new JsonCallback() {


                @Override
                public void onSucess(JSONObject jsonObject) {
                    Toast.makeText(LoginActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
                    try {
                        if (jsonObject.getInt("resultCode") == 200) {
                            UserBean user = new Gson().fromJson(jsonObject.getString("user"), UserBean.class);
                            UserData.setUserId(user.getId());
                            UserData.setUsername(user.getUsername());
                            UserData.setProfilephoto(user.getProfilephoto());
                            Intent intent_login_success = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent_login_success);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            });
        }
    }

    public boolean isUserNameAndPwdValid() {
        if (name.isEmpty()) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (pwd.isEmpty()){
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

