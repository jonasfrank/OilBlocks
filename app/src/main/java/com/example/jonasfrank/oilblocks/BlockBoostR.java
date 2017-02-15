package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;

/**
 * Created by User on 2017-01-23.
 */



public class BlockBoostR extends Block {

    public Level.B blockType = Level.B.BOOSTR;

    //Variablar för sidor, if true så är sidan öppen för bollen att rulla.
    public final boolean sideU = true;
    public final boolean sideR = true;
    public final boolean sideD = true;
    public final boolean sideL = true;

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
