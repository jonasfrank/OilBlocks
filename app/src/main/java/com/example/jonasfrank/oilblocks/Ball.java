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
import android.widget.Toast;

import java.util.Random;
import java.util.logging.Handler;


public class Ball extends ImageView {

    int screenWidth;
    float blockSize;
    float boundariesHeightDP;
    Board board;
    Game game;
    float startBallX;
    float startBallY;
    float moveX = 0;
    float moveY = 0;

    boolean collisionRamp;
    boolean collisionBooster;
    boolean collisionBlock;
    boolean collisionBoundaries;


    int startMoveX = 0;
    int startMoveY = 20;
    int startSpeedX = 0;
    int startSpeedY = 5;
    int speedX;
    int speedY;
    int lapX;
    int lapY;
    int lapGravity;
    int gameLostDelay;

    int maxMove = 8;

    int blockListSize;
    int blockInRowSize;





    public Ball(Context context){
        super(context);
        setImageResource(R.drawable.ball);
    }


    public void setBall(int startScreenWidth, int blockNumberInRow, Board startBoard, Game startGame){

        screenWidth = startScreenWidth;
        blockSize = (float) startScreenWidth / blockNumberInRow;
        board = startBoard;
        game = startGame;
        boundariesHeightDP = board.getBoundariesHeightDP();

        blockListSize = board.blockList.size() - 1;
        blockInRowSize = blockNumberInRow;

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

        collisionCheck();

        lapX++;
        lapY++;

        float ballX = getX();
        float ballY = getY();


        /**
         * Bollförflyttning
         */

        if(speedX > maxMove){
            moveX = 0;
        }
        if (lapX == speedX) {
            lapX = 0;
            if (moveX != 0) {
                setX(ballX + (blockSize / moveX));
                gameLostDelay = 0;
            }
        }


        if(speedY > maxMove) {
            moveY = 0;
        }
        if (lapY == speedY) {
            lapY = 0;

            if (moveY != 0) {
                setY(ballY + (blockSize / moveY));
                gameLostDelay = 0;
            }
        }

    }


