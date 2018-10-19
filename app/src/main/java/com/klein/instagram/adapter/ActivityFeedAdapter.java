package com.klein.instagram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.klein.instagram.R;
import com.klein.instagram.bean.ActivityFeedBean;

import java.util.List;

public class ActivityFeedAdapter extends RecyclerView.Adapter<ActivityFeedAdapter.ViewHolder> {
    private Context context;
    private List<ActivityFeedBean> data;

    public ActivityFeedAdapter(Context context, List<ActivityFeedBean> list) {

        this.context = context;
        this.data = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_feed_item, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        ActivityFeedBean act = data.get(position);
        //Set username
        String username = "Nicolas";
        holder.Act_user_name.setText(username);
//        if(comment.getUserId().equals(0) || comment.getUserId() == null){
        Glide.with(context).load("http://goo.gl/gEgYUd").into(holder.Act_user_img);
//        }
//        holder.com_com_name.setText(comment.getUsername());
        holder.Act_user_act.setText(username + " has recently liked " + act.getLikeCount() + " posts");
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Act_user_img;
        TextView Act_user_name;
        TextView Act_user_act;


        public ViewHolder(View itemView) {
            super(itemView);

            Act_user_img = (ImageView) itemView.findViewById(R.id.Act_user_img);
            Act_user_name = (TextView) itemView.findViewById(R.id.Act_user_name);
            Act_user_act =(TextView) itemView.findViewById(R.id.Act_user_activity);
        }

    }
}

