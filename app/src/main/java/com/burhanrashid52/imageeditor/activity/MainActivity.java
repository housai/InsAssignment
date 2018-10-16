package com.burhanrashid52.imageeditor.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.burhanrashid52.imageeditor.R;
import com.burhanrashid52.imageeditor.fragment.Fragment1;
import com.burhanrashid52.imageeditor.fragment.Fragment2;
import com.burhanrashid52.imageeditor.fragment.Fragment3;
import com.burhanrashid52.imageeditor.fragment.Fragment4;
import com.burhanrashid52.imageeditor.utils.BottomBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
                .addItem(Fragment1.class,
                        "User Feed",
                        R.drawable.first_before,
                        R.drawable.first_after)
                .addItem(Fragment1.class,
                        "Discover",
                        R.drawable.second_before,
                        R.drawable.second_after)
                .addItem(Fragment4.class,
                        "Upload Photo",
                        R.drawable.third_before,
                        R.drawable.third_after)
                .addItem(Fragment2.class,
                        "Activity Feed",
                        R.drawable.fourth_before,
                        R.drawable.fourth_after)
                .addItem(Fragment3.class,
                        "Profile",
                        R.drawable.fifth_before,
                        R.drawable.fifth_after)
                .build();
    }
}
