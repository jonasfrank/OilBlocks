package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;


public class BlockStart extends Block {

    Level.B blockType = Level.B.START;

    //Variablar för sidor, if true så är sidan öppen för bollen att rulla.
    final boolean sideU = true;
    final boolean sideR = true;
    final boolean sideD = true;
    final boolean sideL = true;

    public BlockStart(Context context){
        super(context);

        setImageResource(R.drawable.start);
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
        return false;
    }



}