package com.example.jonasfrank.oilblocks;

import android.content.Context;

/**
 * Created by User on 2017-01-23.
 */

public class BlockRampDR extends Block {

    Level.B blockType = Level.B.RAMPDR;

    //Variablar för sidor, if true så är sidan öppen för bollen att rulla.
    final boolean sideU = false;
    final boolean sideR = true;
    final boolean sideD = true;
    final boolean sideL = false;

    public float ballChangeDirection = 20;

    public BlockRampDR(Context context){
        super(context);

        setImageResource(R.drawable.rampdownright);
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
    public float getBallChangeDirection(){
        return ballChangeDirection;
    }

}