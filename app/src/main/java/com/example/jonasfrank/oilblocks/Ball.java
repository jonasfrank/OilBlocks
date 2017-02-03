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

    public int startMoveY = 20;
    public float groundFriction = (float) 1.05;
    public float wallFriction = (float) 1.5;
    public float speedX = 20;
    public float speedY = 20;




    public Ball(Context context){
        super(context);
        setImageResource(R.drawable.ball);
    }

    public float getSpeedX(){
        return speedX;
    }

    public float getSpeedY(){
        return speedY;
    }

    public void setBall(int startScreenWidth, int blockNumberInRow, Board startBoard){

        screenWidth = startScreenWidth;
        blockSize = (float) startScreenWidth / blockNumberInRow;
        board = startBoard;
        moveX = 0;
        moveY = startMoveY;
        speedX = 20;
        speedY = 20;

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
        moveY = startMoveY;
        speedX = 20;
        speedY = 20;
    }


    public void ballMoveX() {
        if(moveX != 0) {
            collisionCheck();
        }

        float ballX = getX();
        float ballY = getY();

        /**
         * Bollförflyttning
         */
        int stopNumber = 150;
        if (speedX > stopNumber || speedX < -stopNumber) {
            moveX = 0;
            speedX = 20;
        }
        if(moveX != 0){
            setX(ballX + (blockSize / moveX ));
        }
        if (moveX == 0 && moveY == 0 ) {
            // Game over
        }
    }

    public void ballMoveY() {
        if(moveY != 0) {
            collisionCheck();
        }

        float ballX = getX();
        float ballY = getY();

        /**
         * Bollförflyttning
         */
        int stopNumber = 150;
        if (speedY > stopNumber || speedY < -stopNumber) {
            moveY = 0;
            speedY = 20;
        }
        if(moveY != 0) {
            setY(ballY + (blockSize / moveY));
        }
        if (moveX == 0 && moveY == 0 ) {
            Log.d("Game", "Over");
        }
    }

    public void collisionCheck() {

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


            collisionBooster = boosterCheck(ballX, ballY, thisBlockType, thisBlockX, thisBlockY, i);
            if (collisionBooster == false) {
                collisionRamp = rampCheck(ballX, ballY, thisBlockType, thisBlockX, thisBlockY, i);
                //slope
            }
            if (collisionRamp == false && collisionBooster == false) {
                collisionBlock = blockCheck(ballX, ballY, thisBlockType, thisBlockX, thisBlockY, i);
            }

            if (collisionBlock == false && collisionBoundaries == false) {
                //Log.d("tag", " kant koll");
                collisionBoundaries = boundaryCheck(ballX, ballY);
            }
            // OM kollision hittas, avbryt loopen för blocks
            if (collisionBlock == true || collisionBooster == true || collisionBoundaries == true || collisionRamp == true) {
                //Log.d("Broken", "Loop");
                break outerLoop;
            }
        }
    }



    /**
     *Todo
     * Ramp bakifrån
     * Ta bort hörnen?
     * olika hastigheter på olika block
     * minskande hastighet på x axeln olika på empty och rullandes på block
     */


    public boolean boosterCheck(float x, float y, Level.B thisBlockType, float thisBlockX, float thisBlockY, int i){
        float ballCenterX = x + (blockSize / 2);
        float ballCenterY = y + (blockSize / 2);

        Block thisBlock = board.blockList.get(i);

        if(thisBlockType == Level.B.BOOSTR) {
            if (ballCenterX > thisBlockX && ballCenterX < thisBlockX + blockSize && ballCenterY > thisBlockY && ballCenterY < thisBlockY + blockSize) {
                Log.d("tag", "ball boost");
                moveX =  thisBlock.getBallChangeDirection();
                speedX = 5;
                collisionBooster = true;
            }else {
                collisionBooster = false;
            }
        }
        return collisionBooster;
    }


    public boolean rampCheck(float x, float y, Level.B thisBlockType, float thisBlockX, float thisBlockY, int i){
        Block thisBlock = board.blockList.get(i);

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

        if(thisBlockType == Level.B.RAMPUL || thisBlockType == Level.B.RAMPUR || thisBlockType == Level.B.RAMPDL || thisBlockType == Level.B.RAMPDR) {



            /**
             * Träff på rampens 4 yttersidor sidor uppe, nere, vänster eller höger.
             */

            if (bollDX >= blockAX && bollDY == blockAY   &&    bollCX <= blockBX && bollCY == blockBY   &&   bollCY <= blockCY){           //   && ballXNextMove <= thisBlockX + blockSize && ballYNextMove >= thisBlockY + blockSize && ballXNextMove <= thisBlockX + blockSize) {
                //Träff på block uppefrån
                if(moveY >= 0) {
                    if (board.blockList.get(i - 8).getBlockType() == Level.B.EMPTY) {
                        if(thisBlockType == Level.B.RAMPDL || thisBlockType == Level.B.RAMPDR) {
                            Log.d("tag", "ball block uppefrån");
                            moveY = 0;
                            speedX = speedX * (float) groundFriction;
                            collisionBlock = true;
                        }
                    }
                }

            }else if(bollBX >= blockCX && bollBY == blockCY   &&    bollAX <= blockDX && bollAY == blockDY   &&   bollAY >= blockAY) {
                //Träff på block underifrån
                if(moveY < 0) {
                    if (board.blockList.get(i + 8).getBlockType() == Level.B.EMPTY) {
                        if(thisBlockType == Level.B.RAMPUL || thisBlockType == Level.B.RAMPUR) {
                            Log.d("tag", "ball block underifrån");
                            moveY = moveY * -1;
                            collisionBlock = true;
                        }
                    }
                }

            }else if(bollDX == blockAX && bollDY >= blockAY   &&    bollBX == blockCX && bollBY <= blockCY   &&   bollDX <= blockDX) {
                //Träff på block från vänster
                if(moveX > 0) {
                    if (board.blockList.get(i - 1).getBlockType() == Level.B.EMPTY) {
                        if(thisBlockType == Level.B.RAMPDR || thisBlockType == Level.B.RAMPUR) {
                            Log.d("tag", "ball block vänster");
                            speedX = speedX * (float) wallFriction;
                            moveX = moveX * -1;
                            collisionBlock = true;
                        }
                    }
                }

            }else if(bollCX == blockBX && bollCY >= blockBY   &&    bollAX == blockDX && bollAY <= blockDY   &&   bollBX >= blockBX){
                //Träff på block från höger
                if(moveX < 0) {
                    if (board.blockList.get(i + 1).getBlockType() == Level.B.EMPTY) {
                        if(thisBlockType == Level.B.RAMPDL || thisBlockType == Level.B.RAMPUL) {
                            Log.d("tag", "ball block höger");
                            speedX = speedX * (float) wallFriction;
                            moveX = moveX * -1;
                            collisionBlock = true;
                        }
                    }
                }
            }


            /**
             * Träff på blocken 4 innersidor.
             */
            if(thisBlockType == Level.B.RAMPUL ) {
                if (bollBX == blockBX && bollDY >= blockAY && bollDY <= blockDY) {
                    //Träff på block högra innersida
                    Log.d("tag", "ball ramp inner höger");
                    moveX = moveX * -1;
                    collisionRamp = true;
                }
                if (bollCY == blockCY && bollDX >= blockCX && bollDX <= blockDX) {
                    //Träff på block nedre innersida
                    Log.d("tag", "ball ramp inner nere");
                    moveY = moveY * -1;
                    collisionRamp = true;
                }
            }

            if(thisBlockType == Level.B.RAMPUR ) {
                if (bollAX == blockAX && bollCY >= blockAY && bollCY <= blockCY) {
                    //Träff på block vänstra innersida
                    Log.d("tag", "ball ramp inner vänster");
                    moveX = moveX * -1;
                    collisionRamp = true;
                }
                if (bollCY == blockCY && bollCX <= blockCX && bollCX >= blockCX) {
                    //Träff på block nedre innersida
                    Log.d("tag", "ball ramp inner nere");
                    moveY = moveY * -1;
                    collisionRamp = true;
                }
            }

            if(thisBlockType == Level.B.RAMPDL ) {
                if (bollBX == blockBX && bollBY <= blockBY && bollBY >= blockBY) {
                    //Träff på block höger innersida
                    Log.d("tag", "ball ramp inner höger");
                    moveX = moveX * -1;
                    collisionRamp = true;
                }
                if (bollAY == blockAY && bollBX >= blockAX && bollBX <= blockBX) {
                    //Träff på block uppe innersida
                    Log.d("tag", "ball ramp inner uppe");
                    moveY = moveY * -1;
                    collisionRamp = true;
                }
            }

            if(thisBlockType == Level.B.RAMPDR ) {
                if (bollAX == blockAX && bollAY <= blockDY && bollAY >= blockAY) {
                    //Träff på block vänstra innersida
                    Log.d("tag", "ball ramp inner höger");
                    moveX = moveX * -1;
                    collisionRamp = true;
                }
                if (bollAY == blockAY && bollAX <= blockBX && bollAX >= blockAX) {
                    //Träff på block uppe innersida
                    Log.d("tag", "ball ramp inner uppe");
                    moveY = moveY * -1;
                    collisionRamp = true;
                }
            }







            /*if (x >= thisBlockX && x  <= thisBlockX && y >= thisBlockY && y <= thisBlockY) {
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
            }*/
        }
        return collisionRamp;
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
                    if(board.blockList.get(i - 8).getBlockType() == Level.B.EMPTY) {
                        Log.d("tag", "ball block uppe vänster");
                    float tempX = moveX * -1;
                    float tempY = moveY * -1;
                    moveX = tempY;
                    moveY = tempX;


                        collisionBlock = true;
                    }
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
                if(moveY >= 0) {
                    if (board.blockList.get(i - 8).getBlockType() == Level.B.EMPTY) {
                        Log.d("tag", "ball block uppefrån");
                        moveY = 0;
                        speedX = speedX * (float)groundFriction;
                        collisionBlock = true;
                    }
                }

            }else if(bollBX >= blockCX && bollBY == blockCY   &&    bollAX <= blockDX && bollAY == blockDY   &&   bollAY >= blockAY) {
                //Träff på block underifrån
                if(moveY < 0) {
                    if (board.blockList.get(i + 8).getBlockType() == Level.B.EMPTY) {
                        Log.d("tag", "ball block underifrån");
                        moveY = moveY * -1;
                        collisionBlock = true;
                    }
                }

            }else if(bollDX == blockAX && bollDY >= blockAY   &&    bollBX == blockCX && bollBY <= blockCY   &&   bollDX <= blockDX) {
                //Träff på block från vänster
                if(moveX > 0) {
                    if (board.blockList.get(i - 1).getBlockType() == Level.B.EMPTY) {
                        Log.d("tag", "ball block vänster");
                        speedX = speedX * (float) wallFriction;
                        moveX = moveX * -1;
                        collisionBlock = true;
                    }
                }

            }else if(bollCX == blockBX && bollCY >= blockBY   &&    bollAX == blockDX && bollAY <= blockDY   &&   bollBX >= blockBX){
                //Träff på block från höger
                if(moveX < 0) {
                    if (board.blockList.get(i + 1).getBlockType() == Level.B.EMPTY) {
                        Log.d("tag", "ball block höger");
                        speedX = speedX * (float) wallFriction;
                        moveX = moveX * -1;
                        collisionBlock = true;
                    }
                }
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
                speedX = speedX * (float)wallFriction;
                collisionBoundaries = true;
            }
        }
        //Träff av boundaries höger
        if(x + blockSize >= screenWidth){
            if(moveX > 0) {
                Log.d("tag", "ball boundaries höger");
                moveX = moveX * -1;
                speedX = speedX * (float)wallFriction;
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
            if(moveY >= 0) {
                Log.d("tag", "ball boundaries nere");
                speedX = speedX * (float)groundFriction;
                moveY = 0;
                collisionBoundaries = true;
            }
        }
        return collisionBoundaries;
    }
}

