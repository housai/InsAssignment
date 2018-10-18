package com.klein.instagram.utils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.DisplayMetrics;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

public class PhotoUtil {
    public static final int NONE = 0;
    public static final String IMAGE_UNSPECIFIED = "image/*";//Any image type
    public static final int PHOTOGRAPH = 1;// Photo
    public static final int PHOTOZOOM = 2; // Crop
    public static final int PHOTORESOULT = 3;// Result
    public static final int PICTURE_HEIGHT = 500;
    public static final int PICTURE_WIDTH = 500;
    public static String imageName;

    /**
     * Displayed width and height
     * @param context
     */
    public static DisplayMetrics getScreenWH(Context context) {
        DisplayMetrics dMetrics = new DisplayMetrics();
        dMetrics = context.getResources().getDisplayMetrics();
        return dMetrics;
    }
    /**
     * Select image from album
     * @param activity
     */
    public static void selectPictureFromAlbum(Activity activity){
        // Use system album
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_UNSPECIFIED);

        // Use crop feature
        activity.startActivityForResult(intent, PHOTOZOOM);
    }

    /**
     * Pick image to upload
     * @param fragment
     */
    public static void selectPictureFromAlbum(Fragment fragment){
        // Activate system album
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_UNSPECIFIED);

        // Use crop feature
        fragment.startActivityForResult(intent, PHOTOZOOM);
    }

    /**
     * Take photo
     * @param activity
     */
    public static void photograph(Activity activity){
        imageName = File.separator + getStringToday() + ".jpg";

        // Use system camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String status = Environment.getExternalStorageState();
        if(status.equals(Environment.MEDIA_MOUNTED)){
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), imageName)));
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    activity.getFilesDir(), imageName)));
        }
        activity.startActivityForResult(intent, PHOTOGRAPH);
    }

    /**
     * Photograph
     * @param fragment
     */
    public static void photograph(Fragment fragment){
        imageName = "/" + getStringToday() + ".jpg";

        // Use system camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String status = Environment.getExternalStorageState();
        if(status.equals(Environment.MEDIA_MOUNTED)){
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    Environment.getExternalStorageDirectory(), imageName)));
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                    fragment.getActivity().getFilesDir(), imageName)));
        }
        fragment.startActivityForResult(intent, PHOTOGRAPH);
    }

    /**
     * Camera crop
     * @param activity
     * @param uri
     * @param height
     * @param width
     */
    public static void startPhotoZoom(Activity activity,Uri uri,int height,int width) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY is width height ratio
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY crop height and weight
        intent.putExtra("outputX", height);
        intent.putExtra("outputY", width);
        intent.putExtra("noFaceDetection", true); //Turn off face detection
        intent.putExtra("return-data", true);//If true return bitmap
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);//output file
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(intent, PHOTORESOULT);
    }

    /**
     * Crop image
     * @param activity
     * @param uri	  	original image URI
     * @param height  	result height
     * @param width	  	result width
     * @param destUri 	crop image URI
     */
    public static void startPhotoZoom(Activity activity,Uri uri,int height,int width,Uri destUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY height width ratio
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY cropped image height width
        intent.putExtra("outputX", height);
        intent.putExtra("outputY", width);
        intent.putExtra("noFaceDetection", true); //Turn off facedetection
        intent.putExtra("return-data", false);//If true return bitmap
        intent.putExtra(MediaStore.EXTRA_OUTPUT, destUri);//output file
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(intent, PHOTORESOULT);
    }

    /**
     * Crop
     * @param fragment
     * @param uri
     * @param height
     * @param width
     */
    public static void startPhotoZoom(Fragment fragment,Uri uri,int height,int width) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY width height ratio
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", height);
        intent.putExtra("outputY", width);
        intent.putExtra("return-data", true);
        fragment.startActivityForResult(intent, PHOTORESOULT);
    }

    /**
     * Get current system time and format it
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * Create path
     * @param context
     * @return
     */
    public static String getPath(Context context){
        String path = null;
        File file = null;
        long tag = System.currentTimeMillis();
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            //Check is SD card is mounted
            path = Environment.getExternalStorageDirectory() + File.separator +"myimages/";
            file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            path = Environment.getExternalStorageDirectory() + File.separator +"myimages/"+ tag + ".png";
        }else{
            path = context.getFilesDir() + File.separator +"myimages/";
            file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            path = context.getFilesDir() + File.separator +"myimages/"+ tag + ".png";
        }
        return path;
    }

    /**
     * Get bitmap by ratio
     * @param path
     * @param w
     * @param h
     * @return
     */
    public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // If true only get image bounds
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // zoom
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    /**
     * Get original image bitmap
     * @param path
     * @return
     */
    public static Bitmap convertToBitmap2(String path) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // If true only decode bounds
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // return empty
        BitmapFactory.decodeFile(path, opts);
        return  BitmapFactory.decodeFile(path, opts);
    }
}
