package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;


public class BlockGoal extends Block {

    public Level.B blockType = Level.B.GOAL;

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

}