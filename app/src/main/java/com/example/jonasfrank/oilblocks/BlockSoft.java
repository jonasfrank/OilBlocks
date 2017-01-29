package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;


public class BlockSoft extends Block {

    public Level.B blockType = Level.B.SOFT;
    public int [] softBlockList = {R.drawable.groundrost1, R.drawable.groundrost2, R.drawable.groundrost3, R.drawable.groundrost4, R.drawable.groundrost5 };

    public BlockSoft(Context context){
        super(context);

        int randomNum = 0 + (int)(Math.random() * softBlockList.length-1);
        setImageResource(softBlockList[randomNum]);
    }

    public void blockKlicked(){

    }

    public Level.B getBlockType(){
        return blockType;
    }

}
