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
import android.widget.TextView;

public class Game extends AppCompatActivity {

    public int levelNumber;
    public int screenWidth;
    public int blockNumberInRow;
    public int blockSize;
    public Board board;
    public Ball ball;
    public BallLoop ballLoop;
    public Thread threadX;
    public Thread threadY;
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

        TextView levelTextViewGame = (TextView) findViewById(R.id.game_level);
        levelTextViewGame.setText("Level " + levelNumber);

        board = new Board(this);          //skapar bordet
        blockNumberInRow = board.blockNumberInRow;
        blockSize = screenWidth / blockNumberInRow;
        ball = new Ball(this);          //skapar bollen

        board.setBoard(this, screenWidth, levelNumber, ball);       //skickar screen bredden till bord
        ball.setBall(screenWidth, blockNumberInRow, board);

        gridLayoutGame.addView(board);
        relativeLayoutGame.addView(ball);

        threads();
    }

    public void playBall(View view){
        Log.d("tag", "game startBall");
        threadX.start();
        threadY.start();
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

    public void threads() {

        Log.d("Threads", "Test");
        threadX = new Thread() {
            public void run() {
            while (running) {

<<<<<<< Updated upstream
                ball.ballMoveX();
                try {
                    Log.d("X", "Running");
                    float sleedSpeedX = ball.getSpeedX();
                    Log.d("X", "Sleepspeed:" + sleedSpeedX);
                    Thread.sleep((long) sleedSpeedX);
=======
                    ball.ballMoveX();
                    try {
                        //Log.d("X", "Running");
                        float sleedSpeedX = ball.getSpeedX();
                        //Log.d("X", "Sleepspeed:" + sleedSpeedX);
                        Thread.sleep((long) sleedSpeedX);
>>>>>>> Stashed changes

                } catch (InterruptedException e) {
                }
            }
            }
        };

        threadY = new Thread() {
            public void run() {
<<<<<<< Updated upstream
=======
                while (running) {
                    //ball.moveY = ball.moveY * (float)1.1;


                    ball.ballMoveY();
                    try {
                        //Log.d("Y", "Running");
                        float sleedSpeedY = ball.getSpeedY();
                        //Log.d("Y", "Sleepspeed:" + sleedSpeedY);
                        Thread.sleep((long) sleedSpeedY);

                    } catch (InterruptedException e) {
                    }
                }
            }
        };
        //}
    }
    /*class ballThreadY extends Thread {
        long minPrime;
        ballThreadY(long minPrime) {
            this.minPrime = minPrime;
        }

        public void run() {
>>>>>>> Stashed changes
            while (running) {

                ball.ballMoveY();
                try {
                    Log.d("Y", "Running");
                    float sleedSpeedY = ball.getSpeedY();
                    Log.d("Y", "Sleepspeed:" + sleedSpeedY);
                    Thread.sleep((long) sleedSpeedY);

                } catch (InterruptedException e) {
                }
            }
            }
        };
    }
}
