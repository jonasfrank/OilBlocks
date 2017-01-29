package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;


public class BlockEmpty extends Block {

    public Level.B blockType = Level.B.EMPTY;

    public BlockEmpty(Context context){
        super(context);

        setImageResource(R.drawable.groundemptyedge);
    }

    public void blockKlicked(){
        Log.d("tag", "klick på empty");
    }

    public Level.B getBlockType(){
        return blockType;
    }

}
