package csse2002.block.world;

import java.io.FileNotFoundException;

public class WorldMap {

    private Builder worldBuilder;
    private Position worldStartPosition;
    private SparseTileArray worldTileArray;

    /***
     * Initialise the WorldMap using a startingTile positioned at a
     * startingPosition and with a builder. The builder needs to be on the
     * starting tile (i.e. builder.getTile == startingTile)
     * @param startingTile
     * @param startPosition
     * @param builder
     * @throws WorldMapInconsistentException
     */
    public WorldMap(Tile startingTile, Position startPosition,
                    Builder builder) throws WorldMapInconsistentException{
        worldBuilder = builder;
        worldStartPosition = startPosition;
        worldTileArray = new SparseTileArray();
        worldTileArray.addLinkedTiles(startingTile, startPosition.getX(),
                startPosition.getY());
    }

    /***
     * Initialise the WorldMap using a file.
     * @param filename
     * @throws WorldMapInconsistentException
     * @throws FileNotFoundException
     */
    public WorldMap(String filename) throws WorldMapInconsistentException,
            FileNotFoundException {

    }
}
