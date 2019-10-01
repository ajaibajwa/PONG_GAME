package com.example.parrot.pong1;

import android.content.Context;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GameUtilsTest{

//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//    }

    @Test
    public void scoreIsZeroTest(){
        GameUtils gameUtils = new GameUtils();
        assertTrue(gameUtils.isScoreZero(0));
    }
    @Test
    public void livesAreThreeTest(){
        GameUtils gameUtils = new GameUtils();
        assertTrue(gameUtils.isLivesReaminingThree(3));
    }
}