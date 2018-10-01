package csse2002.block.world;

/**
 * This Position class is used to represent the position of a tile in the
 * SparseTileArray.
 *
 * @author Jacobus Hoffmann
 * @version 999
 */
public class Position implements Comparable<Position> {
    //Private variables to save the x and y values
    private int x, y;

    /**
     * Constructor of the Position class which takes an xy and y coordinate,
     * and assigns it to the private class variables.
     * @param x
     * @param y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;

    }

    /**
     * getX() class method returns the x position of the Position instance
     * @return
     */
    public int getX() {
        return this.x;
    }

    /**
     * getY() class method returns the y position fo the Position instance
     * @return
     */
    public int getY() {
        return this.y;
    }


    /**
     * equals function overriden so that two positions can be compared
     * according to:
     * Two Positions are equal if getX() == other.getX() &&
     * getY() == other.getY()
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        Position otherPosition = (Position) obj;

        if ((getX() == otherPosition.getX()) && (getY() == otherPosition.getY())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Compute the hashcode of the current Position instance
     * @return
     */
    @Override
    public int hashCode() {

        return (getX() * 10 + getY());
    }

    /**
     * compare to function compares to positions according to the following:
     * -1 if getX() < other.getX()
     * -1 if getX() == other.getX() and getY() < other.getY()
     * 0 if getX() == other.getX() and getY() == other.getY()
     * 1 if getX() > other.getX()
     * 1 if getX() == other.getX() and getY() > other.getY()
     * @param other
     * @return
     */
    public int compareTo(Position other) {
        if (getX() < other.getX()) {
            return -1;

        } else if (getX() > other.getX()) {
            return 1;

        } else if (getY() < other.getX()) {
            return -1;

        } else if (getY() > other.getY()) {
            return 1;

        } else {
            return 0;
        }
    }

    /**
     * toString returns a String representation of the current position
     * instance such that the output would look like (x, y)
     * @return
     */
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}
