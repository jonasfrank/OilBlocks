package com.example.jonasfrank.oilblocks;

import android.content.Context;

/**
 * Created by User on 2017-01-23.
 */

public class BlockRampDL extends Block {

    Level.B blockType = Level.B.RAMPDL;

    //Variablar för sidor, if true så är sidan öppen för bollen att rulla.
    final boolean sideU = false;
    final boolean sideR = false;
    final boolean sideD = true;
    final boolean sideL = true;

    public float ballChangeDirection = 20;

    public BlockRampDL(Context context){
        super(context);

        setImageResource(R.drawable.rampdownleft);
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