package com.klein.instagram.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.klein.instagram.R;
import com.bumptech.glide.Glide;
import com.klein.instagram.activity.CommentActivity;
import com.klein.instagram.R;
import com.klein.instagram.activity.CommentActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by klein on 2018/8/28.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context context;
    private List<String> list;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    ViewPagerAdapter adapter;
    List<Map<String, Object>> data;


    public HomeAdapter(Context context, List<String> list) {
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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.user_name.setText("Nicolas");
        Glide.with(context).load("http://goo.gl/gEgYUd").into(holder.userImage);
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher) // 在ImageView加载过程中显示图片
                .showImageForEmptyUri(R.drawable.ic_launcher) // image连接地址为空时
                .showImageOnFail(R.drawable.ic_launcher) // image加载失败
                .cacheInMemory(true) // 加载图片时会在内存中加载缓存
                .cacheOnDisc(true) // 加载图片时会在磁盘中加载
                .build();
        adapter = new ViewPagerAdapter(data);
        holder.viewPager.setAdapter(adapter);
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.favourite.setImageResource(R.drawable.dianzan_after);
            }
        });

        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CommentActivity.class);

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
        public int getCount() {                                                                 //获得size
            // TODO Auto-generated method stub
            return viewLists.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View view, int position, Object object)                       //销毁Item
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

    public class ViewHolder extends RecyclerView.ViewHolder{
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


        public ViewHolder(View itemView) {
            super(itemView);
            favourite = (ImageView) itemView.findViewById(R.id.favourite);
            message = (ImageView) itemView.findViewById(R.id.message);
            share = (ImageView) itemView.findViewById(R.id.share);
            userImage = (ImageView) itemView.findViewById(R.id.userImage);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            viewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
            likeNumber = (TextView) itemView.findViewById(R.id.likeNumber);
            like = (TextView) itemView.findViewById(R.id.like);
            contentName = (TextView) itemView.findViewById(R.id.contentName);
            content = (TextView) itemView.findViewById(R.id.content);
            viewMore = (TextView) itemView.findViewById(R.id.viewMore);
        }

    }
}


