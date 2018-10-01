package csse2002.block.world;

/***
 * WorldFormatException class is used as a throwable exception when there is
 * a format error in a WorldMapFile.
 *
 * @author Jacobus Hoffmann
 * @version 1.0
 */
public class WorldMapFormatException extends BlockWorldException {

    /***
     * Default WorldMapFormatException constructor
     */
    public WorldMapFormatException() {
    }

    /***
     * WorldMapFormatException constructor with additional message for System
     * out.
     * @param message
     */
    public WorldMapFormatException(String message) {
        System.out.println("WorldMapFormatException: " + message);
    }
}
