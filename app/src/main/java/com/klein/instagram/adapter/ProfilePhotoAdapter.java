package com.klein.instagram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.klein.instagram.R;
import com.klein.instagram.bean.Post;
import com.klein.instagram.bean.UserBean;
import com.klein.instagram.bean.UserPost;
import com.klein.instagram.network.HttpContent;

/**
 * Created by klein on 2018/8/28.
 */
public class ProfilePhotoAdapter extends RecyclerView.Adapter<ProfilePhotoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Post> list;


    public ProfilePhotoAdapter(Context context,ArrayList<Post> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.profile_photo, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = list.get(position);
        Glide.with(context).load(HttpContent.uploadImage+post.getPhotourl()).into(holder.profile_photo);

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_photo;

        public ViewHolder(View itemView) {
            super(itemView);
            profile_photo = (ImageView) itemView.findViewById(R.id.profile_photo);
        }
    }
}


