package com.company;


/**
 * Created by roman on 09/04/16.
 */
public class Robot {
    private int mId;
    private Position mPosition;
    private int mDirection;

    public static final int UP=0;
    public static final int RIGHT=1;
    public static final int DOWN=2;
    public static final int LEFT=3;

    public Robot(int id, Position position, int direction){
        mId = id;
        mPosition=position;
        if(direction != UP && direction !=RIGHT && direction != DOWN && direction !=LEFT){
            mDirection=UP;
        }else{
            mDirection=direction;
        }
    }

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

    public void turnLeft(){
        if(mDirection<=0){
            mDirection=3;
        }else{
            mDirection--;
        }
    }
    public void turnRight(){
        if(mDirection>=3){
            mDirection=0;
        }else{
            mDirection++;
        }
    }

    public int getId() {
        return mId;
    }

    public Position getPosition() {
        return mPosition;
    }

    public int getDirection() {
        return mDirection;
    }
}
