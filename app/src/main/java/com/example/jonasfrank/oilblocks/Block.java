package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Objects;

import static android.R.attr.x;
import static android.R.attr.y;


abstract class Block extends ImageView {

    public int screenWidth;
    public int blockNumberInRow;
    public int blockSize;
    public int levelNumber;
    public float blockX = x;
    public float blockY = y;
    public Level level;
    public ArrayList blockList;
    public int indexNumber;

    public float ballChangeDirection;


    public Block(Context context){
        super(context);
    }

    public void setBlock(int startScreenWidth, int blockNumberInRow, final Board board){

        blockSize = startScreenWidth / blockNumberInRow;
        setLayoutParams(new FrameLayout.LayoutParams(blockSize, blockSize));
        //AbsoluteLayout.LayoutParams param = new AbsoluteLayout.LayoutParams(blockSize, blockSize, x, y);
        //Log.d("tag", "test" + x + " x " + y);
        //setLayoutParams(param);

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                board.changeDrawBoard(indexNumber);
                board.drawBoard();
                //Log.d("tag", "klick" + getX());
            }
        });
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
