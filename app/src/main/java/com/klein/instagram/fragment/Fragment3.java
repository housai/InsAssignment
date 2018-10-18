package com.klein.instagram.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.klein.instagram.EditImageActivity;
import com.klein.instagram.R;
import com.klein.instagram.activity.LoginActivity;
import com.klein.instagram.activity.MainActivity;
import com.klein.instagram.network.JsonCallback;
import com.klein.instagram.utils.OkGoUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


public class Fragment3 extends Fragment{
    private View mView;
    private static final int PICK_REQUEST = 53;
    private static int count = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment3, container, false);
        return mView;

    }

    public void init(){
        count++;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_REQUEST:
                    try {

                        Intent intent=new Intent(getActivity(), EditImageActivity.class);
                        Uri uri = data.getData();
                        upload(uri);
                        intent.putExtra("gallery", uri);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(count==0){
            init();
        }else{
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            count = 0;
        }

    }

    public void upload(Uri uri) throws IOException {

        Map<String, String> map = new HashMap<>();
        map.put("userId","1");
        map.put("location","1ssss");
        File file = uri2File(uri);
        Toast.makeText(getContext(),file.length()+"",Toast.LENGTH_LONG).show();
        ArrayList<File> files = new ArrayList<File>();
        files.add(file);
        OkGo.post("http://10.12.170.91:8080/ssmtest/fileUploadControllerAPI/upload").isMultipart(true)
                .params(map)
                .tag(this)
                .params("file",file)
                .execute(new StringCallback() {

            @Override
            public void onSuccess(String s, Call call, Response response) {
                //上传成功
            }


            @Override
            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                //这里回调上传进度(该回调在主线程,可以直接更新ui)
            }
        });
    }

    private File uri2File(Uri uri) throws IOException {
        Toast.makeText(getContext(),uri.toString(),Toast.LENGTH_LONG).show();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        Toast.makeText(getContext(),picturePath,Toast.LENGTH_LONG).show();
        return new File(picturePath);
    }

}
