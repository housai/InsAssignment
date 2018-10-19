package com.klein.instagram.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import com.klein.instagram.EditImageActivity;
import com.klein.instagram.R;
import com.klein.instagram.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Fragment1 extends Fragment implements View.OnClickListener{
    private RecyclerView mVRecycler;
    private HomeAdapter mAdapter;
    private List<String> mList;
    private View mView;
    private ImageView take_photo;
    private static final int CAMERA_REQUEST = 52;
    private Button sort;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment1, container, false);
        take_photo = mView.findViewById(R.id.take_photo);
        sort = (Button) mView.findViewById(R.id.button_sort);
        sort.setOnClickListener(this);
        take_photo.setOnClickListener(this);
        mVRecycler = (RecyclerView) mView.findViewById(R.id.recyclerview);
        mVRecycler.setHasFixedSize(true);
        mVRecycler.setNestedScrollingEnabled(false);
        mVRecycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        mList = new ArrayList<>();
        for (int i = 1; i < 17; i++) {
            mList.add("text" + i);
        }


        //Home Adapter
        mAdapter = new HomeAdapter(this.getContext(), mList);
        mVRecycler.setAdapter(mAdapter);
        return mView;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            case R.id.button_sort:
//                Intent sort_location_Intent = new Intent( SortActivity.class);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST:
                    Intent intent=new Intent(getActivity(), EditImageActivity.class);
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    intent.putExtra("camera", photo);
                    startActivity(intent);
                    break;

            }
        }
    }

}
