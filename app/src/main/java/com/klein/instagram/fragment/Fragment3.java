package com.klein.instagram.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.klein.instagram.EditImageActivity;
import com.klein.instagram.R;
import com.klein.instagram.activity.MainActivity;

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

}
