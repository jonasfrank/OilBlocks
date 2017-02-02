package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Random;
import java.util.logging.Handler;


public class Ball extends ImageView {

    public int screenWidth;
    public float blockSize;
    public Board board;
    public float startBallX;
    public float startBallY;
    public float moveX = 0;
    public float moveY = 0;

    boolean collisionRamp;
    boolean collisionBooster;
    boolean collisionBlock;
    boolean collisionBoundaries;


    public Ball(Context context){
        super(context);
        setImageResource(R.drawable.ball);
    }

    public void setBall(int startScreenWidth, int blockNumberInRow, Board startBoard){

        screenWidth = startScreenWidth;
        blockSize = (float) startScreenWidth / blockNumberInRow;
        board = startBoard;
        moveY = 20;

        setLayoutParams(new FrameLayout.LayoutParams((int)blockSize, (int) blockSize));

        startBallX = board.startPosX;
        startBallY = board.startPosY;
        setX(startBallX);
        setY(startBallY);
    }

    public void restartBall(){
        setX(startBallX);
        setY(startBallY);
        moveX = 0;
        moveY = 20;
    }

    public void ballMove() {

        collisionRamp = false;
        collisionBooster = false;
        collisionBlock = false;
        collisionBoundaries = false;

        float ballX = getX();
        float ballY = getY();

        /**
         * Loop som loppar igenom alla blocken i blockList.
         */
        outerLoop:
        for(int i = 0; i < board.blockList.size(); i++) {
            Block thisBlock = board.blockList.get(i);

            Level.B thisBlockType = thisBlock.getBlockType();
            float thisBlockX = thisBlock.getX();
            float thisBlockY = thisBlock.getY();

            collisionRamp = rampCheck(ballX, ballY, thisBlockType, thisBlockX, thisBlockY, i);
            collisionBooster = boosterCheck(ballX, ballY, thisBlockType, thisBlockX, thisBlockY, i);
            collisionBlock = blockCheck(ballX, ballY, thisBlockType, thisBlockX, thisBlockY, i);

            if (collisionBlock == false && collisionBoundaries == false) {
                //Log.d("tag", " kant koll");
                collisionBoundaries = boundaryCheck(ballX, ballY);
            }
            // OM kollision hittas, avbryt loopen för blocks
            if (collisionBlock == true || collisionBooster == true || collisionBoundaries == true || collisionRamp == true) {
                Log.d("Broken", "Loop");
                break outerLoop;
            }

        }
        Log.d("tag", " moveX " + moveX );
        Log.d("tag", " moveY " + moveY );

        /**
         * Bollförflyttning
         */
        if(moveX != 0){
            setX(ballX + (blockSize / moveX ));
        }else{
            setX(ballX + moveX);
        }
        if(moveY != 0) {
            setY(ballY + (blockSize / moveY));
        }else{
            setY(ballY + moveY);
        }
    }



    /**
     *Todo
     * Ramp bakifrån
     * Ta bort hörnen?
     * olika hastigheter på olika block
     * minskande hastighet på x axeln olika på empty och rullandes på block
     */


    public boolean rampCheck(float x, float y, Level.B thisBlockType, float thisBlockX, float thisBlockY, int i){
        Block thisBlock = board.blockList.get(i);

        if(thisBlockType == Level.B.RAMPUL || thisBlockType == Level.B.RAMPUR || thisBlockType == Level.B.RAMPDL || thisBlockType == Level.B.RAMPDR) {
            if (x >= thisBlockX && x  <= thisBlockX && y >= thisBlockY && y <= thisBlockY) {
                Log.d("tag", "ball ramp");
                switch (thisBlockType){
                    case RAMPUL:
                        if(moveX < moveY){
                            moveX = moveX + (thisBlock.getBallChangeDirection()) * -1;
                            moveY = 0;
                        }else{
                            moveX = 0;
                            moveY = moveY + (thisBlock.getBallChangeDirection()) * -1;
                        }
                        break;
                    case RAMPUR:
                        if( moveX < moveY){
                            moveX = moveX + (thisBlock.getBallChangeDirection());
                            moveY = 0;
                        }else{
                            moveX = 0;
                            moveY = moveY + (thisBlock.getBallChangeDirection()) * -1;
                        }
                        break;
                    case RAMPDL:
                        if(moveX > moveY){
                            moveX = 0;
                            moveY = moveY + (thisBlock.getBallChangeDirection());
                        }else{
                            moveX = moveX + (thisBlock.getBallChangeDirection()) * -1;
                            moveY = 0;
                        }
                        break;
                    case RAMPDR:
                        if(moveX > moveY){
                            moveX = moveX + (thisBlock.getBallChangeDirection());
                            moveY = 0;
                        }else{
                            moveX = 0;
                            moveY = moveY + (thisBlock.getBallChangeDirection());
                        }
                        break;
                }
                collisionRamp = true;
            }
        }
        return collisionRamp;
    }

    public boolean boosterCheck(float x, float y, Level.B thisBlockType, float thisBlockX, float thisBlockY, int i){
        Block thisBlock = board.blockList.get(i);

        if(thisBlockType == Level.B.BOOSTR) {
            if (x >= thisBlockX && x  <= thisBlockX && y >= thisBlockY && y <= thisBlockY) {

                Log.d("tag", "ball boost");
                moveX =  thisBlock.getBallChangeDirection();
                collisionBooster = true;
            }else {
                collisionBooster = false;
            }
        }
        return collisionBooster;
    }

