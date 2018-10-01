package csse2002.block.world;

/**
 * WorldMapInconsistentException class is used as a throwable exception for
 * when there is a geometrical inconsistency within the WorldMap.
 *
 * @author Jacobus Hoffmann
 * @version 1.0
 */
public class WorldMapInconsistentException extends Exception {

    /**
     * Default constructor
     */
    public WorldMapInconsistentException(){

    }

    /**
     * Constructor with message that can is printed when the exception is
     * thrown.
     * @param message
     */
    public WorldMapInconsistentException(String message){
        System.out.println(message);
    }

}
