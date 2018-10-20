package com.klein.instagram.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.klein.instagram.R;
import com.klein.instagram.activity.MainActivity;
import com.klein.instagram.adapter.ActivityFeedAdapter;
import com.klein.instagram.bean.ActivityFeedBean;
import com.klein.instagram.network.HttpContent;
import com.klein.instagram.network.JsonCallback;
import com.klein.instagram.utils.OkGoUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.klein.instagram.utils.UserData;


public class Fragment4 extends Fragment implements View.OnClickListener {
    private RecyclerView mVRecycler;
    private ActivityFeedAdapter mAdapter;
    private List<ActivityFeedBean> activityList;
    private View mView;
    private Button back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment4, container, false);
        back = (Button) mView.findViewById(R.id.act_back);
        back.setOnClickListener(this);
        mVRecycler = (RecyclerView) mView.findViewById(R.id.activityRecyclerView);
        mVRecycler.setHasFixedSize(true);
        mVRecycler.setNestedScrollingEnabled(false);
        mVRecycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        activityList = new ArrayList<ActivityFeedBean>();
//        getFollowers();
        getActivityFeed();

        return mView;
    }

//    public void getFollowers() {
//        Map<String, String> map = new HashMap<>();
//        map.put("followedId", myId.toString());
//        OkGoUtil.jsonPost(this.getContext(), HttpContent.SelectFollowByFollowedId, map, true, new JsonCallback() {
//
//            @Override
//            public void onSucess(JSONObject jsonObject) {
//                try {
//                    if (jsonObject.getInt("resultCode") == 200) {
//                        JSONArray arr = jsonObject.getJSONArray("data");
//                        for (int i = 0; i < arr.length(); i++) {
//                            ActivityFeedBean act = new Gson().fromJson(arr.getString(i), ActivityFeedBean.class);
//                            act.setFollower(true);
//                            actList.add(act);
//                        }
//                        mAdapter.notifyDataSetChanged();
//                    }else{
//                        //no followers exist, do nothing
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onError(Exception e) {
//                Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
//            }
//
//        });
//
//    }

    public void getActivityFeed() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", UserData.getUserId()+"");
        OkGoUtil.jsonPost(this.getContext(), HttpContent.GetFollowActivityByUserId, map, true, new JsonCallback() {

            @Override
            public void onSucess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("resultCode") == 200) {
                        JSONArray arr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < arr.length(); i++) {
                            ActivityFeedBean act = new Gson().fromJson(arr.getString(i), ActivityFeedBean.class);

                            act.setFollower(false);
                            if (act.getLikeCount() > 0) {
                                activityList.add(act);
                            }

                        }
                        JSONArray arr1 = jsonObject.getJSONArray("data1");
                        for (int i = 0; i < arr1.length(); i++) {
                            ActivityFeedBean act = new Gson().fromJson(arr1.getString(i), ActivityFeedBean.class);
                            act.setFollower(true);
                            activityList.add(act);
                        }
                        mAdapter = new ActivityFeedAdapter(getContext(), activityList);
                        mVRecycler.setAdapter(mAdapter);
                    }else{
                        //no activity exists, do nothing
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
            }

        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_back:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
        }
    }

}