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
            {
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.RAMPDR,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.RAMPDR,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.BOOSTR,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.START,B.EMPTY,B.EMPTY},
                    {B.SOFT,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.EMPTY,B.SOFT,B.SOFT},
                    {B.SOFT,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.SOFT},
                    {B.SOFT,B.BOOSTR,B.EMPTY,B.EMPTY,B.EMPTY,B.GOAL,B.SOFT,B.SOFT},
                    {B.SOFT,B.BOOSTR,B.RAMPUL,B.RAMPUL,B.RAMPUL,B.RAMPUL,B.SOFT,B.SOFT},
            },
            {
                    {B.SOFT,B.RAMPDR,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.RAMPDL,B.EMPTY},
                    {B.EMPTY,B.START,B.RAMPDR,B.EMPTY,B.EMPTY,B.RAMPDL,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.RAMPDR,B.RAMPDL,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.BOOSTR,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.RAMPUR,B.EMPTY,B.RAMPUL,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.RAMPUR,B.EMPTY,B.EMPTY,B.EMPTY,B.RAMPUL,B.EMPTY,B.EMPTY},
                    {B.RAMPUR,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.RAMPUL,B.GOAL},
            },
            {
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.SOFT,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.START,B.EMPTY},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.RAMPDR,B.EMPTY,B.EMPTY,B.RAMPDL},
                    {B.SOLID,B.SOLID,B.SOLID,B.RAMPDR,B.RAMPDL,B.EMPTY,B.EMPTY,B.SOFT},
                    {B.SOLID,B.SOLID,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT},
                    {B.SOLID,B.EMPTY,B.EMPTY,B.RAMPUL,B.RAMPUR,B.EMPTY,B.RAMPUL,B.SOFT},
                    {B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.EMPTY,B.SOFT},
                    {B.GOAL,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT,B.SOFT},
            },
            {
                    {B.EMPTY,B.EMPTY,B.START,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.BOOSTR,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.BOOSTR,B.BOOSTR,B.BOOSTR,B.BOOSTR,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.GOAL,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
            },
            {
                    {B.BOOSTL,B.EMPTY,B.EMPTY,B.START,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.GOAL,B.EMPTY,B.EMPTY,B.RAMPUL,B.EMPTY},
            }
    };

    B[][] gameUtility ={
            {B.SOFT,B.BOOSTR,B.BOOSTR,B.BOOSTL,B.RAMPUR,B.RAMPUL,B.RAMPDL,B.RAMPDR,B.RAMPDL,B.BOOSTR},
            {B.SOFT,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL},
            {B.SOFT,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL},
            {B.SOFT,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL},
            {B.SOFT,B.BOOSTL,B.BOOSTR,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL,B.BOOSTL}
    };

    int [][] softCount ={
            {8,10},
            {5,20},
            {0,10},
            {5,5},
            {5,15}
    };

}
