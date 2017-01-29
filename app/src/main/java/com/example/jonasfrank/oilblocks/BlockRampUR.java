package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;

/**
 * Created by User on 2017-01-23.
 */

public class BlockRampUR extends Block {

    public Level.B blockType = Level.B.RAMPUR;
    public float ballChangeDirection = 20;

    public BlockRampUR(Context context){
        super(context);

        setImageResource(R.drawable.rampupright);
    }

    public void blockKlicked(){

    }

    public Level.B getBlockType(){
        return blockType;
    }

    @Override
    public float getBallChangeDirection(){
        return ballChangeDirection;
    }

}
