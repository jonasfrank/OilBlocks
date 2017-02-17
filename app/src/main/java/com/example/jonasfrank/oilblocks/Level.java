package com.example.jonasfrank.oilblocks;

import java.util.ArrayList;

/**
 * Created by jonasfrank on 2017-01-18.
 */

public class Level {





    public enum B {
        EMPTY,
        SOFT,
        SOFTUTIL,
        SOLID,
        START,
        GOAL,
        BOOSTR,
        BOOSTL,
        RAMPUL,
        RAMPUR,
        RAMPDL,
        RAMPDR;

    }

    B[][][] gameLevel = {
            {       //1
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.RAMPDR,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.RAMPDR,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.BOOSTR,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.START,B.EMPTY,B.EMPTY},
                    {B.SOFT,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.EMPTY,B.SOFT,B.SOFT},
                    {B.SOFT,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.SOFT},
                    {B.SOFT,B.BOOSTR,B.EMPTY,B.EMPTY,B.EMPTY,B.GOAL,B.SOFT,B.SOFT},
                    {B.SOFT,B.BOOSTR,B.RAMPUL,B.RAMPUL,B.RAMPUL,B.RAMPUL,B.SOFT,B.SOFT},
            },
            {       //2
                    {B.SOFT,B.RAMPDR,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.RAMPDL,B.EMPTY},
                    {B.EMPTY,B.START,B.RAMPDR,B.EMPTY,B.EMPTY,B.RAMPDL,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.RAMPDR,B.RAMPDL,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.BOOSTR,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.RAMPUR,B.EMPTY,B.RAMPUL,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.RAMPUR,B.EMPTY,B.EMPTY,B.EMPTY,B.RAMPUL,B.EMPTY,B.EMPTY},
                    {B.RAMPUR,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.RAMPUL,B.GOAL},
            },
            {       //3
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.SOFT,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.START,B.EMPTY},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.RAMPDR,B.EMPTY,B.EMPTY,B.RAMPDL},
                    {B.SOLID,B.SOLID,B.SOLID,B.RAMPDR,B.RAMPDL,B.EMPTY,B.EMPTY,B.SOFT},
                    {B.SOLID,B.SOLID,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT},
                    {B.SOLID,B.EMPTY,B.EMPTY,B.RAMPUL,B.RAMPUR,B.EMPTY,B.RAMPUL,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.EMPTY,B.SOFT},
                    {B.GOAL,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
            },
            {       //4
                    {B.EMPTY,B.EMPTY,B.START,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.BOOSTR,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.BOOSTR,B.BOOSTR,B.BOOSTR,B.BOOSTR,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.GOAL,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
            },
            {       //5
                    {B.BOOSTL,B.EMPTY,B.EMPTY,B.START,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.GOAL,B.EMPTY,B.EMPTY,B.RAMPUL,B.EMPTY},
            },
            {       //6
                    {B.START,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.RAMPDR,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.RAMPDR,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.BOOSTR,B.EMPTY,B.EMPTY},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.GOAL},
            },
            {       //7
                    {B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.START,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.SOFT,B.SOFT,B.SOLID,B.SOLID,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOLID,B.SOLID,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.GOAL},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
            },
            {       //8
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.SOLID,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.GOAL,B.SOLID,B.START,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
            },
            {       //9
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
            },
            {       //10
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
            }
    };

    B[][] gameUtility ={

            //SOFT,BOOSTR,BOOSTL,RAMPUL,RAMPUR,RAMPDL,RAMPDR

            /*1*/{B.SOFT,B.BOOSTR,B.BOOSTR,B.BOOSTL,B.RAMPUR,B.RAMPUL,B.RAMPDL,B.RAMPDR,B.RAMPDL,B.BOOSTR},
            /*2*/{B.SOFT,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL},
            /*3*/{B.SOFT,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL},
            /*4*/{B.SOFT,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL},
            /*5*/{B.SOFT,B.BOOSTL,B.BOOSTR,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL},
            /*6*/{B.SOFT,B.BOOSTR,B.BOOSTR,B.BOOSTR,B.BOOSTR,B.BOOSTR},
            /*7*/{B.SOFT,B.BOOSTR,B.BOOSTL,B.RAMPUL,B.RAMPDR},
            /*8*/{B.SOFT,B.BOOSTR,B.BOOSTR,B.BOOSTL,B.BOOSTL,B.RAMPUL,B.RAMPUL,B.RAMPUL,B.RAMPUR,B.RAMPUR,B.RAMPUR,B.RAMPUR,B.RAMPDL,B.RAMPDL,B.RAMPDL,B.RAMPDR,B.RAMPDR,B.RAMPDR,B.RAMPDR},
            /*9*/{B.SOFT,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL},
            /*10*/{B.SOFT,B.BOOSTL,B.BOOSTR,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL}
    };

    int [][] softCount ={
            /*1*/{8,10},
            /*2*/{5,20},
            /*3*/{0,10},
            /*4*/{5,5},
            /*5*/{5,15},
            /*6*/{0,1},
            /*7*/{0,14},
            /*8*/{0,5},
            /*9*/{5,5},
            /*10*/{5,15}
    };

}
