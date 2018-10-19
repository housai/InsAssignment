package com.klein.instagram.adapter;


import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.klein.instagram.R;
import com.bumptech.glide.Glide;

import com.klein.instagram.bean.UserBean;

import java.util.List;

/**
 * Created by Sai on 2018/8/28.
 */
public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {

    private Context context;
    private List<UserBean> data;

    public FollowingAdapter(Context context, List<UserBean> list) {

        this.context = context;
        this.data = list;
        Toast.makeText(context,list.size()+"Following Adapter",Toast.LENGTH_LONG).show();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.discovery_result, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {

        UserBean user = data.get(position);
        holder.following_user_name.setText(user.getUsername());
        if(user.getProfilephoto().equals("") || user.getProfilephoto() == null){
            Glide.with(context).load("http://goo.gl/gEgYUd").into(holder.following_userImage);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView following_userImage;
        ImageView follower_userImage;
        TextView following_user_name;
        TextView followeruser_name;


        public ViewHolder(View itemView) {
            super(itemView);
            following_userImage = (ImageView) itemView.findViewById(R.id.follwing_userImage);
            following_user_name = (TextView) itemView.findViewById(R.id.following_user_name);

            follower_userImage = (ImageView) itemView.findViewById(R.id.follower_userImage);
            followeruser_name = (TextView) itemView.findViewById(R.id.follower_user_name);
        }

    }
}


