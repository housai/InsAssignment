package com.klein.instagram.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.klein.instagram.R;
import com.klein.instagram.fragment.Fragment1;
import com.klein.instagram.fragment.Fragment2;
import com.klein.instagram.fragment.Fragment4;
import com.klein.instagram.fragment.Fragment5;
import com.klein.instagram.fragment.Fragment3;
import com.klein.instagram.utils.BottomBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setFirstChecked(0);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
                .addItem(Fragment1.class,
                        "UserBean Feed",
                        R.drawable.first_before,
                        R.drawable.first_after)
                .addItem(Fragment2.class,
                        "Discover",
                        R.drawable.second_before,
                        R.drawable.second_after)
                .addItem(Fragment3.class,
                        "Upload Photo",
                        R.drawable.third_before,
                        R.drawable.third_after)
                .addItem(Fragment4.class,
                        "Activity Feed",
                        R.drawable.fourth_before,
                        R.drawable.fourth_after)
                .addItem(Fragment5.class,
                        "Profile",
                        R.drawable.fifth_before,
                        R.drawable.fifth_after)
                .build();
    }
}
