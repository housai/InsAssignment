package com.klein.instagram.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.klein.instagram.R;
import com.klein.instagram.activity.DiscoverActivity;


public class Fragment2 extends Fragment{
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment2, container, false);

        Intent intent = new Intent(getActivity(), DiscoverActivity.class);
        startActivity(intent);


        return mView;

    }

}
