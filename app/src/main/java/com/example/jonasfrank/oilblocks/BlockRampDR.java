package com.example.jonasfrank.oilblocks;

import android.content.Context;

/**
 * Created by User on 2017-01-23.
 */

public class BlockRampDR extends Block {

    public Level.B blockType = Level.B.RAMPDR;
    public float ballChangeDirection = 20;

    public BlockRampDR(Context context){
        super(context);

        setImageResource(R.drawable.rampdownright);
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