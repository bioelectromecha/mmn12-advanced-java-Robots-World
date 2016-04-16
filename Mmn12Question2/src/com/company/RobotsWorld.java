package com.company;

/**
 * RobotsWorld class - represents the matrix and all the robots inside
 * Created by roman on 09/04/16.
 */

public class RobotsWorld {
    //private fields
    private Robot[][] mRobotWorld;
    private int mWorldSize;
    private int mRobotCount;

    /**
     * constructor to instantiate the robot matrix
     * @param size size of the matrix to create
     */
    public RobotsWorld(int size){
        mRobotCount=0;
        //matrix size constraints
        if(size>100 || size<=0){
            mWorldSize=100;
        }else{
            mWorldSize=size;
        }
        mRobotWorld = new Robot[mWorldSize][mWorldSize];
    }

    /**
     * add a robot to the matrix
     * @param p the position at which to add the new robot
     * @throws IllegalPositionException gets thrown out if robot can't be placed at the requested position
     */
    public void addRobot(Position p) throws IllegalPositionException{
        //check that everything is constraints and not null
        if(p==null || p.getX()<0 || p.getY()<0 || p.getX()>mWorldSize || p.getY()>mWorldSize || mRobotWorld[p.getX()][p.getY()] != null){
            throw new IllegalPositionException();
        }
        //create and add the new robot
        mRobotWorld[p.getX()][p.getY()] = new Robot(mRobotCount,p,Robot.UP);
        mRobotCount++;
    }

    /**
     * remove a robot from teh matrix
     * @param p the position at which to remove the robot
     * @return the Robot which was removed
     */
    public Robot removeRobot(Position p){
        //check that everything is constraints and not null
        if(p==null || p.getX()<0 || p.getY()<0 || p.getX()>mWorldSize || p.getY()>mWorldSize || mRobotWorld[p.getX()][p.getY()] == null){
            return null;
        }
        //remove and and return the removed robot
        Robot temp = mRobotWorld[p.getX()][p.getY()];
        mRobotWorld[p.getX()][p.getY()]=null;
        return temp;
    }

    /**
     * get the robot at specific Position
     * @param p Poisiton object
     * @return the Robot at the requested position, or null
     */
    public Robot getRobot(Position p){
        //check that everything is in constraints and not null
        if(p==null || p.getX()<0 || p.getY()<0 || p.getX()>mWorldSize || p.getY()>mWorldSize || mRobotWorld[p.getX()][p.getY()] == null){
            return null;
        }
        return mRobotWorld[p.getX()][p.getY()];
    }

    /**
     * move the robot inside the matrix from one cell to another
     * @param p Position at which to move the robot
     * @throws IllegalPositionException gets thrown if it's not possible to move the robot, or if there's no robot
     */
    public void moveRobot(Position p) throws IllegalPositionException{
        //check that everything is in constraints and not null
        if(p==null || p.getX()<0 || p.getY()<0 || p.getX()>mWorldSize || p.getY()>mWorldSize || mRobotWorld[p.getX()][p.getY()] == null){
            throw new IllegalPositionException();
        }
        //move the temp robot - which is a copy of the robot at position
        Robot tempRobot = new Robot(getRobot(p));
        tempRobot.move();
        Position destinationPosition=tempRobot.getPosition();
        //check if the new position will be find
        if(destinationPosition==null || destinationPosition.getX()<0 || destinationPosition.getY()<0 || destinationPosition.getX()>=mWorldSize || destinationPosition.getY()>=mWorldSize || mRobotWorld[destinationPosition.getX()][destinationPosition.getY()] != null){
            throw new IllegalPositionException();
        }
        //move the robot to the new position
        mRobotWorld[destinationPosition.getX()][destinationPosition.getY()]=tempRobot;
        removeRobot(p);
    }

}

