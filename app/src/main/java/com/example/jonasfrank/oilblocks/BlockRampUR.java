package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;

/**
 * Created by User on 2017-01-23.
 */

public class BlockRampUR extends Block {

    public Level.B blockType = Level.B.RAMPUR;

    //Variablar för sidor, if true så är sidan öppen för bollen att rulla.
    public final boolean sideU = true;
    public final boolean sideR = true;
    public final boolean sideD = false;
    public final boolean sideL = false;

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
