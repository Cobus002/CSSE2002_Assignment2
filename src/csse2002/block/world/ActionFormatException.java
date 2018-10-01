package csse2002.block.world;

/**
 * This class is used to throw an exception for when an action is formatted
 * incorrectly.
 *
 * @author Jacobus Hoffmann
 * @version 1.0
 */
public class ActionFormatException extends Exception {

    /**
     * Default constructor for the ActionFormatException
     */
    public ActionFormatException(){
    }

    /**
     * ActionFormatException constructor that allows the user to print an
     * aditional message when thrown.
     * @param message
     */
    public ActionFormatException(String message){
        System.out.println(message);
    }
}
