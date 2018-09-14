package csse2002.block.world;

import org.junit.Test;

import static org.junit.Assert.*;

public class SparseTileArrayTest {

    @Test
    public void getTile() {
    }

    @Test
    public void getTiles() {
    }

    @Test
    public void addLinkedTiles() throws NoExitException, WorldMapInconsistentException{

        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        Tile tile3 = new Tile();
        Tile tile4 = new Tile();

        tile1.addExit("north", tile2);
        tile1.addExit("east", tile3);

        tile2.addExit("north", tile4);
        tile2.addExit("south", tile1);

        tile3.addExit("west", tile1);

        SparseTileArray testTileArray = new SparseTileArray();

        assertSame(tile2, tile1.getExits().get("north"));
        //Thus far
        testTileArray.addLinkedTiles(tile1, 0, 0);


    }
}