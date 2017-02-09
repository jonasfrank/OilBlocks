package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class Board extends RelativeLayout{

    public int screenWidth;
    public int blockNumberInRow = 8;
    public int blockSize;
    public int levelNumber;
    public Level level;
    public Block block;
    public Ball ball;
    public int startPos;
    public float startPosX;
    public float startPosY;
    ArrayList<Block> blockList = new ArrayList<Block>();
    ArrayList<Integer> backgrundList = new ArrayList<Integer>();

    public Board(Context context){
        super(context);
    }

    public void setBoard(Context context, int startScreenWidth, int levelNumber, Ball startBall){
        ball = startBall;
        screenWidth = startScreenWidth;

        setLayoutParams(new FrameLayout.LayoutParams(screenWidth,screenWidth));
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

            addView(thisBlock);

            Level.B blockType = thisBlock.getBlockType();
            if(blockType == Level.B.START){
                startPos = i;
                startPosX = thisBlock.getX();
                startPosY = thisBlock.getY();
            }
        }
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
}
