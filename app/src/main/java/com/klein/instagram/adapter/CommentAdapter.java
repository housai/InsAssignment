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
import com.klein.instagram.bean.UserComment;

import java.util.List;

/**
 * Created by Sai on 2018/8/28.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private List<UserComment> data;

    public CommentAdapter(Context context, List<UserComment> list) {

        this.context = context;
        this.data = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_list, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {

        UserComment userComment = data.get(position);
        holder.com_user_name.setText(userComment.getUsername());
        if(userComment.getProfilephoto().equals("") || userComment.getProfilephoto() == null){
            Glide.with(context).load("http://goo.gl/gEgYUd").into(holder.com_userImage);
        }
        holder.com_user_comment.setText(userComment.getComment());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView com_userImage;
        TextView com_user_name;
        TextView com_user_comment;


        public ViewHolder(View itemView) {
            super(itemView);

            com_userImage = (ImageView) itemView.findViewById(R.id.com_userImage);
            com_user_name = (TextView) itemView.findViewById(R.id.com_com_name);
            com_user_comment =(TextView) itemView.findViewById(R.id.com_txt);
        }

    }
}


