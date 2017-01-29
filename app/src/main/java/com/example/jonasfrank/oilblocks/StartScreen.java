package com.example.jonasfrank.oilblocks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);



    }

    public void selectLevel(View view) {
        Intent intent = new Intent(this, LevelSelect.class);
        startActivity(intent);
    }

    public void goToInfo(View view) {
        Intent intent = new Intent(this, Info.class);
        startActivity(intent);
    }
}
