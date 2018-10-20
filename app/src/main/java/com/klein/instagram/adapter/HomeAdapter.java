package com.klein.instagram.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.klein.instagram.R;
import com.bumptech.glide.Glide;
import com.klein.instagram.activity.CommentActivity;
import com.klein.instagram.R;
import com.klein.instagram.activity.CommentActivity;
import com.klein.instagram.bean.UserBean;
import com.klein.instagram.bean.UserPost;
import com.klein.instagram.network.HttpContent;
import com.klein.instagram.network.JsonCallback;
import com.klein.instagram.utils.OkGoUtil;
import com.klein.instagram.utils.UserData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by klein on 2018/8/28.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<UserPost> list;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    ViewPagerAdapter adapter;
    List<Map<String, Object>> data;
    private MyItemClickListener mItemClickListener;


    public HomeAdapter(Context context, ArrayList<UserPost> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view, mItemClickListener);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {

        final UserPost userPost = list.get(position);
        holder.user_name.setText(userPost.getUsername());
        holder.content.setText(userPost.getContent());
        holder.contentName.setText(userPost.getUsername());
        Glide.with(context).load("http://goo.gl/gEgYUd").into(holder.userImage);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher) // When imageView loads add in image
                .showImageForEmptyUri(R.drawable.ic_launcher) // if empty set image
                .showImageOnFail(R.drawable.ic_launcher) // if set image fails
                .cacheInMemory(true) // cache image in memory
                .cacheOnDisc(true) // cache image on disk
                .build();

        data = new ArrayList<Map<String, Object>>();
        Log.e("哈哈哈",HttpContent.uploadImage + userPost.getPhotourl());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", HttpContent.uploadImage + userPost.getPhotourl());
        map.put("view", new ImageView(context));
        data.add(map);
        adapter = new ViewPagerAdapter(data);
        holder.viewPager.setAdapter(adapter);
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> map = new HashMap<>();
                map.put("userId", UserData.getUserId()+"");
                map.put("postId", userPost.getPostId()+"");
                map.put("username", userPost.getUsername()+"");
                OkGoUtil.jsonPost(context, HttpContent.InsertLike, map, true, new JsonCallback() {

                    @Override
                    public void onSucess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt("resultCode") == 200) {
                                holder.favourite.setImageResource(R.drawable.dianzan_after);

                            } else {
                                Toast.makeText(context, jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
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
        });

        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("userId", userPost.getUserId()+"");
                intent.putExtra("username", userPost.getUsername());
                intent.putExtra("profilephoto", userPost.getProfilephoto());
                intent.putExtra("postId", userPost.getPostId()+"");
                intent.putExtra("content", userPost.getContent());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewPagerAdapter extends PagerAdapter {

        List<Map<String, Object>> viewLists;

        public ViewPagerAdapter(List<Map<String, Object>> lists) {
            viewLists = lists;
        }

        @Override
        public int getCount() {                                                                 //get size
            // TODO Auto-generated method stub
            return viewLists.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View view, int position, Object object)                       //destroy Item
        {
            ImageView image = (ImageView) viewLists.get(position).get("view");
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            ((ViewPager) view).removeView(image);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            ImageView iv = new ImageView(context);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            imageLoader.displayImage(viewLists.get(position).get("url").toString(), iv, options);
            container.addView(iv);
            return iv;
            }
        };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView userImage;
        TextView user_name;
        ViewPager viewPager;
        ImageView favourite;
        ImageView message;
        ImageView share;
        TextView likeNumber;
        TextView like;
        TextView contentName;
        TextView content;
        TextView  viewMore;
        private MyItemClickListener mListener;


        public ViewHolder(View itemView,MyItemClickListener listener) {
            super(itemView);
            favourite = (ImageView) itemView.findViewById(R.id.favourite);
            message = (ImageView) itemView.findViewById(R.id.message);
           // share = (ImageView) itemView.findViewById(R.id.share);
            userImage = (ImageView) itemView.findViewById(R.id.userImage);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            viewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
            contentName = (TextView) itemView.findViewById(R.id.contentName);
            content = (TextView) itemView.findViewById(R.id.content);
            //viewMore = (TextView) itemView.findViewById(R.id.viewMore);
            mListener = listener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getPosition());
                }
            }
        }

        public interface MyItemClickListener {
            void onItemClick(View view, int position);
        }
}


