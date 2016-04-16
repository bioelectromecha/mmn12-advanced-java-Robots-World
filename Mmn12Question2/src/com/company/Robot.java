package com.company;

/**
 * Class that represents a single robot inside a matrix of cells (Robots World)
 * Created by roman on 09/04/16.
 */
public class Robot {
    //private fields
    private int mId;
    private Position mPosition;
    private int mDirection;

    //constant fields
    public static final int UP=0;
    public static final int RIGHT=1;
    public static final int DOWN=2;
    public static final int LEFT=3;

    /**
     * main constructor
     * @param id robot id
     * @param position robot position
     * @param direction direction the robot is facing
     */
    public Robot(int id, Position position, int direction){
        mId = id;
        mPosition=position;
        if(direction != UP && direction !=RIGHT && direction != DOWN && direction !=LEFT){
            mDirection=UP;
        }else{
            mDirection=direction;
        }
    }

    /**
     * copy constructor
     * @param other another Robot object
     */
    public Robot(Robot other) {
        if(other == null){
            mId=0;
            mPosition=new Position();
            mDirection=UP;
        }else {
            mId = other.getId();
            mPosition = new Position(other.getPosition());
            mDirection = other.getDirection();
        }
    }

    /**
     * move the robot in accordance with the direction it's facing
     */
    public void move(){
        if(mDirection==UP){
            mPosition.setY(-1+mPosition.getY());
        }else if(mDirection==RIGHT){
            mPosition.setX(1+mPosition.getX());
        }else if(mDirection==DOWN) {
            mPosition.setY(1+mPosition.getY());
        }else if(mDirection==LEFT) {
            mPosition.setX(-1+mPosition.getX());
        }
    }

    /**
     * turn the robot left
     */
    public void turnLeft(){
        if(mDirection==UP){
            mDirection=LEFT;
        }else{
            mDirection--;
        }
    }

    /**
     * turn the robot right
     */
    public void turnRight(){
        if(mDirection>=3){
            mDirection=0;
        }else{
            mDirection++;
        }
    }

    /**
     * get the robot id
     * @return int robot id
     */
    public int getId() {
        return mId;
    }

    /**
     * get the robot position
     * @return robot Position object
     */
    public Position getPosition() {
        return mPosition;
    }

    /**
     * return the direction the robot is facing
     * @return int direction of robot
     */
    public int getDirection() {
        return mDirection;
    }
}
