package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;


public class BlockEmpty extends Block {

    public Level.B blockType = Level.B.EMPTY;

    public BlockEmpty(Context context){
        super(context);

        setImageResource(R.drawable.groundempty);
    }

    public void blockKlicked(){
        Log.d("tag", "klick på empty");
    }

    public Level.B getBlockType(){
        return blockType;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {

            case (MotionEvent.ACTION_DOWN):
                return true;
            case (MotionEvent.ACTION_MOVE):
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d("tag", "Blocksoft tryck");
                board.changeDrawBoard(indexNumber);
                board.drawBoard();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

}
