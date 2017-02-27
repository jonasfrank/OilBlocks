package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;


public class BlockSoftSolid extends Block {

    Level.B blockType = Level.B.SOFTSOLID;

    //Variablar för sidor, if true så är sidan öppen för bollen att rulla.
    final boolean sideU = false;
    final boolean sideR = false;
    final boolean sideD = false;
    final boolean sideL = false;

    public BlockSoftSolid(Context context){
        super(context);

        setImageResource(R.drawable.groundremove);
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

}
