package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LevelSelect extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.jonasfrank.oilblocks.MESSAGE";
    int levelsInRow = 4;
    int levelsInCol = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        int screenSpace = screenWidth / (levelsInRow + 1);
        int section = screenSpace * 8;
        int square = screenSpace;
        int margin = screenSpace / (levelsInRow + 1);
        int spacing = (screenWidth - (margin * levelsInRow * 8)) / 2;

        Log.d("tag", "testtestetstetstetste" + margin);


        DisplayMetrics displayMetrics;
        displayMetrics = this.getResources().getDisplayMetrics();
        double tempDouble = ((600 / displayMetrics.density) + 0.5);
        float gridMarginTop = (float) tempDouble;

        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.activity_level_select);
        //RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.level_wraper);
        //rLayout.setLayoutParams(new FrameLayout.LayoutParams(screenWidth,(levelsInCol * section) + section));

        /*GridLayout grid = new GridLayout(this);
        grid.setColumnCount(levelsInRow);
        grid.setRowCount(levelsInRow);
        grid.setY(gridMarginTop); // Mindre?



        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams((levelsInRow * section), (levelsInCol * section));
        params1.setMargins(spacing, spacing, spacing, spacing);

        grid.setLayoutParams(params1);*/


        for (int i = 0; i < (levelsInRow * levelsInCol); i++) {
            final Button button = new Button(this);

            if (i < MainActivity.clearedStages) {
                button.setBackgroundResource(R.drawable.button_custom_level);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String temp = button.getText().toString();
                        goToGame(temp);
                    }
                });
            } else {
                button.setBackgroundResource(R.drawable.groundsolid);
            }

            //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(square, square);
            //params.setMargins(margin, margin, margin, margin);
            //button.setLayoutParams(params);

            button.setText(Integer.toString(i + 1));
            button.setTextSize(25);
            button.setTextColor(0xff333333);
            button.setTypeface(Typeface.SERIF, Typeface.NORMAL);
            button.setPadding(0, 0, 7, 17);

            /**
             * Räknar ut start position på blocken utifrån indexnummer
             */
            int startIPosAll = i + 1;
            double startYDouble = startIPosAll / 4.0;      //vet inte hur jag ska få levelsInRow till 4.0

            int startY = (int)Math.ceil(startYDouble);
            int startX = startIPosAll - ((startY - 1) * levelsInRow);
            //square = screenWidth / levelsInRow;

            button.setLayoutParams(new FrameLayout.LayoutParams(square, square));

            float density = this.getResources().getDisplayMetrics().density;
            float headerHeightDP = 50 * density;

            float blockTotalHeight = (levelsInCol * square) + (levelsInCol * margin);
            float marginTop = (screenHeight - blockTotalHeight) / 2;

            button.setX(((startX - 1) * square) + ((margin * 1) * startX));
            button.setY(((startY - 1) * square) + ((margin * 1) * startY) + marginTop - margin -margin);


            rLayout.addView(button);
        }
        //rLayout.addView(grid);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LevelSelect.this, StartScreen.class);
        startActivity(intent);
    }

    public void goToGame(String s) {

        Intent intent = new Intent(this, Game.class);
        intent.putExtra(EXTRA_MESSAGE, s);
        startActivity(intent);
    }
}
