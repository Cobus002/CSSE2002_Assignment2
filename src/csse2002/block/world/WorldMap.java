package csse2002.block.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/***
 * WorldMap class is used to load a new world map which will be manipulated
 * and traversed by the builder.
 *
 * @author Jacobus Hoffmann
 * @version 1.0
 */
public class WorldMap {

    private Builder worldBuilder;
    private Position worldStartPosition;
    private SparseTileArray worldTileArray;

    //Private constants to save block types
    private static final String WOOD_BLOCK = "wood";
    private static final String GRASS_BLOCK = "grass";
    private static final String SOIL_BLOCK = "soil";
    private static final String STONE_BLOCK = "stone";

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

    /**
     *
     * @param stringListOfBlocks
     * @return
     */
    private List<Block> stringListToBlockList(String stringListOfBlocks[]){
        List<Block> blockList = new LinkedList<>();
        for(String block:stringListOfBlocks){
            switch (block){
                case WOOD_BLOCK:
                    blockList.add(new WoodBlock());
                    break;
                case SOIL_BLOCK:
                    blockList.add(new SoilBlock());
                    break;
                case GRASS_BLOCK:
                    blockList.add(new GrassBlock());
                    break;
                case STONE_BLOCK:
                    blockList.add(new StoneBlock());
                    break;
                default:
                    //Unknown type return null list
                    return null;
            }

        }
        //If we get here then all is good so return the list
        return blockList;

    }

    /***
     * Initialise the WorldMap using a file.
     * @param filename
     * @throws WorldMapInconsistentException
     * @throws FileNotFoundException
     */
    public WorldMap(String filename) throws WorldMapFormatException,
            WorldMapInconsistentException,
            FileNotFoundException {
        //Get a buffered reader for the file, pointed to by the filename
        BufferedReader buffIn = new BufferedReader(new FileReader(filename));
        int lineCount = 0;
        String line = null;
        int startingX, startingY = 0;
        String builderName=null;
        List<Block> startingBlocks = new LinkedList<>();
        try {

            while ((line = buffIn.readLine())!=null){
                if(lineCount == 0){
                    try {
                        startingX = Integer.parseInt(line);
                    }catch (NumberFormatException e){
                        throw new WorldMapFormatException();
                    }
                }else if(lineCount == 1){
                    try {
                        startingY = Integer.parseInt(line);
                    }catch (NumberFormatException e){
                        throw new WorldMapFormatException();
                    }
                }else if(lineCount == 2){
                    builderName = line;
                }else if(lineCount == 3){
                    String inventoryList[] = line.split(",");
                    startingBlocks = stringListToBlockList(inventoryList);
                    if(startingBlocks == null){
                        throw new WorldMapFormatException();
                    }
                }
            }
        } catch (IOException e){
            System.err.println("Error: "+ e);
        }

    }
}
