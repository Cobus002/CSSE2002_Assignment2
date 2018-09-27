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
    public void addLinkedTiles() throws WorldMapInconsistentException{

        Tile tile1 = new Tile();
        Tile tile2 = new Tile();
        Tile tile3 = new Tile();
        Tile tile4 = new Tile();
        Tile tile5 = new Tile();

        try {
            tile1.addExit("north", tile2);
            tile1.addExit("east", tile3);

            tile2.addExit("north", tile4);
            tile2.addExit("south", tile1);

            tile3.addExit("west", tile1);

            tile2.addExit("east", tile5);
            tile3.addExit("north", tile5);

            tile5.addExit("west", tile2);
            tile5.addExit("south", tile3);


        }catch (NoExitException e) {
            //squash since it should have been implemented correctly work
            System.out.println("There was an error loading exits");
        }

        if(tile2.getExits().get("south") == tile1){
            System.out.println("Same");

        }
        SparseTileArray testTileArray = new SparseTileArray();

        assertSame(tile2, tile1.getExits().get("north"));
        //Test the add linked tiles function
        testTileArray.addLinkedTiles(tile1, 0, 0);

        //check the tiles using getTile


    }
}