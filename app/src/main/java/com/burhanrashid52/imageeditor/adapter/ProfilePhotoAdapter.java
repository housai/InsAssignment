package com.burhanrashid52.imageeditor.adapter;

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

import com.burhanrashid52.imageeditor.R;

/**
 * Created by klein on 2018/8/28.
 */
public class ProfilePhotoAdapter extends RecyclerView.Adapter<ProfilePhotoAdapter.ViewHolder> {

    private Context context;
    private List<String> list;
    List<Map<String, Object>> data;


    public ProfilePhotoAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        data = getData();
    }

    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> mdata = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", "http://img2.duitang.com/uploads/item/201207/19/20120719132725_UkzCN.jpeg");
        map.put("view", new ImageView(context));
        mdata.add(map);

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("url", "http://img4.duitang.com/uploads/item/201404/24/20140424195028_vtvZu.jpeg");
        map1.put("view", new ImageView(context));
        mdata.add(map1);

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("url", "http://www.mangowed.com/uploads/allimg/130425/572-130425105311304.jpg");
        map3.put("view", new ImageView(context));
        mdata.add(map3);
        return  mdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.profile_photo, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(context).load("http://goo.gl/gEgYUd").into(holder.profile_photo);

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


