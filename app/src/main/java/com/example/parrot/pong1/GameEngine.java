package com.example.parrot.pong1;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

import static android.content.Intent.getIntent;
import static android.support.v4.content.ContextCompat.startActivity;

public class GameEngine extends SurfaceView implements Runnable {

    // -----------------------------------
    // ## ANDROID DEBUG VARIABLES
    // -----------------------------------

    // Android debug variables
    final static String TAG="PONG-GAME";

    // -----------------------------------
    // ## SCREEN & DRAWING SETUP VARIABLES
    // -----------------------------------

    int score = 0;
    int threadSleep = 15;

    int lives = 3;
    // screen size
    int screenHeight;
    int screenWidth;

    int easyModeX = this.screenWidth;
    int easyModeY = 50;

    int hardModeX = this.screenWidth;
    int hardModeY = 140;
    String modeTapped= "";

    int ballXPosition;
    int ballYPosition;

    int racketXPosition;
    int racketYPosition;
    int racketLength = 400;
    boolean ballMissed = false;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;


    Random rnd;
    int color = Color.RED;

    // -----------------------------------
    // ## GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------

    // ----------------------------
    // ## GAME STATS - number of lives, score, etc
    // ----------------------------

    public GameEngine(Context context){
        super(context);

    }

    public GameEngine(Context context, int w, int h) {
        super(context);


        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;

        this.ballXPosition = this.screenWidth/2;
        //this.ballXPosition = (this.screenWidth)*20/100; //ball at 20% from left of screen
        //this.ballYposition = (this.screenHeight)-(this.screenHeight/2);
        this.ballYPosition = this.screenHeight/2; //ball at 30% from top of screen

        this.racketXPosition = 350;
        this.racketYPosition = 1200;

        rnd = new Random();

        this.printScreenInfo();

        // @TODO: Add your sprites to this section
        // This is optional. Use it to:
        //  - setup or configure your sprites
        //  - set the initial position of your sprites


        // @TODO: Any other game setup stuff goes here


    }

    // ------------------------------
    // HELPER FUNCTIONS
    // ------------------------------

    // This funciton prints the screen height & width to the screen.
    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }


    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }
    public void quitGame(){
        gameIsRunning = false;
        gameThread.interrupt();
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    //String directionBallIsMoving = "right";
    String directionBallIsMoving = "down";
    String personTapped = "";
    // 1. Tell Android the (x,y) positions of your sprites
    public void updatePositions() {
        // @TODO: Update the position of the sprites



        Log.d("TAG","HELLO");
        //1. calculate new postion for the ball
//        while (ballXpostion<=screenWidth){
//            ballXpostion = ballXpostion + 10;
//            if (ballXpostion>=screenWidth){
//                ballXpostion = ballXpostion - 10;
//            }
//        }

        if (directionBallIsMoving.contentEquals("down")) {
            this.ballYPosition = this.ballYPosition + 10;
            if (modeTapped=="easy"){
                this.ballYPosition = this.ballYPosition + 5;
            }
            else if(modeTapped=="hard"){

                this.ballYPosition = this.ballYPosition + 25;
            }

            if (this.ballYPosition >= this.screenHeight) {
                directionBallIsMoving = "up";
                this.ballXPosition = this.screenWidth/2;
                this.ballYPosition = this.screenHeight/2-100;

                this.racketXPosition = 350;
                this.racketYPosition = 1200;
                directionBallIsMoving = "down";
                personTapped = "";
                this.lives = lives-1;
                }
        }

        else if (directionBallIsMoving.contentEquals("up")) {
            //pauseGame();
            this.ballYPosition = this.ballYPosition - 10;
            if (modeTapped=="easy"){
                this.ballYPosition = this.ballYPosition - 5;
            }
            else if(modeTapped=="hard"){

                this.ballYPosition = this.ballYPosition - 25;
            }

            if (this.ballYPosition<=0){
                directionBallIsMoving = "down";
            }
        }

        if (personTapped.contentEquals("right")){
            if (racketXPosition<this.screenWidth-this.racketLength) {
                this.racketXPosition = this.racketXPosition + 10;
            }
        }
        else if(personTapped.contentEquals("left")){
            if(racketXPosition>0)
            this.racketXPosition = this.racketXPosition -10;
        }

        if ((this.ballYPosition >= this.racketYPosition-50)&&(this.ballXPosition >= this.racketXPosition) && (this.ballXPosition <= this.racketXPosition + this.racketLength)){

                directionBallIsMoving = "up";
                this.score = this.score + 50;
                ballMissed = false;
                color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));


        }
        if (lives==0){
            //this.pauseGame();
            //this.startGame();
            this.quitGame();
        }



        // DEBUG - by outputing current positiong
        Log.d(TAG, "XPos: " + this.ballXPosition);
