package com.example.parrot.pong1;

public class Ball {
    public int ballX;
    public int ballY;
    public int BALL_WIDTH;

    public Ball(int ballX, int ballY, int BALL_WIDTH) {
        this.ballX = ballX;
        this.ballY = ballY;
        this.BALL_WIDTH = BALL_WIDTH;
    }

    public int getBallX() {
        return ballX;
    }

    public void setBallX(int ballX) {
        this.ballX = ballX;
    }

    public int getBallY() {
        return ballY;
    }

    public void setBallY(int ballY) {
        this.ballY = ballY;
    }

    public int getBALL_WIDTH() {
        return BALL_WIDTH;
    }

    public void setBALL_WIDTH(int BALL_WIDTH) {
        this.BALL_WIDTH = BALL_WIDTH;
    }
}
