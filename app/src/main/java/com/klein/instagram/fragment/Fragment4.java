package com.klein.instagram.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import com.klein.instagram.R;
import com.klein.instagram.activity.DiscoverActivity;

import java.io.File;
import java.io.FileNotFoundException;


public class Fragment4 extends Fragment implements View.OnClickListener {
    private View mView;
    private static final int PHOTO_REQUEST_CAREMA = 1;// Take photo
    private static final int PHOTO_REQUEST_GALLERY = 2;// Pick from gallery
    private static final int PHOTO_REQUEST_CUT = 3;// Result

    private ImageView iv_image;
    private Button album;
    private Button camera;
    private Uri imageUri;

    /* Profile photo name */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment4, container, false);
        iv_image = (ImageView) mView.findViewById(R.id.iv_image);
        album = (Button) mView.findViewById(R.id.album);
        camera = (Button) mView.findViewById(R.id.camera);
        album.setOnClickListener(this);
        camera.setOnClickListener(this);
        return mView;

    }

    /*
     * Get from gallery
     */
    public void gallery() {
        // Activate Android gallery and pick from it
        Intent intent = new Intent();
        // get Result from Activity with Request PHOTO_REQUEST_GALLERY
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);

    }

    /*
     * Get from camera
     */
    public void camera() {
        // Activate Android default camera
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // Check if external storage is available and store there
        if (hasSdcard()) {
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    PHOTO_FILE_NAME);
            // Create URI
//                Uri uri = Uri.fromFile(tempFile);
            Uri uri = FileProvider.getUriForFile(getContext(), "ins.klein.com.instagram.fileprovider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        // Get result from activity with request value PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);

    }

    /*
     * Crop image
     */
    private void crop(Uri uri) {
        // Crop intent
        Intent intent = new Intent("com.android.camera.action.CROP", null);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // Crop aspect ratio，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // After crop set image size
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// image format
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(uri.getPath()));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//output format

        intent.putExtra("noFaceDetection", true);// turn off faceDetection feature
        intent.putExtra("return-data", true);
        // get result for activity with request value PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /*
     * Check if SD card is mounted
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
            // Gallery returned
            if (data != null) {
                // get URI
                Uri uri = data.getData();
                String img_url = uri.getPath();

                ContentResolver cr = getContext().getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    /* Set imageView with bitmap */
                    iv_image.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Log.e("Exception", e.getMessage(), e);
                }

                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            // get from camera
            if (hasSdcard()) {
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(getActivity(), "SD card not found, cannot save!", Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // get after crop
            Bundle extras = data.getExtras();
//                if (data != null) {
//                    Bitmap bitmap = data.getParcelableExtra("data");
//                    this.iv_image.setImageBitmap(bitmap);
//                }
//                try {
//                    // delete temp file
//                    tempFile.delete();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");//convert to bitmap
                if (photo != null) {
                    // aCache.put("ShakeBgFromUser",photo);    //Save in cache, aCache is a caching framework
                    iv_image.setImageBitmap(photo);//set image
                }
            }


        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.album:
                gallery();
                break;
            case R.id.camera:
                camera();
                break;
        }
    }
}