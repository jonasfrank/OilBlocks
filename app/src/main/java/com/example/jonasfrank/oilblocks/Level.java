package com.example.jonasfrank.oilblocks;

import java.util.ArrayList;

/**
 * Created by jonasfrank on 2017-01-18.
 */

public class Level {


    public enum B {
        EMPTY,
        SOFT,
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

    B[][][] gameLavel = {
            {
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.RAMPDR,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.RAMPDR,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.BOOSTR,B.EMPTY,B.GOAL},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.START,B.EMPTY,B.EMPTY},
                    {B.SOFT,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.EMPTY,B.SOFT,B.SOFT},
                    {B.SOFT,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.SOFT},
                    {B.SOFT,B.BOOSTR,B.EMPTY,B.EMPTY,B.EMPTY,B.RAMPUL,B.SOFT,B.SOFT},
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
                    {B.SOLID,B.SOLID,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT},
                    {B.SOLID,B.SOLID,B.SOLID,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT},
                    {B.SOLID,B.EMPTY,B.EMPTY,B.RAMPUR,B.RAMPUR,B.EMPTY,B.RAMPUL,B.SOFT},
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
                    {B.EMPTY,B.EMPTY,B.START,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.BOOSTL,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.SOFT,B.EMPTY,B.EMPTY},
                    {B.EMPTY,B.EMPTY,B.RAMPDL,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
                    {B.GOAL,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY,B.EMPTY},
            }
    };

}
