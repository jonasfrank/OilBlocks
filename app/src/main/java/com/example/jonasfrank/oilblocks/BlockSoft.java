package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;


public class BlockSoft extends Block {

    Level.B blockType = Level.B.SOFT;
    int [] softBlockList = {R.drawable.groundrost1, R.drawable.groundrost2, R.drawable.groundrost3, R.drawable.groundrost4, R.drawable.groundrost5 };

    //Variablar för sidor, if true så är sidan öppen för bollen att rulla.
    final boolean sideU = false;
    final boolean sideR = false;
    final boolean sideD = false;
    final boolean sideL = false;

    public BlockSoft(Context context){
        super(context);

        int randomNum = 0 + (int)(Math.random() * softBlockList.length-1);
        setImageResource(softBlockList[randomNum]);
    }

    public Level.B getBlockType(){
        return blockType;
    }

    public boolean getSideU() { return sideU; }
    public boolean getSideR(){
        return sideR;
    }
    public boolean getSideD() { return sideD; }
    public boolean getSideL() { return sideL; }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (board != null && board.ball != null && board.ball.game != null) {
            if (indexNumber <= 63) {

                if (board.ball.game.getgameInProgress() == false) {
                    int action = MotionEventCompat.getActionMasked(event);

                    switch (action) {

                        case (MotionEvent.ACTION_DOWN):
                            return true;
                        case (MotionEvent.ACTION_MOVE):
                            return true;
                        case (MotionEvent.ACTION_UP):
                            Log.d("tag", "Blocksoft tryck");
                            if (board.softCounter >= 0 && board.softCounter < board.maxSoftCounter) {
                                board.changeDrawBoard(indexNumber);
                                board.softCounter++;
                            }
                            board.drawBoard();
                            return true;

                        default:
                            return super.onTouchEvent(event);
                    }
                }
            }
        }
        return true;
    }
}
