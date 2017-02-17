package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;


public class BlockEmptyDisable extends Block {

    public Level.B blockType = Level.B.EMPTY;

    //Variablar för sidor, if true så är sidan öppen för bollen att rulla.
    public final boolean sideU = true;
    public final boolean sideR = true;
    public final boolean sideD = true;
    public final boolean sideL = true;

    public BlockEmptyDisable(Context context){
        super(context);

        setImageResource(R.drawable.groundremove);
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

        return true;
    }

}
