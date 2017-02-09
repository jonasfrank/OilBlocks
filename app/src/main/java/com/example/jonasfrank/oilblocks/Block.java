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
    public float blockX;
    public float blockY;
    public float dx;
    public float dy;
    public Level level;
    public ArrayList blockList;
    public int indexNumber;
    public Board board;

    public float ballChangeDirection;


    public Block(Context context){
        super(context);
    }

    public void setBlock(int startScreenWidth, int blockNumberInRow, final Board startBoard){

        board = startBoard;

        blockSize = startScreenWidth / blockNumberInRow;
        setLayoutParams(new FrameLayout.LayoutParams(blockSize, blockSize));
        //AbsoluteLayout.LayoutParams param = new AbsoluteLayout.LayoutParams(blockSize, blockSize, x, y);
        //Log.d("tag", "test" + x + " x " + y);
        //setLayoutParams(param);

        /*setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                board.changeDrawBoard(indexNumber);
                board.drawBoard();
                //Log.d("tag", "klick" + getX());
            }
        });*/

    }


    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {

            case (MotionEvent.ACTION_DOWN):
                //setImageResource(onDownImgId);
                blockX = event.getRawX();
                blockY = event.getRawY();
                dx = blockX - getX();
                dy = blockY - getY();



                return true;
            case (MotionEvent.ACTION_MOVE):
                setX(event.getRawX() - dx);
                setY(event.getRawY() - dy);
                return true;
            case (MotionEvent.ACTION_UP):

                float pressX = event.getRawX();
                float pressY = event.getRawY();

                outerLoop:
                for (int i = 0; i < board.blockList.size() ; i++) {

                    int[] location = new int[2];
                    board.blockList.get(i).getLocationInWindow(location);

                    if(pressX > location[0] && pressX < location[0] + blockSize && pressY  > location[1] && pressY < location[1] + blockSize ){
                        Log.d("tag", "block denna pos " + i);

                        //board.blockList.remove(indexNumber);
                        //board.blockList.remove(i);
                        //board.blockList.set(indexNumber, board.blockList.get(i));
                        //board.blockList.set(i, this);

                        Collections.swap(board.blockList, i, indexNumber);


                        board.drawBoard();
                        break outerLoop;


                    }


                }

                return true;
            default:
                return super.onTouchEvent(event);
        }
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

    abstract void blockKlicked();
    abstract Level.B getBlockType();
    //abstract float getBallChangeDirection();

}
