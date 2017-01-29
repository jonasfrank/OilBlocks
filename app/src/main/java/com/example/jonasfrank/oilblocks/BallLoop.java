package com.example.jonasfrank.oilblocks;

import android.content.Context;
import android.util.Log;

/**
 * Created by User on 2017-01-22.
 */

public class BallLoop extends Thread {

    private boolean running = true;

    public BallLoop(){
        super();

    }

    long minPrime;
    BallLoop(long minPrime) {
        this.minPrime = minPrime;
    }

    public void run() {

        while (running) {
            try {
                Thread.sleep(500);
                Log.d("tag", "game run");
            } catch (InterruptedException e){
            }
        }
    }
}


