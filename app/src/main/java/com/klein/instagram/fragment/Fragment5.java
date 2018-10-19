package com.klein.instagram.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.klein.instagram.R;
import com.klein.instagram.adapter.HomeAdapter;
import com.klein.instagram.adapter.ProfilePhotoAdapter;
import com.klein.instagram.utils.XCRoundImageView;


public class Fragment5 extends Fragment {
    private View mView;
    private RecyclerView mVRecycler;
    private ProfilePhotoAdapter mAdapter;
    private List<String> mList;
    //private int click = 1;
    ImageView profile_photo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment5, container, false);
        XCRoundImageView imageview = mView.findViewById(R.id.roundImageView);
        profile_photo = mView.findViewById(R.id.profile_photo);
        profile_photo.setOnClickListener(new ImageListener());

        mVRecycler = (RecyclerView) mView.findViewById(R.id.recyclerview_profile);
        mVRecycler.setLayoutManager(new GridLayoutManager(this.getContext(), 3));
        mVRecycler.setHasFixedSize(true);
        mVRecycler.setNestedScrollingEnabled(false);
        mList = new ArrayList<>();
        for (int i = 1; i < 17; i++) {
            mList.add("text" + i);

        }
        mAdapter = new ProfilePhotoAdapter(this.getContext(), mList);
        mVRecycler.setAdapter(mAdapter);
        return mView;
    }


    private class ImageListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.profile_photo:
//                case R.id.profile_photo:
////                    click = 1;
//                    mVRecycler.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
//                    mList = new ArrayList<>();
//                    for (int i = 1; i < 17; i++) {
//                        mList.add("text" + i);
//
//                    }
//                    mAdapter = new ProfilePhotoAdapter(view.getContext(), mList);
//                    mVRecycler.setAdapter(mAdapter);
//                    break;
            }
        }
    }
}