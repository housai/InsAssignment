package com.burhanrashid52.imageeditor.activity;


/**
 * Created by Sai on 2018/10/10.
 */

import android.content.Intent;
import android.os.Bundle;
import com.burhanrashid52.imageeditor.R;
import com.burhanrashid52.imageeditor.network.JsonCallback;
import com.burhanrashid52.imageeditor.utils.OkGoUtil;

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
    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private EditText mPwdCheck;                       //密码编辑
    private Button mSureButton;                       //确定按钮
    private Button mCancelButton;                     //取消按钮

    private String name;
    private String pwd;
    private String spwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAccount = (EditText) findViewById(R.id.resetpwd_edit_name);
        mPwd = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
        mPwdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);

        mSureButton = (Button) findViewById(R.id.register_btn_sure);
        mCancelButton = (Button) findViewById(R.id.register_btn_cancel);

        mSureButton.setOnClickListener(m_register_Listener);      //注册界面两个按钮的监听事件
        mCancelButton.setOnClickListener(m_register_Listener);

    }
    View.OnClickListener m_register_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register_btn_sure:                       //确认按钮的监听事件
                    register();
                    break;
                case R.id.register_btn_cancel:                     //取消按钮的监听事件,由注册界面返回登录界面
                    Intent intent_Register_to_Login = new Intent(RegisterActivity.this,LoginActivity.class) ;    //切换User Activity至Login Activity
                    startActivity(intent_Register_to_Login);
                    finish();
                    break;
            }
        }
    };
    public void register() {
        //确认按钮的监听事件

        Map<String, String> map = new HashMap<>();
        map.put("username", name);
        map.put("password", pwd);
        map.put("surepassword",spwd);

        OkGoUtil.jsonPost(RegisterActivity.this, "http://10.12.170.91:8080/ssmtest/UserController/register", map, true, new JsonCallback() {

            @Override
            public void onSucess(JSONObject jsonObject) {
                Toast.makeText(RegisterActivity.this,jsonObject.toString(),Toast.LENGTH_LONG).show();
                try {
                    if (jsonObject.getInt("resultCode") == 200){
                        Toast.makeText(RegisterActivity.this,"哈哈哈哈",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(RegisterActivity.this,"飒飒的是",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

                String userName = mAccount.getText().toString().trim();
                String userPwd = mPwd.getText().toString().trim();
                String userPwdCheck = mPwdCheck.getText().toString().trim();

                if (userPwd.equals(userPwdCheck) == false) {     //两次密码输入不一样
                    Toast.makeText(this, getString(R.string.pwd_not_the_same), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                    Intent intent_Register_to_Login = new Intent(RegisterActivity.this, LoginActivity.class);    //切换User Activity至Login Activity
                    startActivity(intent_Register_to_Login);
                    finish();
                }

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
        }else if(mPwdCheck.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
