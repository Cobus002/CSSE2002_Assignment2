package csse2002.block.world;

import org.junit.Test;

import static org.junit.Assert.*;

/***
 * This class serves as a test class for the SparseTileArray class, testing
 * the functionality.
 *
 * @author Jacobus Hoffmann
 * @version 1.0
 */
public class SparseTileArrayTest {

    /**
     * Test the addLinkedTiles() function of the SparseTileArray class. The
     * class
     * @throws WorldMapInconsistentException
     */
    @Test
    public void addLinkedTiles() throws WorldMapInconsistentException {
        //Create a few test Tiles
        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        Tile tile3 = new Tile();
        Tile tile4 = new Tile();
        Tile tile5 = new Tile();
        try {
            //Link the created tiles
            tile1.addExit("north", tile2);
            tile1.addExit("east", tile3);
            tile2.addExit("north", tile4);
            tile2.addExit("south", tile1);
            tile3.addExit("west", tile1);
            tile2.addExit("east", tile5);
            tile3.addExit("north", tile5);
            tile4.addExit("south", tile2);
            tile5.addExit("west", tile2);
            tile5.addExit("south", tile3);


        } catch (NoExitException e) {
            //squash since it should have been implemented correctly work
            System.out.println("There was an error loading exits");
        }
        SparseTileArray testTileArray = new SparseTileArray();
        assertSame(tile2, tile1.getExits().get("north"));
        //Test the add linked tiles function
        testTileArray.addLinkedTiles(tile1, 0, 0);
        assertEquals(null, testTileArray.getTile(null));
        assertEquals(null, testTileArray.getTile(
                new Position(1, 2)));
        //check the tile geometry using getTile()
        assertSame(tile1, testTileArray.getTile(new Position(0, 0)));
        assertSame(tile2, testTileArray.getTile(new Position(0, 1)));
        assertSame(tile3, testTileArray.getTile(new Position(1, 0)));
        assertSame(tile4, testTileArray.getTile(new Position(0, 2)));
        assertSame(tile5, testTileArray.getTile(new Position(1, 1)));
    }

    /**
     * Test that the addLinkedTiles() throws a WorldMapInconsistentException
     * when tiles are not properly linked.
     * @throws WorldMapInconsistentException
     */
    @Test(expected = WorldMapInconsistentException.class)
    public void addLinkedTilesInconsistentT1() throws
            WorldMapInconsistentException {
        //Create a few test Tiles
        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        Tile tile3 = new Tile();
        Tile tile4 = new Tile();
        Tile tile5 = new Tile();

        try {
            //Link the test Tiles to one another
            tile1.addExit("north", tile2);
            tile1.addExit("east", tile3);
            tile2.addExit("north", tile4);
            tile2.addExit("south", tile1);
            tile3.addExit("west", tile1);
            tile2.addExit("east", tile5);
            tile3.addExit("north", tile5);
            tile4.addExit("south", tile2);
            tile4.addExit("west", tile5);
            tile5.addExit("west", tile2);
            tile5.addExit("south", tile3);

        } catch (NoExitException e) {
            //squash since it should have been implemented correctly work
            System.out.println("There was an error loading exits");
        }
        SparseTileArray testTileArray = new SparseTileArray();
        assertSame(tile2, tile1.getExits().get("north"));
        //Test the add linked tiles function
        testTileArray.addLinkedTiles(tile1, 0, 0);

    }
}