package com.burhanrashid52.imageeditor.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.burhanrashid52.imageeditor.R;

import java.io.File;
import java.io.FileNotFoundException;


public class Fragment2 extends Fragment implements View.OnClickListener{
    private View mView;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private ImageView iv_image;
    private Button album;
    private Button camera;
    private Uri imageUri;

    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment2, container, false);
        iv_image = (ImageView) mView.findViewById(R.id.iv_image);
        album = (Button) mView.findViewById(R.id.album);
        camera = (Button) mView.findViewById(R.id.camera);
        album.setOnClickListener(this);
        camera.setOnClickListener(this);
        return mView;

    }
        /*
         * 从相册获取
         */
        public void gallery() {
            // 激活系统图库，选择一张图片
            Intent intent = new Intent();
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,PHOTO_REQUEST_GALLERY);

        }

        /*
         * 从相机获取
         */
        public void camera() {
            // 激活相机
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            // 判断存储卡是否可以用，可用进行存储
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                // 从文件中创建uri
//                Uri uri = Uri.fromFile(tempFile);
                Uri uri = FileProvider.getUriForFile(getContext(),"ins.klein.com.instagram.fileprovider", tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
            startActivityForResult(intent, PHOTO_REQUEST_CAREMA);

        }

        /*
         * 剪切图片
         */
        private void crop(Uri uri) {
            // 裁剪图片意图
            Intent intent = new Intent("com.android.camera.action.CROP",null);
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            // 裁剪框的比例，1：1
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // 裁剪后输出图片的尺寸大小
            intent.putExtra("outputX", 250);
            intent.putExtra("outputY", 250);

            intent.putExtra("outputFormat", "JPEG");// 图片格式
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(uri.getPath()));
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出图片的格式

            intent.putExtra("noFaceDetection", true);// 取消人脸识别
            intent.putExtra("return-data", true);
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
            startActivityForResult(intent, PHOTO_REQUEST_CUT);
        }

        /*
         * 判断sdcard是否被挂载
         */
        private boolean hasSdcard() {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == PHOTO_REQUEST_GALLERY) {
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    String img_url = uri.getPath();

                    ContentResolver cr = getContext().getContentResolver();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        /* 将Bitmap设定到ImageView */
                        iv_image.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.e("Exception", e.getMessage(),e);
                    }

                    crop(uri);
                }

            } else if (requestCode == PHOTO_REQUEST_CAREMA) {
                // 从相机返回的数据
                if (hasSdcard()) {
                    crop(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getActivity(), "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
                }

            } else if (requestCode == PHOTO_REQUEST_CUT) {
                // 从剪切图片返回的数据
                Bundle extras = data.getExtras();
//                if (data != null) {
//                    Bitmap bitmap = data.getParcelableExtra("data");
//                    this.iv_image.setImageBitmap(bitmap);
//                }
//                try {
//                    // 将临时文件删除
//                    tempFile.delete();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");//转换为Bitmap类型
                     if(photo!=null){
                        // aCache.put("ShakeBgFromUser",photo);    //保存在缓存里，ACache是我用的一个缓存框架
                         iv_image.setImageBitmap(photo);//展示
                     }
                }



            }

            super.onActivityResult(requestCode, resultCode, data);
        }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.album:
                gallery();
                break;
            case R.id.camera:
                camera();
                break;
        }
    }

}
