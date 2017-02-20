package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;


public class BlockEmpty extends Block {

    public Level.B blockType = Level.B.EMPTY;

    //Variablar för sidor, if true så är sidan öppen för bollen att rulla.
    public final boolean sideU = true;
    public final boolean sideR = true;
    public final boolean sideD = true;
    public final boolean sideL = true;

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

    public boolean getSideU() { return sideU; }
    public boolean getSideR(){
        return sideR;
    }
    public boolean getSideD() { return sideD; }
    public boolean getSideL() { return sideL; }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        /*if (board == null  ) {
            Log.d("tag", "BlockEmpty är null ---------------------");
        }*/

        if (board != null && board.ball != null && board.ball.game != null ) {
            if (board.ball.game.getgameInProgress() == false) {
                int action = MotionEventCompat.getActionMasked(event);

                switch (action) {

                    case (MotionEvent.ACTION_DOWN):
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        return true;
                    case (MotionEvent.ACTION_UP):
                        Log.d("tag", "Blocksoft tryck");
                        if (board.softCounter > 0 && board.softCounter <= board.maxSoftCounter) {
                            board.changeDrawBoard(indexNumber);
                            board.softCounter--;
                        }
                        board.drawBoard();
                        return true;
                    default:
                        return super.onTouchEvent(event);
                }
            }
        }
        return true;
    }

}