    public void gameOverCheck(){
        if(moveX == 0 && moveY == 0) {
            gameLostDelay++;
            if (gameLostDelay >= 20) {
                game.lostGame(this);
            }
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

            if (collisionBlock == true || collisionRamp == true) {
                break outerLoop;
            }
            if (collisionBlock == true || collisionRamp == true|| collisionBooster == true) {
                lapGravity = 0;
            }

            //Om det inte är någon kollision
            if(i == board.blockList.size() - 1 && (collisionBlock == false && collisionBooster == false && collisionBoundaries == false && collisionRamp == false)){
                Log.d("tag", "ball ingen kollision");
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

        collisionBoundaries = boundaryCheck(ballX, ballY);
        gameOverCheck();
    }


    public boolean boosterCheck(float x, float y, Level.B thisBlockType, float thisBlockX, float thisBlockY, int i){
        float ballCenterX = x + (blockSize / 2);
        float ballCenterY = y + (blockSize / 2);


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


        if(thisBlockType == Level.B.BOOSTR){
            if (ballCenterX > thisBlockX && ballCenterX < thisBlockX + blockSize && ballCenterY > thisBlockY && ballCenterY < thisBlockY + blockSize) {
                if(bollAY == blockAY && i + 1 <= blockListSize && board.blockList.get(i + 1).getSideL() == true) {
                    Log.d("tag", "ball boostR" );
                    if((moveY > 0) || (i >= 0 && i <= blockInRowSize - 1) || (moveY < 0 && (i - blockInRowSize >= 0 && (board.blockList.get(i - blockInRowSize).getSideD() == false) || (i - blockInRowSize + 1 >= 0 && board.blockList.get(i - blockInRowSize + 1).getSideD() == false) ))) {
                        moveY = 0;
                    }
                    moveX = thisBlock.getBallChangeDirection();
                    lapX = 0;
                    speedX = 1;

                    collisionBooster = true;
                }
            }
        }else if(thisBlockType == Level.B.BOOSTL ){
            if (ballCenterX > thisBlockX && ballCenterX < thisBlockX + blockSize && ballCenterY > thisBlockY && ballCenterY < thisBlockY + blockSize) {
                if(bollAY == blockAY && i - 1 >= 0 && board.blockList.get(i - 1).getSideR() == true) {
                    Log.d("tag", "ball boostL");
                    if((moveY > 0) || (i >= 0 && i <= blockInRowSize - 1) || (moveY < 0 && ((i - blockInRowSize >= 0 && board.blockList.get(i - blockInRowSize).getSideD() == false) || (i - blockInRowSize - 1 >= 0 && board.blockList.get(i - blockInRowSize - 1).getSideD() == false)))) {
                        moveY = 0;
                    }
                    moveX = thisBlock.getBallChangeDirection();
                    lapX = 0;
                    speedX = 1;

                    collisionBooster = true;
                }
            }
        }
        return collisionBooster;
    }


    public boolean rampCheck(float x, float y, Level.B thisBlockType, float thisBlockX, float thisBlockY, int i){

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
             * Träff på ramp 4 innersidor.
             */
            if (thisBlockType == Level.B.RAMPUL) {
                if (bollAX == blockAX && bollAY == blockAY) {
                    Log.d("tag", "ball ramp RAMPUL mitt i");
                    if (moveX > 0) {
                        if(i - blockInRowSize >= 0 && board.blockList.get(i - blockInRowSize).getSideD() == true) {
                            Log.d("tag", "ball ramp RAMPUL mitt i 1");
                            moveX = 0;
                            moveY = -20;
                            speedY = speedX;
                        }else{
                            moveX = moveX * -1;
                        }
                        lapY = 0;
                    } else if (moveY > 0) {
                        Log.d("tag", "ball ramp RAMPUL i och blocktype" + i +" "+ board.blockList.get(i - 1).getSideR() + " " + board.blockList.get(i - 1).getBlockType());
                        if(i - 1 >= 0 && board.blockList.get(i - 1).getSideR() == true && i % blockInRowSize != 0) {
                            Log.d("tag", "ball ramp RAMPUL mitt i 2");
                            moveX = -20;
                            moveY = 0;
                            speedX = speedY;
                            lapX = 0;
                        }else{
                            moveY = 0;
                        }
                    }
                    collisionRamp = true;
                } else {
                    if (bollBX == blockBX && bollDY > blockBY && bollDY < blockDY) {
                        //Träff på block högra innersida
                        if (moveY > 0) {
                            Log.d("tag", "ball ramp RAMPUL inner höger");
                            moveX = 0;
                        }else{
                            moveX = moveX * -1;
                        }
                        collisionRamp = true;
                    }
                    if (bollCY == blockCY && bollDX > blockCX && bollDX < blockDX) {
                        //Träff på block nedre innersida
                        //if (moveX > 0) {
                            Log.d("tag", "ball ramp RAMPUL inner nere");
                            moveY = 0;
                        //}
                        collisionRamp = true;
                    }
                }
            } else if (thisBlockType == Level.B.RAMPUR) {
                if (bollAX == blockAX && bollAY == blockAY) {
                    Log.d("tag", "ball ramp RAMPUR mitt i");
                    if (moveX < 0) {
                        if(i - blockInRowSize >= 0 && board.blockList.get(i - blockInRowSize).getSideD() == true) {
                            Log.d("tag", "ball ramp RAMPUR mitt i 1");
                            moveX = 0;
                            moveY = -20;
                            speedY = speedX;
                        }else{
                            moveX = moveX * -1;
                            Log.d("tag", "ball ramp RAMPUR mitt i 1");
                        }
                        lapY = 0;
                    } else if (moveY > 0) {
                        if(i + 1 <= blockListSize && board.blockList.get(i + 1).getSideL() == true && (i + 1) % blockInRowSize != 0) {
                            Log.d("tag", "ball ramp RAMPUR mitt i 2");
                            moveX = 20;
                            moveY = 0;
                            speedX = speedY;
                            lapX = 0;

                        }else{
                            moveY = 0;
                        }
                    }
                    collisionRamp = true;
                } else {
                    if (bollAX == blockAX && bollCY > blockAY && bollCY < blockCY) {
                        //Träff på block vänstra innersida
                        if (moveY > 0) {
                            Log.d("tag", "ball ramp RAMPUR inner vänster");
                            moveX = 0;
                        }else{
                            moveX = moveX * -1;
                        }
                        collisionRamp = true;
                    }
                    if (bollCY == blockCY && bollCX > blockCX && bollCX < blockDX) {
                        //Träff på block nedre innersida
                        //if (moveX < 0) {
                            Log.d("tag", "ball ramp RAMPUR inner nere");
                            moveY = 0;
                        //}
                        collisionRamp = true;
                    }
                }
            } else if (thisBlockType == Level.B.RAMPDL) {
                if (bollAX == blockAX && bollAY == blockAY) {
                    Log.d("tag", "ball ramp RAMPDL mitt i");
                    if (moveX > 0) {
                        if(i + blockInRowSize <= blockListSize && board.blockList.get(i + blockInRowSize).getSideU() == true) {
                            Log.d("tag", "ball ramp RAMPDL mitt i 1");
                            moveX = 0;
                            moveY = 20;
                            speedY = speedX;
                        }else{
                            moveX = moveX * -1;
                        }
                    } else if (moveY < 0) {
                        if(i - 1 >= 0 && board.blockList.get(i - 1).getSideR() == true) {
                            Log.d("tag", "ball ramp RAMPDL mitt i 2");
                            moveX = -20;
                            moveY = 0;
                            speedX = speedY;
                        }
                        else{
                            moveY = moveY * -1;
                        }
                    }
                    collisionRamp = true;
                } else {
                    if (bollBX == blockBX && bollBY > blockBY && bollBY < blockDY) {
                        //Träff på block höger innersida
                        if (moveY < 0) {
                            Log.d("tag", "ball ramp RAMPDL inner höger");
                            moveX = 0;
                        }else{
                            moveX = moveX * -1;
                        }
                        collisionRamp = true;
                    }
                    if (bollAY == blockAY && bollBX > blockAX && bollBX < blockBX) {
                        //Träff på block uppe innersida
                        if (moveY < 0) {
                            Log.d("tag", "ball ramp RAMPDL inner uppe");
                            moveY = moveY * -1;
                            collisionRamp = true;
                        }

                    }
                }
            } else if (thisBlockType == Level.B.RAMPDR) {
                if (bollAX == blockAX && bollAY == blockAY) {
                    Log.d("tag", "ball ramp RAMPDR mitt i");
                    if (moveX < 0) {
                        if(i + blockInRowSize <= blockListSize && board.blockList.get(i + blockInRowSize).getSideU() == true) {
                            Log.d("tag", "ball ramp RAMPDR mitt i 1");
                            moveX = 0;
                            moveY = 20;
                            speedY = speedX;
                        }else{
                            moveX = moveX * -1;
                        }
                    } else if (moveY < 0) {
                        if(i + 1 <= blockListSize && board.blockList.get(i + 1).getSideL() == true) {
                            Log.d("tag", "ball ramp RAMPDR mitt i 2");
                            moveX = 20;
                            moveY = 0;
                            speedX = speedY;
                        }else{
                            moveY = moveY * -1;
                        }
                    }
                    collisionRamp = true;
                }else {
                    if (bollAX == blockAX &&bollAY > blockAY && bollAY < blockCY ) {
                        //Träff på block vänstra innersida
                        if (moveY < 0) {
                            Log.d("tag", "ball ramp RAMPDR inner höger");
                            moveX = 0;
                        }else{
                            moveX = moveX * -1;
                        }
                        collisionRamp = true;
                    }
                    if (bollAY == blockAY && bollAX < blockBX && bollAX > blockAX) {
                        //Träff på block uppe innersida
                        if (moveY < 0) {
                            Log.d("tag", "ball ramp RAMPDR inner uppe");
                            moveY = moveY * -1;
                            collisionRamp = true;
                        }

                    }
                }
            }

            /**
             * Träff på rampens 4 yttersidor sidor uppe, nere, vänster eller höger.
             */

            if (bollDX > blockAX && bollDY == blockAY && bollCX < blockBX && bollCY <= blockCY) {
                //Träff på ramp uppefrån
                if (moveY >= 0) {
                    if (i - blockInRowSize >= 0 && board.blockList.get(i - blockInRowSize).getSideD() == true) {
                        if (thisBlockType == Level.B.RAMPDL || thisBlockType == Level.B.RAMPDR) {
                            Log.d("tag", "ball ramp uppefrån" + thisBlockType);
                            moveY = 0;
                            collisionBlock = true;
                        }
                    }

                }

            } else if (bollBX > blockCX && bollBY == blockCY && bollAX < blockDX && bollAY >= blockAY) {
                //Träff på ramp underifrån
                if (moveY < 0) {
                    //if (i + blockInRowSize <= blockListSize && board.blockList.get(i + blockInRowSize).getSideU() == true) {
                    if (board.blockList.get(i + blockInRowSize).getSideU() == true
                            && board.blockList.get(i + blockInRowSize).getBlockType() != Level.B.BOOSTL
                            && board.blockList.get(i + blockInRowSize).getBlockType() != Level.B.BOOSTR
                            || ((bollBX >= blockCX && bollBX < blockCX + (blockSize / 2))
                                || ((bollAX <= blockDX && bollAX > blockDX - (blockSize / 2))
                                && ((board.blockList.get(i + blockInRowSize).getBlockType() == Level.B.BOOSTR)
                                || (board.blockList.get(i + blockInRowSize).getBlockType() == Level.B.BOOSTL))))) {

                        if (thisBlockType == Level.B.RAMPUL || thisBlockType == Level.B.RAMPUR) {
                            Log.d("tag", "ball ramp underifrån" + thisBlockType);
                            moveY = moveY * -1;
                            collisionBlock = true;

                            if(bollAX == blockCX && bollAY == blockCY
                                    && (board.blockList.get(i + blockInRowSize - 1).getSideR() == false
                                    || board.blockList.get(i + blockInRowSize + 1).getSideL() == false)){
                                Log.d("tag", "ball block underifrån mitt i");
                                moveX = moveX * -1;
                            }
                        }
                    }
                }

            //} else if (bollBX == blockAX && bollDY >= blockAY && bollBY < blockCY) {
            }else if(bollDX == blockAX && bollDY >= blockAY   &&    bollBX == blockCX && bollBY < blockCY   &&   bollAX <= blockAX) {
                //Träff på ramp från vänster
                if (moveX > 0) {
                    //if (i - blockInRowSize >= 0 && board.blockList.get(i - 1).getSideR() == true && board.blockList.get(i - 1).getSideU() == true) {
                        //if (i - 1 - blockInRowSize >= 0 && board.blockList.get(i - 1 - blockInRowSize).getBlockType() != Level.B.BOOSTR) {
                    if (board.blockList.get(i - 1).getSideR() == true && board.blockList.get(i - 1).getBlockType() != Level.B.RAMPDR) {
                        if ((i - 1 - blockInRowSize >= 0 && board.blockList.get(i - 1 - blockInRowSize).getBlockType() != Level.B.BOOSTR) || (i - 1 - blockInRowSize >= 0 && board.blockList.get(i - 1 - blockInRowSize).getBlockType() == Level.B.BOOSTR && bollDY != blockAY) || (i - 1 - blockInRowSize <= 0)) {

                            if (thisBlockType == Level.B.RAMPDR || thisBlockType == Level.B.RAMPUR) {
                                Log.d("tag", "ball ramp vänster" + thisBlockType);
                                moveX = moveX * -1;
                                speedX++;
                                collisionBlock = true;
                            }
                        }
                    }
                }
            //}else if(bollCX == blockBX && bollCY >= blockBY   &&    bollAX == blockDX && bollAY < blockDY   &&   bollBX >= blockBX){
            }else if(bollCX == blockBX && bollCY >= blockBY && bollAY < blockDY){
                //Träff på ramp från höger
                if (moveX < 0) {
                    //if (i + blockInRowSize <= blockListSize && board.blockList.get(i + 1).getSideL() == true && board.blockList.get(i + 1).getSideU() == true) {
                        //if (i + 1 - blockInRowSize >= 0 && board.blockList.get(i + 1 - blockInRowSize).getBlockType() != Level.B.BOOSTL) {
                    if (board.blockList.get(i + 1).getSideL() == true && board.blockList.get(i + 1).getBlockType() != Level.B.RAMPDL) {
                        if ((i + 1 - blockInRowSize >= 0 && board.blockList.get(i + 1 - blockInRowSize).getBlockType() != Level.B.BOOSTL) || (i + 1 - blockInRowSize >= 0 && board.blockList.get(i + 1 - blockInRowSize).getBlockType() == Level.B.BOOSTL && bollCY != blockBY) || (i + 1 - blockInRowSize <= 0)) {

                            if (thisBlockType == Level.B.RAMPDL || thisBlockType == Level.B.RAMPUL) {
                                //if (board.blockList.get(i + 1).getBlockType() == Level.B.EMPTY) {
                                Log.d("tag", "ball ramp höger" + thisBlockType);
                                moveX = moveX * -1;
                                speedX++;
                                collisionBlock = true;
                                //}
                            }
                        }
                    }
                }
            }



            /**
             * Träff på blocken 2 stolpar.
             */
            if (thisBlockType == Level.B.RAMPUL) {
                if ((bollDX == blockCX && bollDY > blockCY && bollBY < blockCY) || (bollDX == blockCX && bollDY == blockCY && i + blockInRowSize - 1 <= blockListSize && board.blockList.get(i + blockInRowSize - 1).getSideU() == true && i - blockInRowSize >= 0 && board.blockList.get(i - 1).getSideD() == true && board.blockList.get(i - 1).getBlockType() != Level.B.RAMPDR && board.blockList.get(i - 1).getBlockType() != Level.B.BOOSTR)) {
                    //Träff på block nedre stolpe
                    if (moveX > 0) {
                        Log.d("tag", "ball ramp RAMPUL stolpe nedre");
                        moveX = moveX * -1;
                        speedX++;

                        collisionRamp = true;
                    }
                }
                if ((bollCY == blockAY && bollDX >= blockBX && bollCX < blockBX)) {
                    //Träff på block högra stolpe
                    if (moveY >= 0 && bollDX != blockBX) {
                        Log.d("tag", "ball ramp RAMPUL stolpe högra");
                        moveY = 0;
                        collisionRamp = true;
                    } else if(bollDX == blockBX && (moveX > 0 || moveY > 0) && i - blockInRowSize >= 0 && board.blockList.get(i - blockInRowSize).getBlockType() != Level.B.BOOSTR){
                        Log.d("tag", "ball ramp RAMPUL stolpe högra testetstets");
                        moveX = 0;
                        moveY = 20;
                        collisionRamp = true;
                    }


                }
            } else if (thisBlockType == Level.B.RAMPUR) {
                if ((bollCX == blockDX && bollCY > blockDY && bollAY < blockDY) || (bollCX == blockDX && bollCY == blockDY && i + blockInRowSize + 1 <= blockListSize && board.blockList.get(i + blockInRowSize + 1).getSideU() == true && i + 1 <= blockListSize && board.blockList.get(i + 1).getSideD() == true && board.blockList.get(i + 1).getBlockType() != Level.B.RAMPDL && board.blockList.get(i + 1).getBlockType() != Level.B.BOOSTL)) {
                    //Träff på block nedre stolpe
                    if (moveX < 0) {
                        Log.d("tag", "ball ramp RAMPUR stolpe nedre");
                        moveX = moveX * -1;
                        speedX++;
                        collisionRamp = true;
                    }
                }
                if (bollCY == blockAY && bollDX > blockAX && bollCX <= blockAX) {
                    //Träff på block vänstra stolpe
                    if (moveY >= 0 && bollCX != blockAX) {
                        Log.d("tag", "ball ramp RAMPUR stolpe högra");
                        moveY = 0;
                        collisionRamp = true;
                    } else if(bollCX == blockAX && (moveX < 0 || moveY > 0) && i - blockInRowSize >= 0 && board.blockList.get(i - blockInRowSize).getBlockType() != Level.B.BOOSTL){
                        Log.d("tag", "ball ramp RAMPUR stolpe högra testetstets");
                        moveX = 0;
                        moveY = 20;
                        collisionRamp = true;
                    }
                }
            }else if (thisBlockType == Level.B.RAMPDL) {
                if ((bollBX == blockAX && bollDY > blockAY && bollBY < blockAY) || (bollBX == blockAX && bollDY == blockAY && i - 1 >= 0 && board.blockList.get(i - 1).getSideR() == true && board.blockList.get(i - 1).getSideU() == true) && (i - blockInRowSize - 1 >= 0 && board.blockList.get(i - blockInRowSize - 1).getBlockType() != Level.B.BOOSTR)) {
                    //Träff på block uppe stolpe
                    if (moveX > 0) {
                        Log.d("tag", "ball ramp RAMPDL stolpe uppe");
                        moveX = moveX * -1;
                        speedX++;
                        collisionRamp = true;
                    }
                }
                if (bollBY == blockDY && bollBX > blockDX && bollAX < blockDX) {
                    //Träff på block höger stolpe
                    if (moveY < 0) {
                        Log.d("tag", "ball ramp RAMPDL stolpe höger");
                        moveY = moveY * -1;
                        collisionRamp = true;
                    }
                }
            } else if (thisBlockType == Level.B.RAMPDR) {
                if ((bollAX == blockBX && bollCY > blockBY && bollAY < blockBY) || (bollAX == blockBX && bollCY == blockBY && i + 1 <= blockListSize && board.blockList.get(i + 1).getSideL() == true && board.blockList.get(i + 1).getSideU() == true) && (i - blockInRowSize + 1 >= 0 && board.blockList.get(i - blockInRowSize + 1).getBlockType() != Level.B.BOOSTL)) {
                    //Träff på block uppe stolpe
                    if (moveX < 0) {
                        Log.d("tag", "ball ramp RAMPDR stolpe uppe");
                        moveX = moveX * -1;
                        speedX++;
                        collisionRamp = true;
                    }
                }
                if (bollAY == blockDY && bollBX > blockCX && bollAX < blockCX) {
                    //Träff på block vänstra stolpe
                    if (moveY < 0) {
                        Log.d("tag", "ball ramp RAMPDR stolpe vänstra");
                        moveY = moveY * -1;
                        collisionRamp = true;
                    }
                }
            }
        }
        return collisionRamp;
    }




    public boolean blockCheck(float x, float y, Level.B thisBlockType, float thisBlockX, float thisBlockY, int i){

        if(thisBlockType == Level.B.SOFT || thisBlockType == Level.B.SOLID || thisBlockType == Level.B.GOAL || thisBlockType == Level.B.SOFTSOLID) {

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
             * Träff på blocken 4 sidor uppe, nere, vänster eller höger.
             */
            if(bollAX == blockAX && bollAY == blockAY && thisBlockType == Level.B.GOAL){
                Log.d("tag", "ball mål2");
                moveX = 0;
                moveY = 0;

                game.wonGame(this);
                collisionBlock = true;


            }
            //else if (bollDX > blockAX && bollDY == blockAY   &&    bollCX < blockBX && bollCY == blockBY   &&   bollCY <= blockCY){
            else if (bollDX > blockAX && bollDY == blockAY   &&    bollCX < blockBX){
                //Träff på block ovanifrån
               if(moveY >= 0) {
                   if (board.blockList.get(i - blockInRowSize).getSideD() == true) {
                       Log.d("tag", "ball block uppefrån");
                       moveY = 0;

                       if (thisBlockType == Level.B.GOAL) {
                           if (bollAX == blockAX) {
                               Log.d("tag", "ball mål");
                               moveX = 0;
                               moveY = 20;
                           }
                       }
                   }

                   collisionBlock = true;
                }

            //}else if(bollBX > blockCX && bollBY == blockCY   &&    bollAX < blockDX && bollAY == blockDY   &&   bollAY >= blockAY) {
            }else if(bollBX > blockCX && bollBY == blockCY   && bollAX < blockDX) {
                //Träff på block underifrån
                if(moveY < 0) {
                    if (board.blockList.get(i + blockInRowSize).getSideU() == true
                            && board.blockList.get(i + blockInRowSize).getBlockType() != Level.B.BOOSTL
                            && board.blockList.get(i + blockInRowSize).getBlockType() != Level.B.BOOSTR
                            || ((bollBX >= blockCX && bollBX < blockCX + (blockSize / 2))
                                || ((bollAX <= blockDX && bollAX > blockDX - (blockSize / 2))
                                && ((board.blockList.get(i + blockInRowSize).getBlockType() == Level.B.BOOSTR)
                                || (board.blockList.get(i + blockInRowSize).getBlockType() == Level.B.BOOSTL))))) {

                        Log.d("tag", "ball block underifrån");
                        moveY = moveY * -1;
                        collisionBlock = true;

                        if(bollAX == blockCX && bollAY == blockCY
                                && (board.blockList.get(i + blockInRowSize - 1).getSideR() == false
                                    || board.blockList.get(i + blockInRowSize + 1).getSideL() == false)){
                            Log.d("tag", "ball block underifrån mitt i");
                            moveX = moveX * -1;
                        }
                    }
                }

            //}else if(bollDX == blockAX && bollDY >= blockAY   &&    bollBX == blockCX && bollBY < blockCY   &&   bollAX <= blockAX) {

            }else if(( bollDX == blockAX && bollDY >= blockAY  && bollBY < blockCY)) {
                //Träff på block från vänster
                if(moveX > 0) {
                    if (board.blockList.get(i - 1).getSideR() == true && board.blockList.get(i - 1).getBlockType() != Level.B.RAMPDR) {
                        if ((i - 1 - blockInRowSize >= 0 && board.blockList.get(i - 1 - blockInRowSize).getBlockType() != Level.B.BOOSTR) || (i - 1 - blockInRowSize >= 0 && board.blockList.get(i - 1 - blockInRowSize).getBlockType() == Level.B.BOOSTR && bollDY != blockAY) || (i - 1 - blockInRowSize <= 0)) {
                            Log.d("tag", "ball block vänster" + i);
                            moveX = moveX * -1;
                            speedX++;
                            collisionBlock = true;
                        }
                    }
                }

            //}else if(bollCX == blockBX && bollCY >= blockBY   &&    bollAX == blockDX && bollAY < blockDY   &&   bollBX >= blockBX){
            }else if((bollCX == blockBX && bollCY >= blockBY && bollAY < blockDY)){
                //Träff på block från höger
                if(moveX < 0) {
                    if (board.blockList.get(i + 1).getSideL() == true && board.blockList.get(i + 1).getBlockType() != Level.B.RAMPDL) {
                        if ((i + 1 - blockInRowSize >= 0 && board.blockList.get(i + 1 - blockInRowSize).getBlockType() != Level.B.BOOSTL) || (i + 1 - blockInRowSize >= 0 && board.blockList.get(i + 1 - blockInRowSize).getBlockType() == Level.B.BOOSTL && bollCY != blockBY) || (i + 1 - blockInRowSize <= 0)) {
                            Log.d("tag", "ball block höger" + i);
                            moveX = moveX * -1;
                            speedX++;
                            collisionBlock = true;
                        }
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
                speedX++;
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
        if (y <= 0 + boundariesHeightDP) {
            if(moveY < 0) {
                Log.d("tag", "ball boundaries uppe");
                moveY = moveY * -1;
                collisionBoundaries = true;
            }
        }
        //Träff av boundaries nere
        if (y + blockSize >= screenWidth + boundariesHeightDP) {
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

