package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
    ArrayList<Block> utilityList = new ArrayList<Block>();

    public ArrayList location;

    public Board(Context context){
        super(context);
    }

    public void setBoard(Context context, int startScreenWidth, int levelNumber, Ball startBall){
        ball = startBall;
        screenWidth = startScreenWidth;

        setLayoutParams(new FrameLayout.LayoutParams(screenWidth, ViewGroup.LayoutParams.MATCH_PARENT));

        //setColumnCount(blockNumberInRow);
        //setRowCount(blockNumberInRow);

        Level level = new Level();

        for (int i = 0; i < blockNumberInRow; i++) {

            for (int j = 0; j < blockNumberInRow; j++) {

                Level.B blockType = level.gameLavel[levelNumber - 1][i][j];
                switch (blockType){
                    case EMPTY:
                        block = new BlockEmpty(context);
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
            }

            utilityList.add(block);       //lägger till blocket i arraylisten
            block.setBlock(screenWidth, blockNumberInRow, this);       //"ritar" blocket

        }


        drawBoard();
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

        int utilCounter = 0;
        int softCounter = 0;
        int boostRCounter = 0;
        int boostLCounter = 0;
        int rampULCounter = 0;
        int rampURCounter = 0;
        int rampDLCounter = 0;
        int rampDRCounter = 0;

        for (int i = 0; i < utilityList.size(); i++) {
            Level.B thisUtility = utilityList.get(i).getBlockType();

            switch (thisUtility){
                case SOFT:
                    float[] XY;
                    if(softCounter == 0){

                        XY = drawUtilityBackground(R.drawable.groundrost1, utilCounter);
                        utilCounter++;
                    }



                    softCounter++;
                    break;
                case BOOSTR:
                    if(boostRCounter == 0){

                        drawUtilityBackground(R.drawable.boostright, utilCounter);
                        utilCounter++;
                    }

                    boostRCounter++;
                    break;
                case BOOSTL:
                    if(boostLCounter == 0){

                        drawUtilityBackground(R.drawable.boostleft, utilCounter);
                        utilCounter++;
                    }

                    boostLCounter++;
                    break;
                case RAMPUL:
                    if(rampULCounter == 0){

                        drawUtilityBackground(R.drawable.rampupleft, utilCounter);
                        utilCounter++;
                    }

                    rampULCounter++;
                    break;
                case RAMPUR:
                    if(rampURCounter == 0){

                        drawUtilityBackground(R.drawable.rampupright, utilCounter);
                        utilCounter++;
                    }

                    rampURCounter++;
                    break;
                case RAMPDL:
                    if(rampDLCounter == 0){

                        drawUtilityBackground(R.drawable.rampdownleft, utilCounter);
                        utilCounter++;
                    }

                    rampDLCounter++;
                    break;
                case RAMPDR:
                    if(rampDRCounter == 0){

                        drawUtilityBackground(R.drawable.rampdownright, utilCounter);
                        utilCounter++;
                    }

                    rampDRCounter++;
                    break;
            }

        }


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
        utilBackground.setAlpha((float)0.7);
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

        outerLoop:
        for (int i = 0; i < blockList.size() ; i++) {

            int[] location = new int[2];
            blockList.get(i).getLocationInWindow(location);

            if(i != indexNumber && blockList.get(i).getBlockType() == Level.B.EMPTY) {
                if (releaseX >= location[0] && releaseX <= location[0] + blockSize && releaseY >= location[1] && releaseY <= location[1] + blockSize) {
                    Log.d("tag", "block denna pos " + i);
                    Collections.swap(blockList, indexNumber, i);

                    drawBoard();
                    break outerLoop;
                }
            }else if(i == indexNumber){
                drawBoard();
            }
        }
    }
}
