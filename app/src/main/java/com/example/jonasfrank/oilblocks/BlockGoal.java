package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;


public class BlockGoal extends Block {

    public Level.B blockType = Level.B.GOAL;

    //Variablar för sidor, if true så är sidan öppen för bollen att rulla.
    public final boolean sideU = true;
    public final boolean sideR = false;
    public final boolean sideD = false;
    public final boolean sideL = false;

    public BlockGoal(Context context){
        super(context);

        setImageResource(R.drawable.blockgoal);
    }

    public void blockKlicked(){
        Log.d("tag", "klick på goal");
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