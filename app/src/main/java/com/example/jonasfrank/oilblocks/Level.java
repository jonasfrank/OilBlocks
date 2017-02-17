package com.example.jonasfrank.oilblocks;

import java.util.ArrayList;

/**
 * Created by jonasfrank on 2017-01-18.
 */

public class Level {





    public enum B {
        EMPTY,
        EMPTYDISABLE,
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
                    {B.EMPTY,B.EMPTY,B.START,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.GOAL,B.EMPTY,B.EMPTY,B.EMPTY},
            },
            {       //2
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.START},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.SOLID,B.SOLID,B.SOFT,B.SOLID,B.SOLID,B.SOLID,B.SOLID,B.SOLID},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.GOAL,B.SOFT,B.SOFT},
            },
            {       //3
                    {B.EMPTY,B.START,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.SOLID,B.SOLID,B.SOLID,B.SOLID,B.SOLID,B.SOLID,B.SOFT,B.SOLID},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.SOFT,B.EMPTY,B.EMPTY,B.SOLID},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.SOFT,B.SOLID,B.SOLID,B.SOLID},
                    {B.SOLID,B.EMPTY,B.EMPTY,B.SOFT,B.SOFT,B.SOLID,B.EMPTY,B.EMPTY},
                    {B.SOLID,B.GOAL,B.EMPTY,B.SOLID,B.SOLID,B.SOLID,B.EMPTY,B.EMPTY},
                    {B.SOLID,B.SOLID,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
            },
            {       //4
                    {B.SOLID,B.SOLID,B.SOLID,B.SOLID,B.SOLID,B.SOLID,B.SOLID,B.SOLID},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.SOFT,B.SOFT},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.SOFT,B.SOFT},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.SOFT,B.GOAL},
                    {B.START,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.SOFT,B.SOLID},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.SOLID,B.SOLID},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.SOLID,B.SOLID,B.SOLID,B.SOLID},
                    {B.SOLID,B.SOLID,B.SOLID,B.SOLID,B.SOLID,B.SOLID,B.SOLID,B.SOLID},
            },
            {       //5
                    {B.START,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.GOAL,B.SOFT,B.SOFT,B.SOFT},
            },
            {       //6
                    {B.START,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.GOAL,B.SOFT,B.SOFT,B.SOFT},
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
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.START},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.GOAL,B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.SOLID},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
            },
            {       //10
                    {B.EMPTY,B.SOFT,B.SOFT,B.START,B.EMPTY,B.SOFT,B.SOLID,B.EMPTY},
                    {B.EMPTY,B.SOLID,B.SOFT,B.EMPTY,B.SOLID,B.SOFT,B.EMPTY,B.EMPTY},
                    {B.SOFT,B.SOFT,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.EMPTY,B.SOFT},
                    {B.EMPTY,B.SOFT,B.SOFT,B.EMPTY,B.SOLID,B.GOAL,B.SOLID,B.SOFT},
                    {B.SOLID,B.EMPTY,B.EMPTY,B.SOLID,B.EMPTY,B.SOFT,B.EMPTY,B.SOFT},
                    {B.SOFT,B.SOFT,B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.SOLID,B.SOLID},
                    {B.SOFT,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOLID,B.SOFT,B.SOFT},
                    {B.EMPTY,B.EMPTY,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOLID,B.SOLID},
            }
    };

    B[][] gameUtility ={

            //SOFT,BOOSTR,BOOSTL,RAMPUL,RAMPUR,RAMPDL,RAMPDR

            /*1*/{B.SOFT,B.BOOSTR},
            /*2*/{B.SOFT,B.BOOSTR,B.RAMPUL},
            /*3*/{B.SOFT,B.BOOSTR,B.BOOSTL,B.RAMPUL},
            /*4*/{B.SOFT,B.BOOSTR,B.BOOSTR,B.BOOSTR,B.BOOSTR,B.RAMPUL,B.RAMPUL,B.RAMPUL},
            /*5*/{B.SOFT,B.BOOSTR,B.BOOSTR},
            /*6*/{B.SOFT,B.BOOSTR,B.BOOSTR},
            /*7*/{B.SOFT,B.BOOSTR,B.BOOSTL,B.RAMPUL,B.RAMPDR},
            /*8*/{B.SOFT,B.BOOSTR,B.BOOSTR,B.BOOSTL,B.BOOSTL,B.RAMPUL,B.RAMPUL,B.RAMPUL,B.RAMPUR,B.RAMPUR,B.RAMPUR,B.RAMPUR,B.RAMPDL,B.RAMPDL,B.RAMPDL,B.RAMPDR,B.RAMPDR,B.RAMPDR,B.RAMPDR},
            /*9*/{B.SOFT,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.RAMPUL,B.RAMPUR,B.RAMPUR,B.RAMPDR},
            /*10*/{B.SOFT,B.BOOSTR,B.BOOSTL,B.RAMPUL,B.RAMPUL,B.RAMPUR,B.RAMPDL,B.RAMPDL,B.RAMPDR}
    };

    int [][] softCount ={
            /*1*/{5,10},
            /*2*/{5,15},
            /*3*/{0,5},
            /*4*/{5,15},
            /*5*/{0,1},
            /*6*/{0,1},
            /*7*/{0,14},
            /*8*/{0,5},
            /*9*/{1,1},
            /*10*/{5,15}
    };

}
