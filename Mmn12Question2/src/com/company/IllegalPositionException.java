package com.company;

/**
 * this class represents an expecton that is thrown when a Robot tries to move
 * into an invalid position or a similar invalid situation
 * Created by roman on 09/04/16.
 */
public class IllegalPositionException extends Exception {

    public IllegalPositionException() {
        super("illegal robot world position - out of robot world bounds or a robot already exists in requested position");
    }

}
