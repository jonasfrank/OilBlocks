package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "com.example.jonasfrank.savedatatest.MyPREFERENCES";
    public static final String CLEARED_STAGES_KEY = "clearedStagesKey";
    public static int clearedStages;
    public static SharedPreferences sharedPreferences;

    private static final int SPLASH_DURATION = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        clearedStages = sharedPreferences.getInt(CLEARED_STAGES_KEY, 0);
        if (clearedStages == 0) {
            clearedStages = 1;
        }

        //clearedStages = 13;

        Handler handler = new Handler();

        // run a thread after 2 seconds to start the home screen
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
                startActivity(new Intent(getApplicationContext(), StartScreen.class));
            }
        }, SPLASH_DURATION);


    }
}
