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
    public Thread threadMove;
    public boolean running = true;


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
        ball.setBall(screenWidth, blockNumberInRow, board, this);

        gridLayoutGame.addView(board);
        relativeLayoutGame.addView(ball);

        threads();

    }



    public void threads() {


        /*new Thread(new Runnable() {
            public void run() {


                while (running) {

                    try {
                        float sleepSpeedX = ball.getSpeedX();
                        Thread.sleep((long) sleepSpeedX);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("game", "tråd  test test test test test v test");
                    ball.>////////ballMoveX();
                }
            }
        }).start();*/



        threadMove = new Thread() {
            public void run() {
            while (running) {

                // colletion
                ball.ballMove();
                //Log.d("tag", "game tråd status" + threadMove.getState());
                try {
                    Thread.sleep(10);

                } catch (InterruptedException e) {
                    Log.d("game", "tråd X");
                }
                // move
            }
            }
        };
    }



    public void playBall(View view) {
        Log.d("tag", "game startBall");
        running = true;
        threadMove.start();

    }

    public void restartBall(View view) {
        Log.d("tag", "game restartBall");
        running = false;
        ball.restartBall();
    }

    public void stopBall(View view) {
        Log.d("tag", "game stopBall");
        running = false;
    }
}

