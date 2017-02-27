package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class LevelSelect extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.jonasfrank.oilblocks.MESSAGE";
    int clearedStages;
    int lastPlayedStage;
    int levelsInRow = 4;
    int levelsInCol = 5;
    int screenWidth;
    int screenHeight;
    float leftOverBottom = 0;
    Level level;
    ArrayList<Button> buttonList = new ArrayList<Button>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        clearedStages = MainActivity.clearedStages;

        Intent intent = getIntent();
        String temp = intent.getStringExtra(LevelSelect.EXTRA_MESSAGE);
        Log.d("tag", " test teste test temp " + temp + " " + lastPlayedStage);
        if(temp != null) {
            lastPlayedStage = Integer.parseInt(temp);
        }



        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        int screenSpace = screenWidth / (levelsInRow + 1);

        int square = screenSpace;
        int margin = screenSpace / (levelsInRow + 1);

        level = new Level();

        DisplayMetrics displayMetrics;
        displayMetrics = this.getResources().getDisplayMetrics();
        double tempDouble = ((600 / displayMetrics.density) + 0.5);

        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.activity_level_select);



        for (int i = 0; i < (level.gameLevel.length); i++) {
            final Button button = new Button(this);

            if (i < clearedStages) {
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


           /* Resources resources = this.getResources();
            DisplayMetrics textSizeDP = resources.getDisplayMetrics();*/
            //float dp = 25 / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);



            button.setText(Integer.toString(i + 1));
            button.setTextSize(getResources().getDimension(R.dimen.level_select_button_size));
            //button.setTextSize(25);
            button.setTextColor(0xff333333);
            button.setTypeface(Typeface.SERIF, Typeface.NORMAL);
            button.setPadding(0, 0, 7, 17);

            /**
             * Räknar ut start position på blocken utifrån indexnummer
             */
            int levelSelectPage = i / (levelsInCol*levelsInRow);

            int startIPosAll = i + 1;
            double startYDouble = startIPosAll / 4.0;      //vet inte hur jag ska få levelsInRow till 4.0

            int startY = (int)Math.ceil(startYDouble);
            int startX = startIPosAll - ((startY - 1) * levelsInRow);

            button.setLayoutParams(new FrameLayout.LayoutParams(square, square));

            float density = this.getResources().getDisplayMetrics().density;

            float blockTotalHeight = (levelsInCol * square) + (levelsInCol * margin);
            float marginTop = (screenHeight - blockTotalHeight) / 2;

            button.setX(((startX - 1) * square) + ((margin * 1) * startX) + (screenWidth * levelSelectPage));
            button.setY(((startY - 1) * square) + ((margin * 1) * startY) + (marginTop - margin - margin) - (screenHeight * levelSelectPage) + (leftOverBottom * levelSelectPage) + ((marginTop - margin - margin) * levelSelectPage));

            if(i == 20 - 1) {
                leftOverBottom = screenHeight - (button.getY() + square);
            }


            buttonList.add(button);
            rLayout.addView(button);
            if(clearedStages > levelsInCol*levelsInRow) {
                setClearedStagesOnScreen(i);
            }
        }
        setButtonStyle();
    }

    public void setClearedStagesOnScreen(int i){
        int setclearedStagesSelectPage = (clearedStages - 1) / (levelsInCol*levelsInRow);
        int setlastPlayedStageSelectPage = (lastPlayedStage - 1) / (levelsInCol*levelsInRow);

        Button thisButton = buttonList.get(i);
        float thisButtonX = thisButton.getX();
        if(lastPlayedStage == 0) {
            thisButton.setX(thisButtonX - (screenWidth * setclearedStagesSelectPage));
        }else{
            thisButton.setX(thisButtonX - (screenWidth * setlastPlayedStageSelectPage));
        }
    }

    public void setButtonStyle(){
        ImageButton buttonB = (ImageButton)findViewById(R.id.level_Backwards);
        ImageButton buttonF = (ImageButton)findViewById(R.id.level_Forward);
        if (buttonList.get(0).getX() > 0) {
            buttonB.setAlpha((float) 0.5);
            buttonB.setEnabled(false);
        }else{
            buttonB.setAlpha((float) 1);
            buttonB.setEnabled(true);
        }

        if (buttonList.get(buttonList.size()-1).getX() < screenWidth) {
            buttonF.setAlpha((float) 0.5);
            buttonF.setEnabled(false);
        }else{
            buttonF.setAlpha((float) 1);
            buttonF.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LevelSelect.this, StartScreen.class);
        startActivity(intent);
    }

    public void levelForward(View view){
        Log.d("tag", "levelselect levelForward");
        if (buttonList.get(buttonList.size()-1).getX() > screenWidth ) {
            for (int i = 0; i < buttonList.size(); i++) {
                Button thisButton = buttonList.get(i);
                float thisButtonX = thisButton.getX();
                thisButton.setX(thisButtonX - screenWidth);
            }
        }
        setButtonStyle();
    }

    public void levelBackwards(View view) {
        Log.d("tag", "levelselect levelBackwards");
        if (buttonList.get(0).getX() < 0 ){
            for (int i = 0; i < buttonList.size(); i++) {
                Button thisButton = buttonList.get(i);
                float thisButtonX = thisButton.getX();
                thisButton.setX(thisButtonX + screenWidth);
            }
        }
        setButtonStyle();
    }

    public void goToGame(String s) {

        Intent intent = new Intent(this, Game.class);
        intent.putExtra(EXTRA_MESSAGE, s);
        startActivity(intent);
    }
}
