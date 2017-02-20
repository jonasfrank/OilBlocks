package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;

/**
 * Created by User on 2017-01-23.
 */



public class BlockBoostL extends Block {

    Level.B blockType = Level.B.BOOSTL;

    //Variablar för sidor, if true så är sidan öppen för bollen att rulla.
    final boolean sideU = true;
    final boolean sideR = true;
    final boolean sideD = true;
    final boolean sideL = true;

    public float ballChangeDirection = -20;

    public BlockBoostL(Context context){
        super(context);

        setImageResource(R.drawable.boostleft);
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
