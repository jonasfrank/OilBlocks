package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;


public class BlockSolid extends Block {

    public Level.B blockType = Level.B.SOLID;

    public BlockSolid(Context context){
        super(context);

        setImageResource(R.drawable.groundsolid);
    }

    public void blockKlicked(){
        Log.d("tag", "klick på solid");
    }

    public Level.B getBlockType(){
        return blockType;
    }
}