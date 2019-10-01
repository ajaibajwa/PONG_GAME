package com.example.parrot.pong1;

import android.content.Context;

public class GameUtils {

    GameEngine gameEngine;
    Context context;

    public boolean isScoreZero(int score){

        gameEngine = new GameEngine(this.context);
        int initialScore = gameEngine.score;
        if (score == initialScore){
            return true;
        }
        return false;
    }

    public boolean isLivesReaminingThree(int lives){
        gameEngine = new GameEngine(this.context);
        int initialLives = gameEngine.lives;
        if (lives == initialLives){
            return true;
        }
        return false;
    }

    public boolean 
}
