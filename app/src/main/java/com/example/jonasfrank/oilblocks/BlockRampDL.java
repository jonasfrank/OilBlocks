package com.example.jonasfrank.oilblocks;

import android.content.Context;

/**
 * Created by User on 2017-01-23.
 */

public class BlockRampDL extends Block {

    public Level.B blockType = Level.B.RAMPDL;
    public float ballChangeDirection = 20;

    public BlockRampDL(Context context){
        super(context);

        setImageResource(R.drawable.rampdownleft);
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