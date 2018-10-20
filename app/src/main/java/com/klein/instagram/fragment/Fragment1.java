package com.klein.instagram.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.klein.instagram.EditImageActivity;
import com.klein.instagram.R;
import com.klein.instagram.activity.CommentActivity;
import com.klein.instagram.activity.LoginActivity;
import com.klein.instagram.activity.MainActivity;
import com.klein.instagram.activity.PostActivity;
import com.klein.instagram.adapter.HomeAdapter;
import com.klein.instagram.bean.Post;
import com.klein.instagram.bean.UserBean;
import com.klein.instagram.bean.UserPost;
import com.klein.instagram.network.HttpContent;
import com.klein.instagram.network.JsonCallback;
import com.klein.instagram.utils.OkGoUtil;
import com.klein.instagram.utils.UserData;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class Fragment1 extends Fragment implements View.OnClickListener{
    private RecyclerView mVRecycler;
    private HomeAdapter mAdapter;
    private List<String> mList;
    private View mView;
    private ImageView take_photo;
    private static final int CAMERA_REQUEST = 52;
    private Button sort;
    private Button sortbyTime;
    private Button sortbyLocation;
    private Button cancel_sort;
    private ArrayList<UserPost> upList = new ArrayList<UserPost>();
    private Dialog mCameraDialog;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment1, container, false);
        take_photo = mView.findViewById(R.id.take_photo);
        take_photo.setOnClickListener(this);
        mVRecycler = (RecyclerView) mView.findViewById(R.id.recyclerview);
        mVRecycler.setHasFixedSize(true);
        mVRecycler.setNestedScrollingEnabled(false);
        mVRecycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        // 设置sort相关
        sort = (Button) mView.findViewById(R.id.button_sort);
        sort.setOnClickListener(this);
//        sortbyTime = (Button) mView.findViewById(R.id.but_sortByTime);
//        sortbyLocation = (Button) mView.findViewById(R.id.but_sortByLocation);
//        cancel_sort = (Button) mView.findViewById(R.id.cancel_Sort);
        initData();
        //Home Adapter

        return mView;

    }


    public void initData(){
        Map<String, String> map = new HashMap<>();
        map.put("userId", UserData.getUserId()+"");
        OkGoUtil.jsonPost(getContext(), HttpContent.SelectFollowPostByUserId, map, true, new JsonCallback() {


            @Override
            public void onSucess(JSONObject jsonObject) {

                try {
                    if (jsonObject.getInt("resultCode") == 200) {
                        JSONArray arr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < arr.length(); i++) {
                            UserPost up = new Gson().fromJson(arr.getString(i), UserPost.class);
                            upList.add(up);
                        }
                        mAdapter = new HomeAdapter(getContext(), upList);
                        mVRecycler.setAdapter(mAdapter);
                    } else {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_photo:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            case R.id.button_sort:
                setDialog();
                break;
            case R.id.but_sortByTime:
                sortByTime();
                mCameraDialog.hide();
                break;
            case R.id.but_sortByLocation:
                sortByLocation();
                mCameraDialog.hide();
                break;
            case R.id.cancel_Sort:
                mCameraDialog.hide();
                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST:
                    Intent intent=new Intent(getActivity(), EditImageActivity.class);
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    intent.putExtra("camera", photo);
                    startActivity(intent);
                    break;

            }
        }
    }

    private void setDialog(){
        mCameraDialog = new Dialog(getActivity(), R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.sort, null);
        root.findViewById(R.id.but_sortByTime).setOnClickListener(this);
        root.findViewById(R.id.but_sortByLocation).setOnClickListener(this);
        root.findViewById(R.id.cancel_Sort).setOnClickListener(this);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.width = (int) getResources().getDisplayMetrics().widthPixels;
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f;
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    public void sortByTime(){
        Map<String, String> map = new HashMap<>();
        map.put("userId",UserData.getUserId()+"");
        OkGoUtil.jsonPost(getActivity(), HttpContent.SelectPostByUserIdSortByTime, map, true, new JsonCallback() {

            @Override
            public void onSucess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("resultCode") == 200) {
                        //display the sorted data from the server(by time)
                        upList = new ArrayList<UserPost>();
                        JSONArray arr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < arr.length(); i++) {
                            UserPost up = new Gson().fromJson(arr.getString(i), UserPost.class);
                            upList.add(up);
                        }
                        mAdapter = new HomeAdapter(getContext(), upList);
                        mVRecycler.setAdapter(mAdapter);

                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }

    public void sortByLocation(){
        Map<String, String> map = new HashMap<>();
        map.put("userId",UserData.getUserId()+"");
        OkGoUtil.jsonPost(getActivity(), HttpContent.SelectPostByUserIdSortByLocation, map, true, new JsonCallback() {

            @Override
            public void onSucess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("resultCode") == 200) {
                        upList = new ArrayList<UserPost>();
                        JSONArray arr = jsonObject.getJSONArray("data");
                        //display the sorted data from the server(by location)

                        for (int i = 0; i < arr.length(); i++) {
                            UserPost up = new Gson().fromJson(arr.getString(i), UserPost.class);
                            upList.add(up);
                        }

                        mAdapter = new HomeAdapter(getContext(), upList);
                        mVRecycler.setAdapter(mAdapter);
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }

}

