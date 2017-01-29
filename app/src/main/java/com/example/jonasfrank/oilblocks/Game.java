package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Game extends AppCompatActivity {

    public int levelNumber;
    public int screenWidth;
    public int blockNumberInRow;
    public int blockSize;
    public Board board;
    public Ball ball;
    public BallLoop ballLoop;
    public ballThread ballRun;
    private boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;

        Intent intent = getIntent();
        String temp = intent.getStringExtra(LevelSelect.EXTRA_MESSAGE);
        levelNumber = Integer.parseInt(temp);

        drawBoard();
    }

    public void drawBoard() {
        GridLayout gridLayoutGame = (GridLayout) findViewById(R.id.game_game);
        RelativeLayout relativeLayoutGame = (RelativeLayout) findViewById(R.id.game_ball_layout);

        board = new Board(this);          //skapar bordet
        blockNumberInRow = board.blockNumberInRow;
        blockSize = screenWidth / blockNumberInRow;
        ball = new Ball(this);          //skapar bollen
        ballRun = new ballThread(143);

        board.setBoard(this, screenWidth, levelNumber, ball);       //skickar screen bredden till bord
        ball.setBall(screenWidth, blockNumberInRow, board);

        gridLayoutGame.addView(board);

        /*TranslateAnimation anim = new TranslateAnimation( 0, 400 , 00, 400);
        anim.setDuration(1000);
        anim.setFillAfter( true );
        relativeLayoutGame.startAnimation(anim);*/

        relativeLayoutGame.addView(ball);
    }


    public void playBall(View view){
        Log.d("tag", "game startBall");
        ballRun.start();
        running = true;
    }
    public void restartBall(View view){
        Log.d("tag", "game restartBall");
        running = false;
        ball.restartBall();
    }

    public void stopBall(View view){
       Log.d("tag", "game stopBall");
       running = false;
    }

    class ballThread extends Thread {
        long minPrime;
        ballThread(long minPrime) {
            this.minPrime = minPrime;
        }

        public void run() {
            while (running) {
                //ball.moveY = ball.moveY * (float)1.1;


                ball.ballMove();
                try {
                    Thread.sleep(20);

                } catch (InterruptedException e){
                }
            }
        }
    }




}
