package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;


public class BlockSolid extends Block {

    Level.B blockType = Level.B.SOLID;

    //Variablar för sidor, if true så är sidan öppen för bollen att rulla.
    final boolean sideU = false;
    final boolean sideR = false;
    final boolean sideD = false;
    final boolean sideL = false;

    public BlockSolid(Context context){
        super(context);

        setImageResource(R.drawable.groundsolid);
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
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

}