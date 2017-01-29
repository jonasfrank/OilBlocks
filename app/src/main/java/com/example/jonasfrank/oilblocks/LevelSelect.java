package com.example.jonasfrank.oilblocks;

import android.content.Intent;
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
    int clearedStages = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;

        int screenSpace = screenWidth / levelsInRow / 8;
        int section = screenSpace * 8;
        int square = screenSpace * 6;
        int margin = screenSpace * 1;
        int spacing = (screenWidth - (margin * levelsInRow * 8)) / 2;


        DisplayMetrics displayMetrics;
        displayMetrics = this.getResources().getDisplayMetrics();
        double tempDouble = ((600 / displayMetrics.density) + 0.5);
        float gridMarginTop = (float) tempDouble;

        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.activity_level_select);

        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(levelsInRow);
        grid.setRowCount(levelsInRow);
        grid.setY(gridMarginTop); // Mindre?
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams((levelsInRow * section), (levelsInCol * section));
        params1.setMargins(spacing, spacing, spacing, spacing);

        grid.setLayoutParams(params1);


        for (int i = 0; i < (levelsInRow * levelsInCol); i++) {
            final Button button = new Button(this);
            if (i < clearedStages) {
                button.setBackgroundResource(R.drawable.groundmetaldot);
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(square, square);
            params.setMargins(margin, margin, margin, margin);
            button.setLayoutParams(params);
            button.setText(Integer.toString(i + 1));
            button.setTextSize(25);
            button.setTextColor(0xff333333);
            button.setPadding(0, 0, 7, 17);
            grid.addView(button);
        }
        rLayout.addView(grid);
    }
        public void goToGame(String s) {

            Intent intent = new Intent(this, Game.class);
            intent.putExtra(EXTRA_MESSAGE, s);
            startActivity(intent);

    }


}
