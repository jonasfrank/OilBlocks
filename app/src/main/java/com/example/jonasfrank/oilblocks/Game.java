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

    public final static String EXTRA_MESSAGE = "com.example.jonasfrank.oilblocks.MESSAGE";
    public int levelNumber;
    public int screenWidth;
    public int blockNumberInRow;
    public int blockSize;
    public Board board;
    public Level level;
    public Ball ball;
    public Thread threadMove;
    public boolean running = false;
    public boolean gameIsWon = false;

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
        Intent intent = new Intent(Game.this, LevelSelect.class);
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
        board.setBoard(this, screenWidth, levelNumber, ball, level);       //skickar screen bredden till bord
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
        gameIsWon = false;
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

    public void restartBoard(View view) {
        Intent intent = new Intent(Game.this, Game.class);
        intent.putExtra(EXTRA_MESSAGE, String.valueOf(levelNumber));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

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

                    menyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //LevelSelect
                            Intent intent = new Intent(Game.this, LevelSelect.class);
                            startActivity(intent);
                        }
                    });

                    reversButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Game.this, Game.class);
                            intent.putExtra(EXTRA_MESSAGE, String.valueOf(levelNumber));
                            startActivity(intent);

                        }
                    });


                    builder.setView(diaView);
                    AlertDialog dialog = builder.create();
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

                    menyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //LevelSelect
                            Intent intent = new Intent(Game.this, LevelSelect.class);
                            startActivity(intent);
                        }
                    });

                    reversButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Game.this, Game.class);
                            intent.putExtra(EXTRA_MESSAGE, String.valueOf(levelNumber));
                            startActivity(intent);

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


                    builder.setView(diaView);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });


    }

    public boolean getRunning() {
        return running;
    }

    public void lostGame(View view) {
        if (gameIsWon == false) {
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

