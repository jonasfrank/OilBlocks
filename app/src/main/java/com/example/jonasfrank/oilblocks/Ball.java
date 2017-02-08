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
    public Game game;
    public float startBallX;
    public float startBallY;
    public float moveX = 0;
    public float moveY = 0;

    boolean collisionRamp;
    boolean collisionBooster;
    boolean collisionBlock;
    boolean collisionBoundaries;


    public float groundFriction = (float) 1.05;
    public float wallFriction = (float) 1.5;

    public int startMoveX = 0;
    public int startMoveY = 20;
    public int startSpeedX = 0;
    public int startSpeedY = 5;
    public int speedX;
    public int speedY;
    public int lapX;
    public int lapY;
    public int lapGravity;

    public int maxMove = 8;




    public Ball(Context context){
        super(context);
        setImageResource(R.drawable.ball);
    }

    /*public float getSpeedX(){
        return speedX;
    }

    public float getSpeedY(){
        return speedY;
    }*/

    public void setBall(int startScreenWidth, int blockNumberInRow, Board startBoard, Game startGame){

        screenWidth = startScreenWidth;
        blockSize = (float) startScreenWidth / blockNumberInRow;
        board = startBoard;
        game = startGame;

        moveX = startMoveX;
        moveY = startMoveY;
        speedX = startSpeedX;
        speedY = startSpeedY;
        lapX = 0;
        lapY = 0;

        setLayoutParams(new FrameLayout.LayoutParams((int)blockSize, (int) blockSize));

        startBallX = board.startPosX;
        startBallY = board.startPosY;
        setX(startBallX);
        setY(startBallY);

    }

    public void restartBall(){
        setX(startBallX);
        setY(startBallY);
        moveX = startMoveX;
        moveY = startMoveY;
        speedX = startSpeedX;
        speedY = startSpeedY;
        lapX = 0;
        lapY = 0;
    }


    public void ballMove() {
        //if(moveX != 0) {
            collisionCheck();
       // }
        lapX++;
        lapY++;

        float ballX = getX();
        float ballY = getY();


        /**
         * Bollförflyttning
         */
        //Log.d("tag", "ball speesd " + speedX + " " + speedY);

        if(speedX > maxMove){
            moveX = 0;
        }
        if (lapX == speedX) {
            lapX = 0;
            if (moveX != 0) {
                setX(ballX + (blockSize / moveX));
            }
        }


        if(speedY > maxMove) {
            moveY = 0;
        }
        if (lapY == speedY) {
            lapY = 0;

            if (moveY != 0) {
                setY(ballY + (blockSize / moveY));
            }
        }

        //gameOverCheck();
    }


    public void gameOverCheck(){
        if(moveX == 0 && moveY == 0) {
            Log.d("Game", "Over");
            game.running = false;
            restartBall();
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
            collisionRamp = rampCheck(ballX, ballY, thisBlockType, thisBlockX, thisBlockY, i);
            collisionBlock = blockCheck(ballX, ballY, thisBlockType, thisBlockX, thisBlockY, i);
            /*if (collisionBooster == false) {
                //Log.d("tag", "ball test1 ");
                collisionRamp = rampCheck(ballX, ballY, thisBlockType, thisBlockX, thisBlockY, i);
                //slope
            }
            if (collisionRamp == false) {
                //Log.d("tag", "ball test2 ");
                collisionBlock = blockCheck(ballX, ballY, thisBlockType, thisBlockX, thisBlockY, i);
            }

            if (collisionBlock == false && collisionBoundaries == false && collisionRamp == false) {
                //Log.d("tag", "ball test3 ");
                //Log.d("tag", " kant koll");
                //collisionBoundaries = boundaryCheck(ballX, ballY, i);
            }*/
            // OM kollision hittas, avbryt loopen för blocks
           if (collisionBlock == true || collisionRamp == true) {
                //Log.d("Broken", "Loop" + i);
                break outerLoop;
            }

            //Om det inte är någon kolletion
            if(i == board.blockList.size() - 1 && (collisionBlock == false && collisionBooster == false && collisionBoundaries == false && collisionRamp == false)){
                Log.d("tag", "ball ingen kolletion");
                if(moveY > 0 || moveY == 0) {
                    lapGravity++;
                    if (lapGravity == 5) {
                        lapY = 0;
                        moveY = 20;
                        lapGravity = -15;
                        if (speedY > 1) {
                            speedY--;
                        }
                    }
                }else if(moveY < 0) {
                    lapGravity++;
                    if (lapGravity == 5) {
                        lapY = 0;
                        moveY = -20;
                        lapGravity = -15;
                        if (speedY >= 1) {
                            speedY++;
                        }
                    }
                }
            }
        }

        //if (collisionBlock == false && collisionBoundaries == false && collisionRamp == false) {
            collisionBoundaries = boundaryCheck(ballX, ballY);
        //}
    }


    public boolean boosterCheck(float x, float y, Level.B thisBlockType, float thisBlockX, float thisBlockY, int i){
        float ballCenterX = x + (blockSize / 2);
        float ballCenterY = y + (blockSize / 2);

        Block thisBlock = board.blockList.get(i);

        if(thisBlockType == Level.B.BOOSTR || thisBlockType == Level.B.BOOSTL ){
            if (ballCenterX > thisBlockX && ballCenterX < thisBlockX + blockSize && ballCenterY > thisBlockY && ballCenterY < thisBlockY + blockSize) {
                Log.d("tag", "ball boost");
                moveX =  thisBlock.getBallChangeDirection();
                lapX = 0;
                speedX = 1;
                collisionBooster = true;
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

            if (bollDX > blockAX && bollDY == blockAY   &&    bollCX < blockBX && bollCY == blockBY   &&   bollCY <= blockCY){           //   && ballXNextMove <= thisBlockX + blockSize && ballYNextMove >= thisBlockY + blockSize && ballXNextMove <= thisBlockX + blockSize) {
                //Träff på ramp uppefrån
                if(moveY >= 0) {
                    if (board.blockList.get(i - 8).getBlockType() == Level.B.EMPTY) {
                        if(thisBlockType == Level.B.RAMPDL || thisBlockType == Level.B.RAMPDR) {
                            Log.d("tag", "ball ramp uppefrån");
                            moveY = 0;
                            //speedX = speedX * (float) groundFriction;
                            collisionBlock = true;
                        }
                    }
                }

            }else if(bollBX > blockCX && bollBY == blockCY   &&    bollAX < blockDX && bollAY == blockDY   &&   bollAY >= blockAY) {
                //Träff på ramp underifrån
                if(moveY < 0) {
                    if (board.blockList.get(i + 8).getBlockType() == Level.B.EMPTY) {
                        if(thisBlockType == Level.B.RAMPUL || thisBlockType == Level.B.RAMPUR) {
                            Log.d("tag", "ball ramp underifrån");
                            moveY = moveY * -1;
                            collisionBlock = true;
                        }
                    }
                }

            }else if(bollDX == blockAX && bollDY > blockAY   &&    bollBX == blockCX && bollBY < blockCY   &&   bollDX <= blockDX) {
                //Träff på ramp från vänster
                if(moveX > 0) {
                    if (board.blockList.get(i - 1).getBlockType() == Level.B.EMPTY) {
                        if(thisBlockType == Level.B.RAMPDR || thisBlockType == Level.B.RAMPUR) {
                            Log.d("tag", "ball ramp vänster");
                            //speedX = speedX * (float) wallFriction;
                            moveX = 3;
                            speedX++;
                            collisionBlock = true;
                        }
                    }
                }

            }else if(bollCX == blockBX && bollCY > blockBY   &&    bollAX == blockDX && bollAY < blockDY   &&   bollBX >= blockBX){
                //Träff på ramp från höger
                if(moveX < 0) {
                    if (board.blockList.get(i + 1).getBlockType() == Level.B.EMPTY) {
                        if(thisBlockType == Level.B.RAMPDL || thisBlockType == Level.B.RAMPUL) {
                            Log.d("tag", "ball ramp höger");
                            //speedX = speedX * (float) wallFriction;
                            moveX = moveX * -1;
                            speedX++;
                            collisionBlock = true;
                        }
                    }
                }
            }


            /**
             * Träff på ramp 4 innersidor.
             */
            if(thisBlockType == Level.B.RAMPUL ) {
                if(bollAX == blockAX && bollAY == blockAY){
                    Log.d("tag", "ball ramp RAMPUL mitt i");
                    if(moveX > 0 ){
                        Log.d("tag", "ball ramp RAMPUL mitt i 1");
                        moveX = 0;
                        moveY = -20;
                        speedY = speedX;
                        lapY = 0;
                    }
                    else if (moveY > 0){
                        Log.d("tag", "ball ramp RAMPUL mitt i 2");
                        moveX = -20;
                        moveY = 0;
                        speedX = speedY;
                        lapX = 0;
                    }
                    collisionRamp = true;
                }else {
                    if (bollBX == blockBX && bollDY > blockBY && bollDY < blockDY) {
                        //Träff på block högra innersida
                        if(moveY > 0) {
                            Log.d("tag", "ball ramp RAMPUL inner höger");
                            moveX = 0;

                        }
                        collisionRamp = true;
                    }
                    if (bollCY == blockCY && bollDX > blockCX && bollDX < blockDX) {
                        //Träff på block nedre innersida
                        if(moveX > 0) {
                            Log.d("tag", "ball ramp RAMPUL inner nere");
                            moveY = 0;

                        }
                        collisionRamp = true;
                    }
                }
            }

            else if(thisBlockType == Level.B.RAMPUR ) {
                if(bollAX == blockAX && bollAY == blockAY){
                    Log.d("tag", "ball ramp RAMPUR mitt i");
                    if(moveX < 0 ){
                        Log.d("tag", "ball ramp RAMPUR mitt i 1");
                        moveX = 0;
                        moveY = -20;
                        speedX = speedY;
                    }
                    else if (moveY > 0){
                        Log.d("tag", "ball ramp RAMPUR mitt i 2");
                        moveX = 20;
                        moveY = 0;
                        speedY = speedX;
                    }
                }else {
                    if (bollAX == blockAX && bollCY >= blockAY && bollCY <= blockCY) {
                        //Träff på block vänstra innersida
                        if(moveY > 0) {
                            Log.d("tag", "ball ramp RAMPUR inner vänster");
                            moveX = 0;

                        }
                        collisionRamp = true;
                    }
                    if (bollCY == blockCY && bollCX <= blockCX && bollCX >= blockCX) {
                        //Träff på block nedre innersida
                        if(moveX < 0) {
                            Log.d("tag", "ball ramp RAMPUR inner nere");
                            moveY = 0;

                        }
                        collisionRamp = true;
                    }
                }
            }

            else if(thisBlockType == Level.B.RAMPDL ) {
                if(bollAX == blockAX && bollAY == blockAY){
                    Log.d("tag", "ball ramp RAMPDL mitt i");
                    if(moveX > 0 ){
                        Log.d("tag", "ball ramp RAMPDL mitt i 1");
                        moveX = 0;
                        moveY = 20;
                    }
                    else if (moveY < 0){
                        Log.d("tag", "ball ramp RAMPDL mitt i 2");
                        moveX = -20;
                        moveY = 0;
                    }
                } else {
                    if (bollBX == blockBX && bollBY <= blockBY && bollBY >= blockBY) {
                        //Träff på block höger innersida
                        if(moveY < 0) {
                            Log.d("tag", "ball ramp RAMPDL inner höger");
                            moveX = 0;

                        }
                        collisionRamp = true;
                    }
                    if (bollAY == blockAY && bollBX >= blockAX && bollBX <= blockBX) {
                        //Träff på block uppe innersida
                        if(moveX > 0) {
                            Log.d("tag", "ball ramp RAMPDL inner uppe");
                            moveY = 0;

                        }
                        collisionRamp = true;
                    }
                }
            }

            else if(thisBlockType == Level.B.RAMPDR ) {
                if (bollAX == blockAX && bollAY == blockAY) {
                    Log.d("tag", "ball ramp RAMPDR mitt i");
                    if (moveX < 0) {
                        Log.d("tag", "ball ramp RAMPDR mitt i 1");
                        moveX = 0;
                        moveY = 20;
                    } else if (moveY < 0) {
                        Log.d("tag", "ball ramp RAMPDR mitt i 2");
                        moveX = 20;
                        moveY = 0;
                    }
                }
            }else {
                if (bollAX == blockAX && bollAY <= blockDY && bollAY >= blockAY) {
                    //Träff på block vänstra innersida
                    if(moveY < 0) {
                        Log.d("tag", "ball ramp RAMPDR inner höger");
                        moveX = 0;

                    }
                    collisionRamp = true;
                }
                if (bollAY == blockAY && bollAX <= blockBX && bollAX >= blockAX) {
                    //Träff på block uppe innersida
                    if(moveX < 0) {
                        Log.d("tag", "ball ramp RAMPDR inner uppe");
                        moveY = 0;

                    }
                    collisionRamp = true;
                }
            }





            /**
             * Träff på blocken 2 stolpar.
             */
            if(thisBlockType == Level.B.RAMPUL ) {
                if (bollDX == blockCX && bollDY > blockCY && bollBY < blockCY) {
                    //Träff på block nedre stolpe
                    if(moveX > 0) {
                        Log.d("tag", "ball ramp stolpe nedre");
                        moveX = moveX * -1;
                        speedX++;
                        collisionRamp = true;
                    }
                }
                if (bollCY == blockAY && bollDX > blockBX && bollCX < blockBX) {
                    //Träff på block högra stolpe
                    if(moveY > 0) {
                        Log.d("tag", "ball ramp stolpe högra");
                        moveY = 0;
                        collisionRamp = true;
                    }
                }
            }

            else if(thisBlockType == Level.B.RAMPUR ) {
                if (bollCX == blockDX && bollCY > blockDY && bollAY < blockDY) {
                    //Träff på block nedre stolpe
                    Log.d("tag", "ball ramp stolpe nedre");
                    moveX = moveX * -1;
                    speedX++;
                    collisionRamp = true;
                }
                if (bollCY == blockAY && bollDX > blockAX && bollCX < blockAX) {
                    //Träff på block vänstra stolpe
                    Log.d("tag", "ball ramp stolpe vänstra");
                    moveY = moveY * -1;
                    collisionRamp = true;
                }
            }

            else if(thisBlockType == Level.B.RAMPDL ) {
                if (bollBX == blockAX && bollDY > blockAY && bollBY < blockAY) {
                    //Träff på block uppe stolpe
                    Log.d("tag", "ball ramp RAMPDL stolpe uppe");
                    moveX = moveX * -1;
                    speedX++;
                    collisionRamp = true;
                }
                if (bollBY == blockDY && bollBX > blockDX && bollAX < blockDX) {
                    //Träff på block höger stolpe
                    Log.d("tag", "ball ramp RAMPDL stolpe höger");
                    moveY = moveY * -1;
                    collisionRamp = true;
                }
            }

            else if(thisBlockType == Level.B.RAMPDR ) {
                if (bollAX == blockBX && bollCY > blockBY && bollAY < blockBY) {
                    //Träff på block uppe stolpe
                    Log.d("tag", "ball ramp RAMPDR stolpe uppe");
                    moveX = moveX * -1;
                    speedX++;
                    collisionRamp = true;
                }
                if (bollAY == blockDY && bollBX > blockCX && bollAX < blockCX) {
                    //Träff på block vänstra stolpe
                    Log.d("tag", "ball ramp RAMPDR stolpe vänstra");
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

        if(thisBlockType == Level.B.SOFT || thisBlockType == Level.B.SOLID || thisBlockType == Level.B.GOAL) {

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
                    speedX++;
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
                    speedX++;
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
                    speedX++;
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
                    speedX++;
                    float tempY = moveY * -1;
                    moveX = tempY;
                    moveY = tempX;
                    collisionBlock = true;
                }
            }*/


            /**
             * Träff på blocken 4 sidor uppe, nere, vänster eller höger.
             */
            if(bollAX == blockAX && bollAY == blockAY){
                Log.d("tag", "ball mål2");
                moveX = 0;
                moveY = 0;
                game.stopBall(this);

            }
            else if (bollDX > blockAX && bollDY == blockAY   &&    bollCX < blockBX && bollCY == blockBY   &&   bollCY <= blockCY){           //   && ballXNextMove <= thisBlockX + blockSize && ballYNextMove >= thisBlockY + blockSize && ballXNextMove <= thisBlockX + blockSize) {
               if(moveY >= 0) {

                    Log.d("tag", "ball block uppefrån");
                    moveY = 0;

                    if(thisBlockType == Level.B.GOAL){
                        if(bollAX == blockAX){
                            Log.d("tag", "ball mål");
                            moveX = 0;
                            moveY = 20;
                            /*if(bollAY == blockAY){
                                Log.d("tag", "ball mål2");
                                moveX = 0;
                                moveY = 0;
                            }*/
                        }
                    }
                        //speedX = speedX * (float)groundFriction;


                   collisionBlock = true;
                }

            }else if(bollBX > blockCX && bollBY == blockCY   &&    bollAX < blockDX && bollAY == blockDY   &&   bollAY >= blockAY) {
                //Träff på block underifrån
                if(moveY < 0) {
                    if (board.blockList.get(i + 8).getBlockType() == Level.B.EMPTY) {
                        Log.d("tag", "ball block underifrån");
                        moveY = moveY * -1;
                        collisionBlock = true;
                    }
                }

            }else if(bollDX == blockAX && bollDY >= blockAY   &&    bollBX == blockCX && bollBY < blockCY   &&   bollDX <= blockDX) {
                //Träff på block från vänster
                if(moveX > 0) {
                    if (board.blockList.get(i - 1).getBlockType() == Level.B.EMPTY) {
                        Log.d("tag", "ball block vänster");
                       // speedX = speedX * (float) wallFriction;
                        moveX = moveX * -1;
                        speedX++;
                        collisionBlock = true;
                    }
                }

            }else if(bollCX == blockBX && bollCY >= blockBY   &&    bollAX == blockDX && bollAY < blockDY   &&   bollBX >= blockBX){
                //Träff på block från höger
                if(moveX < 0) {
                    if (board.blockList.get(i + 1).getBlockType() == Level.B.EMPTY) {
                        Log.d("tag", "ball block höger");
                        //speedX = speedX * (float) wallFriction;
                        moveX = moveX * -1;
                        speedX++;
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
                //speedX++;
                //speedX = speedX * (float)wallFriction;
                collisionBoundaries = true;
            }
        }
        //Träff av boundaries höger
        if(x + blockSize >= screenWidth){
            if(moveX > 0) {
                Log.d("tag", "ball boundaries höger");
                moveX = moveX * -1;
                speedX++;
                //speedX = speedX * (float)wallFriction;
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
                //speedX = speedX * (float)groundFriction;
                speedY = 0;
                moveY = 0;
                collisionBoundaries = true;
            }
        }
        return collisionBoundaries;
    }
}

