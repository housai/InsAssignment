package com.klein.instagram.activity;


/**
 * Created by Sai on 2018/10/10.
 */

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.klein.instagram.R;
import com.klein.instagram.bean.UserBean;
import com.klein.instagram.network.JsonCallback;
import com.klein.instagram.utils.OkGoUtil;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    private EditText mAccount;                        //Set username
    private EditText mPwd;                            //Set password
    private EditText mPwdCheck;                       //Check password
    private Button mSureButton;                       //Sure button
    private Button mCancelButton;                     //Cancel button

    private String name;
    private String pwd;
    private String spwd;
    private String errorMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAccount = (EditText) findViewById(R.id.resetpwd_edit_name);
        mPwd = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
        mPwdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);

        mSureButton = (Button) findViewById(R.id.register_btn_sure);
        mCancelButton = (Button) findViewById(R.id.register_btn_cancel);

        mSureButton.setOnClickListener(m_register_Listener);      //Register listeners for 2 buttons
        mCancelButton.setOnClickListener(m_register_Listener);

    }
    View.OnClickListener m_register_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register_btn_sure:
                    //Sure button regsiters
                    register();
//                    Intent intent_Register2_to_Login = new Intent(RegisterActivity.this,LoginActivity.class) ;    //切换User Activity至Login Activity
//                    startActivity(intent_Register2_to_Login);
                    break;
                case R.id.register_btn_cancel:
                    //Cancels register and goes back to Login
                    Intent intent_Register_to_Login = new Intent(RegisterActivity.this,LoginActivity.class) ;    //切换User Activity至Login Activity
                    startActivity(intent_Register_to_Login);
                    finish();
                    break;
            }
        }
    };
    public void register() {
        //When we are sure and register
        name = mAccount.getText().toString().trim();
        System.out.println("THis is the name______________"+name);
        pwd = mPwd.getText().toString().trim();
        System.out.println("THis is the pwd______________"+pwd);
        spwd = mPwdCheck.getText().toString().trim();
        System.out.println("THis is the second pwd______________"+spwd);



        Map<String, String> map = new HashMap<>();
        map.put("username", name);
        map.put("password", pwd);
        map.put("surepassword",spwd);

        if (isUserNameAndPwdValid()) {


            OkGoUtil.jsonPost(RegisterActivity.this, "http://10.12.170.91:8080/ssmtest/UserController/register", map, true, new JsonCallback() {


                @Override
                public void onSucess(JSONObject jsonObject) {
                    Toast.makeText(RegisterActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
                    try {
                        if (jsonObject.getInt("resultCode") == 200) {
                            Toast.makeText(RegisterActivity.this, getString(R.string.register_success), Toast.LENGTH_LONG).show();
                            Intent intent_Register_to_Login = new Intent(RegisterActivity.this, LoginActivity.class);
                            //Switch back to Login
                            startActivity(intent_Register_to_Login);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this,jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }

    }
    public boolean isUserNameAndPwdValid() {
        if (name.isEmpty()) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (pwd.isEmpty()) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(spwd.isEmpty()) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(!pwd.equals(spwd)){
            Toast.makeText(this, "Please check your password",
                    Toast.LENGTH_SHORT).show();
            return false;
        }else{
        return true;}
    }
}
