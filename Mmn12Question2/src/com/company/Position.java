package com.company;

/**
 * Created by roman on 09/04/16.
 */
public class Position {
    private int mXPosition;
    private int mYPosition;

    public Position(){
        mXPosition =0;
        mYPosition =0;
    }
    public Position(int x, int y){
        mXPosition =x;
        mYPosition =y;
    }
    public Position(Position other){
        if(other == null){
            mXPosition =0;
            mYPosition =0;
        }else{
            mXPosition=other.getX();
            mYPosition=other.getY();
        }
    }

    public int getX() {

        return mXPosition;
    }

    public void setX(int xPosition) {
        mXPosition = xPosition;
    }

    public int getY() {
        return mYPosition;
    }

    public void setY(int yPosition) {
        mYPosition = yPosition;
    }
}
