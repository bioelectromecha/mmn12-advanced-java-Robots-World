package com.company;

/**
 * Created by roman on 09/04/16.
 */
public class IllegalPositionException extends Exception {

    public IllegalPositionException() {
        super("illegal robot world position - out of robot world bounds or a robot already exists in requested position");
    }

}
