package com.klein.instagram.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.klein.instagram.R;
import com.bumptech.glide.Glide;

import com.klein.instagram.activity.DiscoverActivity;
import com.klein.instagram.bean.UserBean;
import com.klein.instagram.helpers.UserData;
import com.klein.instagram.network.JsonCallback;
import com.klein.instagram.utils.OkGoUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sai on 2018/8/28.
 */
public class DiscoveryAdapter extends RecyclerView.Adapter<DiscoveryAdapter.ViewHolder> {

    private Context context;
    private List<UserBean> data;
    private UserBean userSearching;

    public DiscoveryAdapter(Context context, List<UserBean> list) {

        this.context = context;
        this.data = list;
        Toast.makeText(context,list.size()+"Discovery Adapter",Toast.LENGTH_LONG).show();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.discovery_result, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        UserBean user = data.get(position);
        userSearching = user;
        holder.sug_res_user_name.setText(user.getUsername());
        if(user.getProfilephoto().equals("") || user.getProfilephoto() == null){
            Glide.with(context).load("http://goo.gl/gEgYUd").into(holder.sug_userImage);
        } else {
            Glide.with(context).load(user.getProfilephoto()).into(holder.sug_userImage);
        }
        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                followUser();
            }
        });
    }

    public void followUser() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", UserData.getUserId().toString());
        map.put("followedId", userSearching.getId().toString());
        OkGoUtil.jsonPost(context, "http://10.12.170.91:8080/ssmtest/UserController/insertFollow", map, true, new JsonCallback() {

            @Override
            public void onSucess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("resultCode") == 200) {

                        Toast.makeText(context, "Followed" + "Success setText", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView sug_userImage;
        TextView sug_res_user_name;
        Button follow;

        public ViewHolder(View itemView) {
            super(itemView);
            follow = (Button) itemView.findViewById(R.id.follow);
            sug_userImage = (ImageView) itemView.findViewById(R.id.sug_userImage);
            sug_res_user_name = (TextView) itemView.findViewById(R.id.sug_res_user_name);
        }
    }
}


