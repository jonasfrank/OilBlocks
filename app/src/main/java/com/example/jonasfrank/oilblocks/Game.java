package com.example.jonasfrank.oilblocks;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.centerX;
import static android.R.attr.gravity;
import static android.R.attr.level;
import static android.R.attr.onClick;

public class Game extends AppCompatActivity {

    final static String EXTRA_MESSAGE = "com.example.jonasfrank.oilblocks.MESSAGE";
    int levelNumber;
    int screenWidth;
    int blockNumberInRow;
    int blockSize;

    Board board;
    Level level;
    Ball ball;
    Thread threadMove;

    boolean gameInProgress = false;
    boolean running = false;
    boolean gameIsWon = false;

    AlertDialog dialog;

    private static final int SPLASH_DURATION = 750;

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

    @Override
    public void onBackPressed() {
        running = false;
        String temp = Integer.toString(levelNumber);
        Intent intent = new Intent(Game.this, LevelSelect.class);
        intent.putExtra(EXTRA_MESSAGE, temp);
        startActivity(intent);
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
        level = new Level();
        //LevelSelect.getLevel
        board.setBoard(this, screenWidth, levelNumber, ball, level);       //skickar screen bredden till bord
        ball.setBall(screenWidth, blockNumberInRow, board, this);

        gridLayoutGame.addView(board);
        relativeLayoutGame.addView(ball);

        threads();

    }

    public void threads() {

        threadMove = new Thread() {
            public void run() {
                while (running) {

                    ball.ballMove();
                    //Log.d("tag", "game tråd status" + threadMove.getState());
                    try {
                        Thread.sleep(10);

                    } catch (InterruptedException e) {
                        Log.d("game", "tråd X");
                    }
                }
            }
        };
    }

    public void playBall(View view) {
        Log.d("tag", "game startBall");
        if(gameInProgress == false) {
            running = true;
            gameInProgress = true;
            gameIsWon = false;


            if (threadMove.getState() != Thread.State.TERMINATED) {
                threadMove.start();
            } else {
                threads();
                threadMove.start();
            }
        }

    }

    public void restartBall(View view) {
        Log.d("tag", "game restartBall");
        running = false;
        gameInProgress = false;
        ball.restartBall();
    }

    public void stopBall(View view) {
        Log.d("tag", "game stopBall");
        running = false;
    }

    public void restartBoard(View view) {
        if(gameInProgress == false){
            Intent intent = new Intent(Game.this, Game.class);
            intent.putExtra(EXTRA_MESSAGE, String.valueOf(levelNumber));
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }

    }

    public void wonGame(View view) {
        running = false;
        gameIsWon = true;


        if (MainActivity.clearedStages == levelNumber && MainActivity.clearedStages < level.gameLevel.length) {
            MainActivity.clearedStages++;

            SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
            editor.putInt(MainActivity.CLEARED_STAGES_KEY, MainActivity.clearedStages);
            editor.commit();
        } else if (MainActivity.clearedStages == level.gameLevel.length) {

            //Lägg till annan overlay
            Log.d("YOU", "WON");
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            if(levelNumber == level.gameLevel.length){
                AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
                builder.setCancelable(false);
                View diaView = getLayoutInflater().inflate(R.layout.won_game_dialog_last_stage, null);
                ImageButton menyButton = (ImageButton) diaView.findViewById(R.id.menyButton);
                ImageButton reversButton = (ImageButton) diaView.findViewById(R.id.reversButton);

                builder.setView(diaView);
                dialog = builder.create();

                menyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //LevelSelect
                        String temp = Integer.toString(levelNumber);
                        Intent intent = new Intent(Game.this, LevelSelect.class);
                        intent.putExtra(EXTRA_MESSAGE, temp);
                        startActivity(intent);
                    }
                });

                reversButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Tillbaka till spelet
                        /*Intent intent = new Intent(Game.this, Game.class);
                        intent.putExtra(EXTRA_MESSAGE, String.valueOf(levelNumber));
                        startActivity(intent);*/
                        dialog.cancel();
                        restartBall(null);
                    }
                });

                dialog.show();

            }else {
                Log.d("game", "won");
                AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
                builder.setCancelable(false);
                View diaView = getLayoutInflater().inflate(R.layout.won_game_dialog, null);
                TextView textView = (TextView) diaView.findViewById(R.id.wonLevelNum);
                textView.setText(Integer.toString(levelNumber));
                ImageButton menyButton = (ImageButton) diaView.findViewById(R.id.menyButton);
                ImageButton reversButton = (ImageButton) diaView.findViewById(R.id.reversButton);
                ImageButton nextButton = (ImageButton) diaView.findViewById(R.id.nextButton);

                builder.setView(diaView);
                dialog = builder.create();

                menyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //LevelSelect
                    String temp = Integer.toString(levelNumber);
                    Intent intent = new Intent(Game.this, LevelSelect.class);
                    intent.putExtra(EXTRA_MESSAGE, temp);
                    startActivity(intent);
                    }
                });

                reversButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Tillbaka till spelet
                        /*Intent intent = new Intent(Game.this, Game.class);
                        intent.putExtra(EXTRA_MESSAGE, String.valueOf(levelNumber));
                        startActivity(intent);*/
                        dialog.cancel();
                        restartBall(null);
                    }
                });

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //Next
                    if (levelNumber < MainActivity.clearedStages) {
                        Intent intent = new Intent(Game.this, Game.class);
                        intent.putExtra(EXTRA_MESSAGE, String.valueOf(levelNumber + 1));
                        startActivity(intent);
                    }
                    }
                });

                dialog.show();
            }
            }
        });
    }

    public boolean getRunning() {
        return running;
    }
    public boolean getgameInProgress() {
        return gameInProgress;
    }

    public void lostGame(View view) {
        if (gameIsWon == false) {
            gameInProgress = false;
            running = false;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                Context context = getApplicationContext();
                CharSequence text = "Game over!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();

                Handler handler = new Handler();

                // run a thread after 2 seconds to start the home screen
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        ball.restartBall();
                    }
                }, SPLASH_DURATION);
                }
            });
        }
    }
}

