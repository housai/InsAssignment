package com.klein.instagram.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.klein.instagram.EditImageActivity;
import com.klein.instagram.R;
import com.klein.instagram.activity.MainActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


public class Fragment3 extends Fragment{
    private View mView;
    private static final int RESULT_PICK = 100;
    private static int count = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment3, container, false);
        init();
        return mView;

    }

    public void init(){

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    RESULT_PICK);

        }else {
            //权限已经被授予，在这里直接写要执行的相应方法即可
            startPickPhoto();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK:
                    try {
                        Intent intent=new Intent(getActivity(), EditImageActivity.class);
                        Uri uri = data.getData();
//                        upload(uri);
                        intent.putExtra("gallery", uri);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

            }
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
                //Upload Succeeds
            }


            @Override
            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                //Return upload progress, should be returned to main thread and update UI
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


    private void startPickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), RESULT_PICK);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {

            case RESULT_PICK:
                // 如果权限被拒绝，grantResults 为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //选择图片
                    startPickPhoto();
                } else {
                    Toast.makeText(getContext(), "需要读取权限", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

}