    public boolean blockCheck(float x, float y, Level.B thisBlockType, float thisBlockX, float thisBlockY, int i){

        if(thisBlockType == Level.B.SOFT || thisBlockType == Level.B.SOLID) {

            // A,B,C,D representerar ett hörn. A = uppe vänster , B = uppe höger, C = nere vänster, D = nere höger
            float bollAX = x;
            float bollAY = y;
            float bollBX = x + blockSize;
            float bollBY = y;
            float bollCX = x;
            float bollCY = y + blockSize;
            float bollDX = x + blockSize;
            float bollDY = y + blockSize;

            float blockAX = thisBlockX;
            float blockAY = thisBlockY;
            float blockBX = thisBlockX + blockSize;
            float blockBY = thisBlockY;
            float blockCX = thisBlockX;
            float blockCY = thisBlockY + blockSize;
            float blockDX = thisBlockX + blockSize;
            float blockDY = thisBlockY + blockSize;

            /**
             * Träff på block på hörnen. När bollen ligger precis i rutan ett steg diagonalt från ett block.
             */
            /*if(bollDX == blockAX && bollDY == blockAY){
                //Träff på block diagonalt uppe vänster
                if(moveX > 0 && moveY > 0){
                    Log.d("tag", "ball block uppe vänster");
                    float tempX = moveX * -1;
                    float tempY = moveY * -1;
                    moveX = tempY;
                    moveY = tempX;
                    collisionBlock = true;
                }
            }else if(bollCX == blockBX && bollCY == blockBY){
                //Träff på block diagonalt uppe höger
                if(moveX < 0 && moveY > 0){
                    Log.d("tag", "ball block uppe höger");
                    float tempX = moveX * -1;
                    float tempY = moveY * -1;
                    moveX = tempY * -1;
                    moveY = tempX * -1;
                    collisionBlock = true;
                }
            }else if(bollBX == blockCX && bollBY == blockCY){
                //Träff på block diagonalt ner vänster
                if(moveX > 0 && moveY < 0){
                    Log.d("tag", "ball block ner vänster");
                    float tempX = moveX * -1;
                    float tempY = moveY * -1;
                    moveX = tempY * -1;
                    moveY = tempX * -1;
                    collisionBlock = true;
                }
            }else if(bollAX == blockDX && bollAY == blockDY){
                //Träff på block diagonalt ner höger
                if(moveX < 0 && moveY < 0){
                    Log.d("tag", "ball block ner höger");
                    float tempX = moveX * -1;
                    float tempY = moveY * -1;
                    moveX = tempY;
                    moveY = tempX;
                    collisionBlock = true;
                }
            }*/


            /**
             * Träff på blocken 4 sidor uppe, nere, vänster eller höger.
             */
            if (bollDX >= blockAX && bollDY == blockAY   &&    bollCX <= blockBX && bollCY == blockBY   &&   bollCY <= blockCY){           //   && ballXNextMove <= thisBlockX + blockSize && ballYNextMove >= thisBlockY + blockSize && ballXNextMove <= thisBlockX + blockSize) {
                //Träff på block uppefrån
                Log.d("tag", "ball block uppe");
                moveX = moveX * 2;
                moveY = 0;
                collisionBlock = true;

            }else if(bollBX >= blockCX && bollBY == blockCY   &&    bollAX <= blockDX && bollAY == blockDY   &&   bollAY >= blockAY) {
                //Träff på block underifrån
                Log.d("tag", "ball block nere");
                moveY = moveY *-1;
                collisionBlock = true;

            }else if(bollDX == blockAX && bollDY >= blockAY   &&    bollBX == blockCX && bollBY <= blockCY   &&   bollDX <= blockDX) {
                //Träff på block från vänster
                Log.d("tag", "ball block vänster");
                moveX = moveX * 2;
                moveX = moveX *-1;
                collisionBlock = true;

            }else if(bollCX == blockBX && bollCY >= blockBY   &&    bollAX == blockDX && bollAY <= blockDY   &&   bollBX >= blockBX){
                //Träff på block från höger
                Log.d("tag", "ball block höger");
                moveX = moveX * 2;
                moveX = moveX *-1;
                collisionBlock = true;

            }
            /**
             * Om det inte är en träff
             */
            else {
                collisionBlock = false;
            }

        }
        return collisionBlock;
    }

    public boolean boundaryCheck(float x, float y){

        //Träff av boundaries vänster
        if(x <= 0 ){
            if(moveX < 0) {
                Log.d("tag", "ball boundaries vänster");
                moveX = moveX * -1;
                moveX = moveX * 2;
                collisionBoundaries = true;
            }
        }
        //Träff av boundaries höger
        if(x + blockSize >= screenWidth){
            if(moveX > 0) {
                Log.d("tag", "ball boundaries höger");
                moveX = moveX * -1;
                moveX = moveX * 2;
                collisionBoundaries = true;
            }
        }
        //Träff av boundaries uppe
        if (y <= 0) {
            if(moveY < 0) {
                Log.d("tag", "ball boundaries uppe");
                moveY = moveY * -1;
                collisionBoundaries = true;
            }
        }
        //Träff av boundaries nere
        if (y + blockSize >= screenWidth ) {
            if(moveY > 0) {
                Log.d("tag", "ball boundaries nere");
                //moveY = moveY * -1;
                moveX = moveX * 2;
                moveY = 0;
                collisionBoundaries = true;
            }
        }
        return collisionBoundaries;
    }
}