//        if (ballXpostion<screenWidth){
//            ballXpostion = ballXpostion+10;
//        }
//        else {
//            ballXpostion = ballXpostion -10;
//        }

        // @TODO: Collision detection code

    }

    // 2. Tell Android to DRAW the sprites at their positions
    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------
            // Put all your drawing code in this section

            // configure the drawing tools
            //this.canvas.drawColor(Color.argb(255,0,0,255));
            this.canvas.drawColor(color);
            paintbrush.setColor(Color.WHITE);


            //@TODO: Draw the sprites (rectangle, circle, etc)
            //draw the ball
            //this.canvas.drawRect(this.screenWidth/2,this.screenHeight/2,(this.screenWidth/2)+50,(this.screenHeight/2)+50,paintbrush);
            //this.canvas.drawRect(ballXpostion,ballYposition,ballXpostion+50,ballYposition+50,paintbrush);
            //draw the circle
            this.canvas.drawCircle(ballXPosition,ballYPosition,25,paintbrush);
            //@TODO: Draw game statistics (lives, score, etc)
            paintbrush.setTextSize(60);
            canvas.drawText("Score: "+this.score, 20, 100, paintbrush);
            canvas.drawText("Lives Remaining: "+this.lives,20,180,paintbrush);
            canvas.drawText("EASY",700,this.easyModeY,paintbrush);
            canvas.drawText("HARD",700,this.hardModeY,paintbrush);
            paintbrush.setColor(Color.YELLOW);
            canvas.drawRect(this.racketXPosition,this.racketYPosition,this.racketXPosition+this.racketLength,this.racketYPosition+50,paintbrush);
            paintbrush.setColor(Color.WHITE);
            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    // Sets the frame rate of the game
    public void setFPS() {
        try {
            gameThread.sleep(10);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Get position of the tap
        // Compare position of tap to the middle of the screen
        // If tap is on left, move racket Position to left
        // If tap is on right, move racket position to right
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {
            // user pushed down on screen

            float fingerXPosition = event.getX();
            float fingerYPosition = event.getY();

            Log.d(TAG, "Person's pressed: " + fingerXPosition + ","+ fingerYPosition);

            //Compare the position to middle
            int middleOfScreen = this.screenWidth/2;


            if ((fingerXPosition<=middleOfScreen)&&(fingerYPosition>200)){
                personTapped = "left";
            }
            else if((fingerXPosition>middleOfScreen)&&(fingerYPosition>200)) {
                personTapped = "right";
            }
            if ((fingerXPosition>=700)&&(fingerXPosition<900)&&(fingerYPosition>=easyModeY)&&(fingerYPosition<easyModeY+20)){
                modeTapped = "easy";
            }
            if ((fingerXPosition>=700)&&(fingerXPosition<900)&&(fingerYPosition>=hardModeY)&&(fingerYPosition<hardModeY+20)){
                modeTapped = "hard";
            }
        }
        else if (userAction == MotionEvent.ACTION_UP) {
            // user lifted their finger
        }
        return true;
    }
}