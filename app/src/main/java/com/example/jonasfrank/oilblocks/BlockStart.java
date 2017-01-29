package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;


public class BlockStart extends Block {

    public Level.B blockType = Level.B.START;

    public BlockStart(Context context){
        super(context);

        setImageResource(R.drawable.start);

        /*int[] posXY = new int[2];
        getLocationOnScreen(posXY);
        int x = posXY[0];
        int y = posXY[1];

        Log.d("tag", "start" + x + " x " + getY());

        setImageResource(R.drawable.start);
        int testx = getDrawable().getIntrinsicHeight();
        int testy = getDrawable().getIntrinsicWidth();

        Log.d("tag", "start" + testx + " x " + testy);

        int[] location = new int[2];
        this.getLocationOnScreen(location);
        int tx = location[0];
        int ty = location[1];

        Log.d("tag", "start" + tx + " x " + ty);*/



    }

    public void blockKlicked(){
        Log.d("tag", "klick på start");
    }

    public Level.B getBlockType(){
        return blockType;
    }


}