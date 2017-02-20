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

    int blockSize;
    int indexNumber;

    float pressX;
    float pressY;
    float dx;
    float dy;
    public float x;
    public float y;

    Level level;
    Board board;

    boolean sideU;
    boolean sideR;
    boolean sideD;
    boolean sideL;

    float ballChangeDirection;


    public Block(Context context) {
        super(context);
    }

    public void setBlock(int startScreenWidth, int blockNumberInRow, final Board startBoard) {

        board = startBoard;

        blockSize = startScreenWidth / blockNumberInRow;
        setLayoutParams(new FrameLayout.LayoutParams(blockSize, blockSize));
    }

    public boolean onTouchEvent(MotionEvent event) {

        if (board != null && board.ball != null && board.ball.game != null) {
            if (board.ball.game.getgameInProgress() == false) {
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
                        Log.d("tag", "index:" + indexNumber);
                        if (indexNumber < 64) {
                            board.swapBlock(releaseX, releaseY, indexNumber);
                        } else {
                            board.swapBlockArray(releaseX, releaseY, this);
                        }
                        return true;
                    default:
                        return super.onTouchEvent(event);
                }
            }
        }
        return true;
    }

    public Level getLevel(){
        return level;
    }

    public void setIndexNumber(int startIndexNumber){
        indexNumber = startIndexNumber;
    }

    public float getBallChangeDirection(){
        return ballChangeDirection;
    }

    abstract Level.B getBlockType();
    abstract boolean getSideU();
    abstract boolean getSideR();
    abstract boolean getSideD();
    abstract boolean getSideL();
    //abstract float getBallChangeDirection();

}
