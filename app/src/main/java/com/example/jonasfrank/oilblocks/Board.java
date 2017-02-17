package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.shapes.Shape;
import android.renderscript.Sampler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class Board extends RelativeLayout{

    public int screenWidth;
    public int blockNumberInRow = 8;
    public int utilityNumberInRow = 6;
    public int blockSize;
    public float boundariesHeightDP;
    public int levelNumber;
    public Level level;
    public Block block;
    public Ball ball;
    public int startPos;
    public float startPosX;
    public float startPosY;
    ArrayList<Block> blockList = new ArrayList<Block>();
    ArrayList<Integer> startUtilityList = new ArrayList<Integer>();
    ArrayList<Block> utilityList = new ArrayList<Block>();


    int utilCounter;
    int softCounter;
    int maxSoftCounter;
    int boostRCounter;
    int boostLCounter;
    int rampULCounter;
    int rampURCounter;
    int rampDLCounter;
    int rampDRCounter;

    TextView softUtilCounter = new TextView(getContext());
    TextView boostRUtilCounter = new TextView(getContext());
    TextView boostLUtilCounter = new TextView(getContext());
    TextView rampULUtilCounter = new TextView(getContext());
    TextView rampURUtilCounter = new TextView(getContext());
    TextView rampDLUtilCounter = new TextView(getContext());
    TextView rampDRUtilCounter = new TextView(getContext());

    float[] softXY = new float[2];
    float[] boostRXY = new float[2];
    float[] boostLXY = new float[2];
    float[] rampULXY = new float[2];
    float[] rampURXY = new float[2];
    float[] rampDLXY = new float[2];
    float[] rampDRXY = new float[2];


    public ArrayList location;

    public Board(Context context){
        super(context);
    }

    public void setBoard(Context context, int startScreenWidth, int levelNumber, Ball startBall, Level inLevel){
        ball = startBall;
        screenWidth = startScreenWidth;

        setLayoutParams(new FrameLayout.LayoutParams(screenWidth, ViewGroup.LayoutParams.MATCH_PARENT));

        //setColumnCount(blockNumberInRow);
        //setRowCount(blockNumberInRow);

        level = inLevel;
        //Level level = new Level();

        for (int i = 0; i < blockNumberInRow; i++) {

            for (int j = 0; j < blockNumberInRow; j++) {

                Level.B blockType = level.gameLevel[levelNumber - 1][i][j];
                switch (blockType){
                    case EMPTY:
                        block = new BlockEmpty(context);
                        break;
                    case EMPTYDISABLE:
                        block = new BlockEmptyDisable(context);
                        break;
                    case SOFT:
                        block = new BlockSoft(context);
                        break;
                    case SOLID:
                        block = new BlockSolid(context);
                        break;
                    case GOAL:
                        block = new BlockGoal(context);
                        break;
                    case START:
                        block = new BlockStart(context);
                        break;
                    case BOOSTR:
                        block = new BlockBoostR(context);
                        break;
                    case BOOSTL:
                        block = new BlockBoostL(context);
                        break;
                    case RAMPUL:
                        block = new BlockRampUL(context);
                        break;
                    case RAMPUR:
                        block = new BlockRampUR(context);
                        break;
                    case RAMPDL:
                        block = new BlockRampDL(context);
                        break;
                    case RAMPDR:
                        block = new BlockRampDR(context);
                        break;
                    default:
                        block = new BlockEmpty(context);
                        break;
                }

                blockList.add(block);       //lägger till blocket i arraylisten
                block.setBlock(screenWidth, blockNumberInRow, this);       //"ritar" blocket
            }
        }


        for (int i = 0; i < level.gameUtility[levelNumber - 1].length; i++) {

            Level.B thisUtility = level.gameUtility[levelNumber - 1][i];



            switch (thisUtility){
                case SOFT:
                    block = new BlockSoft(context);
                    setUtilityBackgroundList(R.drawable.groundrost1, i);
                    softCounter = level.softCount[levelNumber - 1][0];
                    maxSoftCounter = level.softCount[levelNumber - 1][1];

                    break;
                case BOOSTR:
                    block = new BlockBoostR(context);
                    setUtilityBackgroundList(R.drawable.boostright, i);
                    break;
                case BOOSTL:
                    block = new BlockBoostL(context);
                    setUtilityBackgroundList(R.drawable.boostleft, i);
                    break;
                case RAMPUL:
                    block = new BlockRampUL(context);
                    setUtilityBackgroundList(R.drawable.rampupleft, i);
                    break;
                case RAMPUR:
                    block = new BlockRampUR(context);
                    setUtilityBackgroundList(R.drawable.rampupright, i);
                    break;
                case RAMPDL:
                    block = new BlockRampDL(context);
                    setUtilityBackgroundList(R.drawable.rampdownleft, i);
                    break;
                case RAMPDR:
                    block = new BlockRampDR(context);
                    setUtilityBackgroundList(R.drawable.rampdownright, i);
                    break;
            }


            Log.d("tag", "board list storlek" + startUtilityList.size());
            utilityList.add(block);       //lägger till blocket i arraylisten
            block.setBlock(screenWidth, blockNumberInRow, this);       //"ritar" blocket

        }


        drawBoard();
    }

    public void setUtilityBackgroundList(int pic, int i){
        boolean picExist = false;

        if(i == 0){
            startUtilityList.add(pic);       //lägger till blocket i arraylisten
        }else{
            for (int j = 0; j < startUtilityList.size(); j++) {
                if(startUtilityList.get(j) == pic){
                    picExist = true ;
                }

            }
            if(picExist == false){
                startUtilityList.add(pic);       //lägger till blocket i arraylisten
            }
        }

        Log.d("tag", "board startUtilitylist " + startUtilityList.size());
    }

    public void drawBoard(){
        removeAllViews();

        for (int i = 0; i < blockList.size(); i++) {
            /*
            *Bakgunds bilder
             */
            ImageView thisBlockImg = new ImageView(getContext());
            thisBlockImg.setImageResource(R.drawable.groundbackgound);

            /**
             * Räknar ut start position på blocken utifrån indexnummer
             */
            int startIPosAll = i + 1;
            double startYDouble = startIPosAll / 8.0;      //vet inte hur jag ska få blockNumberInRow till 8.0
            int startY = (int)Math.ceil(startYDouble);
            int startX = startIPosAll - ((startY - 1) * blockNumberInRow);
            blockSize = screenWidth / blockNumberInRow;

            thisBlockImg.setLayoutParams(new FrameLayout.LayoutParams(blockSize, blockSize));

            thisBlockImg.setX(((startX - 1) * blockSize));
            thisBlockImg.setY(((startY - 1) * blockSize));

            addView(thisBlockImg);


            /*
            *Blocken
            */
            Block thisBlock = blockList.get(i);
            thisBlock.setIndexNumber(i);     //ger blocket ett index nummer

            thisBlock.setX(((startX - 1) * blockSize));
            thisBlock.setY(((startY - 1) * blockSize));

            //thisBlock.setXPos(((startX - 1) * blockSize));
            //thisBlock.setYPos(((startY - 1) * blockSize));
            //Log.d("tag", "board blockX " + ((startX - 1) * blockSize));
            //Log.d("tag", "board blockY " + ((startY - 1) * blockSize));

            addView(thisBlock);

            Level.B blockType = thisBlock.getBlockType();
            if(blockType == Level.B.START){
                startPos = i;
                startPosX = thisBlock.getX();
                startPosY = thisBlock.getY();
            }
        }

        float density = getContext().getResources().getDisplayMetrics().density;
        boundariesHeightDP = 10 * density;
        //float dp = 10 / density;

        ImageView bottomBoundaries = new ImageView(getContext());
        bottomBoundaries.setImageResource(R.drawable.boundaries);
        bottomBoundaries.setY(blockSize * blockNumberInRow);
        bottomBoundaries.setAlpha((float)0.7);
        bottomBoundaries.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int)boundariesHeightDP));
        bottomBoundaries.setScaleType(ImageView.ScaleType.CENTER_CROP);

        addView(bottomBoundaries);


        utilCounter = 0;
        //softCounter = 0;
        //maxSoftCounter = 0;
        boostRCounter = 0;
        boostLCounter = 0;
        rampULCounter = 0;
        rampURCounter = 0;
        rampDLCounter = 0;
        rampDRCounter = 0;



        for (int i = 0; i < startUtilityList.size() ; i++) {
            int pic = startUtilityList.get(i);
            float[] XY = drawUtilityBackground(pic, i);

            switch (pic){
                case R.drawable.groundrost1:
                    softXY = XY;
                    setUtilityCounterHolder(softUtilCounter, softXY);
                    break;
                case R.drawable.boostright:
                    boostRXY = XY;
                    setUtilityCounterHolder(boostRUtilCounter, boostRXY);
                    break;
                case R.drawable.boostleft:
                    boostLXY = XY;
                    setUtilityCounterHolder(boostLUtilCounter, boostLXY);
                    break;
                case R.drawable.rampupleft:
                    rampULXY = XY;
                    setUtilityCounterHolder(rampULUtilCounter, rampULXY);
                    break;
                case R.drawable.rampupright:
                    rampURXY = XY;
                    setUtilityCounterHolder(rampURUtilCounter, rampURXY);
                    break;
                case R.drawable.rampdownleft:
                    rampDLXY = XY;
                    setUtilityCounterHolder(rampDLUtilCounter, rampDLXY);
                    break;
                case R.drawable.rampdownright:
                    rampDRXY = XY;
                    setUtilityCounterHolder(rampDRUtilCounter, rampDRXY);
                    break;
            }
        }


        for (int i = 0; i < utilityList.size(); i++) {
            Level.B thisUtilityType = utilityList.get(i).getBlockType();
            Block thisUtility = utilityList.get(i);



            switch (thisUtilityType){
                case SOFT:

                    if(softCounter > 0) {
                        thisUtility.setX(softXY[0]);
                        thisUtility.setY(softXY[1]);
                        thisUtility.setIndexNumber((blockNumberInRow * blockNumberInRow) + i);
                        addView(thisUtility);
                    }

                    //maxSoftCounter++;
                    //softCounter++;
                    updateUtilityCounter(softUtilCounter, softCounter, maxSoftCounter);
                    break;
                case BOOSTR:
                    thisUtility.setX(boostRXY[0]);
                    thisUtility.setY(boostRXY[1]);
                    thisUtility.setIndexNumber((blockNumberInRow * blockNumberInRow) + i);
                    addView(thisUtility);

                    boostRCounter++;
                    updateUtilityCounter(boostRUtilCounter, boostRCounter);
                    break;
                case BOOSTL:
                    thisUtility.setX(boostLXY[0]);
                    thisUtility.setY(boostLXY[1]);
                    thisUtility.setIndexNumber((blockNumberInRow * blockNumberInRow) + i);
                    addView(thisUtility);

                    boostLCounter++;
                    updateUtilityCounter(boostLUtilCounter, boostLCounter);
                    break;
                case RAMPUL:
                    thisUtility.setX(rampULXY[0]);
                    thisUtility.setY(rampULXY[1]);
                    thisUtility.setIndexNumber((blockNumberInRow * blockNumberInRow) + i);
                    addView(thisUtility);

                    rampULCounter++;
                    updateUtilityCounter(rampULUtilCounter, rampULCounter);
                    break;
                case RAMPUR:
                    thisUtility.setX(rampURXY[0]);
                    thisUtility.setY(rampURXY[1]);
                    thisUtility.setIndexNumber((blockNumberInRow * blockNumberInRow) + i);
                    addView(thisUtility);

                    rampURCounter++;
                    updateUtilityCounter(rampURUtilCounter, rampURCounter);
                    break;
                case RAMPDL:
                    thisUtility.setX(rampDLXY[0]);
                    thisUtility.setY(rampDLXY[1]);
                    thisUtility.setIndexNumber((blockNumberInRow * blockNumberInRow) + i);
                    addView(thisUtility);

                    rampDLCounter++;
                    updateUtilityCounter(rampDLUtilCounter, rampDLCounter);
                    break;
                case RAMPDR:
                    thisUtility.setX(rampDRXY[0]);
                    thisUtility.setY(rampDRXY[1]);
                    thisUtility.setIndexNumber((blockNumberInRow * blockNumberInRow) + i);
                    addView(thisUtility);

                    rampDRCounter++;
                    updateUtilityCounter(rampDRUtilCounter, rampDRCounter);
                    break;
            }
        }
    }

    public void setUtilityCounterHolder(TextView textView, float[] pos) {

        textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        textView.setText(String.valueOf(0));
        textView.setTextSize(21);
        textView.setTypeface(Typeface.SERIF, Typeface.BOLD);
        textView.setShadowLayer(10, 7, 6, Color.BLACK);
        textView.setX(pos[0]);
        textView.setY(pos[1]);

        addView(textView);
    }

    public void updateUtilityCounter(TextView textView, int counter) {
        textView.setText(String.valueOf(counter));
        textView.bringToFront();
    }

    public void updateUtilityCounter(TextView textView, int counter, int max) {
        textView.setText(String.valueOf(counter) + "/" + String.valueOf(max));
        textView.setTextSize(16);
        textView.bringToFront();
    }

    public float[] drawUtilityBackground(int pic, int utilCounter){
        float x;
        float y;

        ImageView utilBackground = new ImageView(getContext());

        if(utilCounter <= utilityNumberInRow - 1){
            x = (((blockSize * 2) / 7) + (blockSize * utilCounter) + (((blockSize * 2) / 7) * utilCounter));
            y = (((blockSize * 8) + boundariesHeightDP + boundariesHeightDP));
        }else{
            x = (((blockSize * 2) / 7) + (blockSize * (utilCounter - 6)) + (((blockSize * 2) / 7) * (utilCounter - 6)));
            y = (((blockSize * 8) + boundariesHeightDP + boundariesHeightDP) + blockSize + boundariesHeightDP );
        }

        utilBackground.setX(x);
        utilBackground.setY(y);
        utilBackground.setImageResource(pic);
        utilBackground.setAlpha((float)0.5);
        utilBackground.setLayoutParams(new FrameLayout.LayoutParams(blockSize, blockSize));

        addView(utilBackground);


        float[] XY = new float[2];
        XY[0] = x;
        XY[1] = y;

        return XY;

    }



    public void changeDrawBoard(int indexNumber){

       Level.B blockType = blockList.get(indexNumber).getBlockType();

        if(blockType == Level.B.SOFT || blockType == Level.B.EMPTY) {
            Log.d("tag", "klick " + blockType);
            switch (blockType) {
                case EMPTY:
                    block = new BlockSoft(getContext());               //skapar blocket
                    break;
                case SOFT:
                    block = new BlockEmpty(getContext());
                    break;
                /*case "solid":
                    block = new BlockSolid(getContext());
                    break;
                case "goal":
                    block = new BlockGoal(getContext());
                    break;
                case "start":
                    block = new BlockStart(getContext());
                    break;
                default:
                    block = new BlockEmpty(getContext());
                    break;*/
            }

            block.setBlock(screenWidth, blockNumberInRow, this);       //"ritar" blocket
            blockList.set(indexNumber, block);
        }
    }

    public void swapBlock(float releaseX, float releaseY, int indexNumber){

        for (int i = 0; i < blockList.size() ; i++) {

            int[] location = new int[2];
            blockList.get(i).getLocationInWindow(location);


            if (releaseX >= location[0] && releaseX <= location[0] + blockSize && releaseY >= location[1] && releaseY <= location[1] + blockSize) {
                if(i != indexNumber && blockList.get(i).getBlockType() == Level.B.EMPTY) {
                    Log.d("tag", "block denna pos " + i);
                    Collections.swap(blockList, indexNumber, i);

                    drawBoard();
                    break;
                }

            }else if (releaseY > (blockSize * 8) + boundariesHeightDP){
                utilityList.add(blockList.get(indexNumber));
                Block newEmptyBlock = new BlockEmpty(getContext());
                blockList.set(indexNumber, newEmptyBlock);
                drawBoard();
            }else{
                drawBoard();
            }
        }
    }

    public void swapBlockArray(float releaseX, float releaseY, Block utilityBlock) {

        for (int i = 0; i < blockList.size(); i++) {
            int[] location = new int[2];
            blockList.get(i).getLocationInWindow(location);

            if (releaseX >= location[0] && releaseX <= location[0] + blockSize && releaseY >= location[1] && releaseY <= location[1] + blockSize) {
                if (blockList.get(i).getBlockType() == Level.B.EMPTY) {
                    blockList.set(i, utilityBlock);
                    
                    utilityList.remove(utilityBlock);
                    drawBoard();
                    break;
                }
            }else{
                drawBoard();
            }

        }
    }
}
