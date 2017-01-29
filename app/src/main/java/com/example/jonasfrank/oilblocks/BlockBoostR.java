package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;

/**
 * Created by User on 2017-01-23.
 */



public class BlockBoostR extends Block {

    public Level.B blockType = Level.B.BOOSTR;
    public float ballChangeDirection = 20;

    public BlockBoostR(Context context){
        super(context);

        setImageResource(R.drawable.boostright);
    }

    public void blockKlicked(){
        Log.d("tag", "klick på booster");
    }

    public Level.B getBlockType(){
        return blockType;
    }

    @Override
    public float getBallChangeDirection(){
        return ballChangeDirection;
    }

}
