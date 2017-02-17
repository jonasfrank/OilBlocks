package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import static android.R.attr.x;
import static android.R.attr.y;


abstract class Block extends ImageView {

    public int screenWidth;
    public int blockNumberInRow;
    public int blockSize;
    public int levelNumber;
    public float pressX;
    public float pressY;
    public float dx;
    public float dy;
    public Level level;
    public ArrayList blockList;
    public int indexNumber;
    public Board board;
    public float x;
    public float y;

    public boolean sideU;
    public boolean sideR;
    public boolean sideD;
    public boolean sideL;

    public float ballChangeDirection;


    public Block(Context context) {
        super(context);
    }

    public void setBlock(int startScreenWidth, int blockNumberInRow, final Board startBoard) {

        board = startBoard;

        blockSize = startScreenWidth / blockNumberInRow;
        setLayoutParams(new FrameLayout.LayoutParams(blockSize, blockSize));

        //AbsoluteLayout.LayoutParams param = new AbsoluteLayout.LayoutParams(blockSize, blockSize, x, y);
        //Log.d("tag", "test" + x + " x " + y);
        //setLayoutParams(param);

        /*setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < blockList.size(); i++) {

                }

                board.changeDrawBoard(indexNumber);
                board.drawBoard();
                //Log.d("tag", "klick" + getX());
            }
        });*/

    }


    public boolean onTouchEvent(MotionEvent event) {

        if (board.ball.game.getRunning() == false) {
            int action = MotionEventCompat.getActionMasked(event);

            switch (action) {


                case (MotionEvent.ACTION_DOWN):
                    pressX = event.getRawX();
                    pressY = event.getRawY();
                    dx = pressX - getX();
                    dy = pressY - getY();

                    return true;
                case (MotionEvent.ACTION_MOVE):
                    bringToFront();
                    setX(event.getRawX() - dx);
                    setY(event.getRawY() - dy);

                    return true;
                case (MotionEvent.ACTION_UP):
                    float releaseX = event.getRawX();
                    float releaseY = event.getRawY();
                    Log.d("index: ", "" + indexNumber);
                    if (indexNumber < 64) {
                        board.swapBlock(releaseX, releaseY, indexNumber);
                    } else {
                        board.swapBlockArray(releaseX, releaseY, this);
                        Log.d("utility", "dras");
                    }
                    return true;
                default:
                    return super.onTouchEvent(event);
            }

        }
        return true;
    }



    public int getLevelNumber(){
        return levelNumber;
    }

    public Level getLevel(){
        return level;
    }

    public ArrayList getArrayList(){
        return blockList;
    }

    public void setIndexNumber(int startIndexNumber){
        indexNumber = startIndexNumber;
    }

    public float getBallChangeDirection(){
        return ballChangeDirection;
    }

    /*public boolean getSideU(){
        return sideU;
    }
    public boolean getSideR(){
        return sideR;
    }
    public boolean getSideD() { return sideD; }
    public boolean getSideL(){
        return sideL;
    }*/

    abstract void blockKlicked();
    abstract Level.B getBlockType();
    abstract boolean getSideU();
    abstract boolean getSideR();
    abstract boolean getSideD();
    abstract boolean getSideL();
    //abstract float getBallChangeDirection();

}
