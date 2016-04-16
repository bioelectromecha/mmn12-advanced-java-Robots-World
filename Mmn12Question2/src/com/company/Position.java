package com.company;


/**
 * this class represents the position of the robot
 * Created by roman on 09/04/16.
 */
public class Position {
    //private fields
    private int mXPosition;
    private int mYPosition;

    /**
     * empty constructor
     */
    public Position(){
        mXPosition =0;
        mYPosition =0;
    }

    /**
     * main constrcutor
     * @param x X positon
     * @param y Y position
     */
    public Position(int x, int y){
        mXPosition =x;
        mYPosition =y;
    }

    /**
     * copy constructor
     * @param other
     */
    public Position(Position other){
        if(other == null){
            mXPosition =0;
            mYPosition =0;
        }else{
            mXPosition=other.getX();
            mYPosition=other.getY();
        }
    }

    /**
     * X position getter
     * @return X position
     */
    public int getX() {
        return mXPosition;
    }
    /**
     * X position setter
     * @param xPosition  X position
     */
    public void setX(int xPosition) {
        mXPosition = xPosition;
    }

    /**
     * Y position getter
     * @return Y position
     */
    public int getY() {
        return mYPosition;
    }

    /**
     * Y position setter
     * @param yPosition Y position
     */
    public void setY(int yPosition) {
        mYPosition = yPosition;
    }
}
