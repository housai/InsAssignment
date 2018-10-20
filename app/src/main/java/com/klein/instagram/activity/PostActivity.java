package com.klein.instagram.activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.klein.instagram.network.HttpContent;
import com.klein.instagram.network.JsonCallback;
import com.klein.instagram.utils.OkGoUtil;
import com.klein.instagram.utils.UserData;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/* Created by Kaven Peng 16/10/18
**/

public class PostActivity extends AppCompatActivity {
        private EditText editText;                        //Edit post
        private Button mPostButton;
        private Button mbackButton;
        private ImageView postImage;
        private String postText;
        private Uri imageUri;
        private  String post_image_path;
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
            post_image_path = intent.getStringExtra("imagePath");
            imageUri = Uri.parse(post_image_path);
            postImage.setImageURI(imageUri);
        }
        View.OnClickListener mListener = new View.OnClickListener() {                  //Set listeners
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.post_button_forward:                            //Post
                        postText = editText.getText().toString();    //Get post text
                        try {
                            post();
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    case R.id.post_button_backward: //Return
                        Intent intent_back_Post_to_Main = new Intent(PostActivity.this,MainActivity.class) ; //Go back
                        startActivity(intent_back_Post_to_Main);
                        finish();
                        break;
                }
            }
        };
        public void post() throws IOException {
            Map<String, String> map = new HashMap<>();
            map.put("userId", UserData.getUserId()+"");
            map.put("location","location");
            map.put("content",postText);
            File file = new File(post_image_path);
            ArrayList<File> files = new ArrayList<File>();
            files.add(file);
            OkGo.post(HttpContent.Upload).isMultipart(true)
                    .params(map)
                    .tag(this)
                    .params("file",file)
                    .execute(new StringCallback() {

                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            //Upload Succeeds
                            Intent intent_Post_to_Main = new Intent(PostActivity.this,MainActivity.class) ; //Post done, return to main
                            startActivity(intent_Post_to_Main);
                        }

                    });
        }


    private File uri2File(Uri uri) throws IOException {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return new File(picturePath);
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