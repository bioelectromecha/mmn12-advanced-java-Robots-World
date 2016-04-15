package com.company;

/**
 * Created by roman on 09/04/16.
 */
public class RobotsWorld {
    private Robot[][] mRobotWorld;
    private int mWorldSize;
    private int mRobotCount;

    public RobotsWorld(int size){
        mRobotCount=0;
        if(size>100 || size<=0){
            mWorldSize=100;
        }else{
            mWorldSize=size;
        }
        mRobotWorld = new Robot[mWorldSize][mWorldSize];
    }

    public void addRobot(Position p) throws IllegalPositionException{
        if(p==null || p.getX()<0 || p.getY()<0 || p.getX()>mWorldSize || p.getY()>mWorldSize || mRobotWorld[p.getX()][p.getY()] != null){
            throw new IllegalPositionException();
        }
        mRobotWorld[p.getX()][p.getY()] = new Robot(mRobotCount,p,Robot.UP);
        mRobotCount++;
    }

    public Robot removeRobot(Position p){
        if(p==null || p.getX()<0 || p.getY()<0 || p.getX()>mWorldSize || p.getY()>mWorldSize || mRobotWorld[p.getX()][p.getY()] == null){
            return null;
        }
        Robot temp = mRobotWorld[p.getX()][p.getY()];
        mRobotWorld[p.getX()][p.getY()]=null;
        return temp;
    }

    public Robot getRobot(Position p){
        if(p==null || p.getX()<0 || p.getY()<0 || p.getX()>mWorldSize || p.getY()>mWorldSize || mRobotWorld[p.getX()][p.getY()] == null){
            return null;
        }
        return mRobotWorld[p.getX()][p.getY()];
    }

    public void moveRobot(Position p) throws IllegalPositionException{
        if(p==null || p.getX()<0 || p.getY()<0 || p.getX()>mWorldSize || p.getY()>mWorldSize || mRobotWorld[p.getX()][p.getY()] == null){
            throw new IllegalPositionException();
        }
        Robot tempRobot = new Robot(getRobot(p));
        tempRobot.move();
        Position destinationPosition=tempRobot.getPosition();
        if(destinationPosition==null || destinationPosition.getX()<0 || destinationPosition.getY()<0 || destinationPosition.getX()>=mWorldSize || destinationPosition.getY()>=mWorldSize || mRobotWorld[destinationPosition.getX()][destinationPosition.getY()] != null){
            throw new IllegalPositionException();
        }
        mRobotWorld[destinationPosition.getX()][destinationPosition.getY()]=tempRobot;
        removeRobot(p);
    }

    public int getWorldSize() {
        return mWorldSize;
    }
}

