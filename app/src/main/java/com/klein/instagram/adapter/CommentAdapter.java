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
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private Context context;
    private List<UserBean> data;

    public CommentAdapter(Context context, List<UserBean> list) {

        this.context = context;
        this.data = list;
        Toast.makeText(context,list.size()+"Comment Adapter",Toast.LENGTH_LONG).show();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_list, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {

        UserBean user = data.get(position);
        holder.com_user_name.setText(user.getUsername());
        if(user.getProfilephoto().equals("") || user.getProfilephoto() == null){
            Glide.with(context).load("http://goo.gl/gEgYUd").into(holder.com_userImage);
        }
//        holder.com_user_name.setText(user.getComment());
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


