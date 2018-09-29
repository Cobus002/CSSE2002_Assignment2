package csse2002.block.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

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

    //Private constants to save directions
    private static final String NORTH = "north";
    private static final String EAST = "east";
    private static final String SOUTH = "south";
    private static final String WEST = "west";

    //List to make looping through the directions easier
    private static final List<String> DIR_LIST = Arrays.asList(NORTH, EAST,
            SOUTH, WEST);

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
     * Initialise the WorldMap using a file. The world map needs to be in the
     * specified format, else a WorldMapFormatException is thrown. If there
     * are any inconsistencies in the tile layout a
     * WorldMapInconsistentException is thrown.
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
        int startingX, startingY, secondBlankLine = 0;
        int endOfFile = 0;
        int totalTiles = 0;
        String builderName=null;
        List<Block> startingBlocks = new LinkedList<>();
        List<Block> tileBlocksTempList = new LinkedList<>();
        Map<Integer, Tile> tileMap = new LinkedHashMap<>();

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
                    //If blank then inventory is empty:
                    if(line.equals("")){
                        //Do nothing and just continue to the nex line
                        continue;
                    }
                    String inventoryList[] = line.split(",");
                    startingBlocks = stringListToBlockList(inventoryList);
                    if(startingBlocks == null){
                        throw new WorldMapFormatException();
                    }
                }else if(lineCount==4){
                    //This line needs to be blank
                    if(!line.equals("")){
                        throw new WorldMapFormatException();
                    }
                }
                else if(lineCount==5){
                    //Read in the tiles
                    String line4[] = line.split(":", 2);
                    if(line4.length!=2){
                        throw new WorldMapFormatException();
                    }
                    if(!line4[0].equals("total")){
                        throw new WorldMapFormatException();
                    }
                    try{
                        totalTiles = Integer.parseInt(line4[1]);
                        if(!(totalTiles>0)){
                            throw new WorldMapFormatException();
                        }
                        secondBlankLine = lineCount+totalTiles+1;
                    }catch (NumberFormatException e){
                        throw new WorldMapFormatException();
                    }
                }else if(lineCount<secondBlankLine){
                    //Create tile with blocks
                    System.out.println(line);
                    //Check if missing lines are present
                    if(line.equals("")){
                        throw new WorldMapFormatException();
                    }
                    String tileLine[] = line.split(" ", 2);
                    tileBlocksTempList.clear();
                    tileBlocksTempList =
                            stringListToBlockList(tileLine[1].split(","));
                    if (tileBlocksTempList==null){
                        //Error occured, may have invalid block type
                        throw new WorldMapFormatException();
                    }else{
                        try {
                            Tile tempTile = new Tile(tileBlocksTempList);
                            Integer tempInt = Integer.parseInt(tileLine[0]);
                            if(!(tempInt>0) || (tempInt>(totalTiles-1))){
                                throw new NumberFormatException();
                            }
                            tileMap.put(tempInt, tempTile);
                        }catch(Exception e){
                            if(e instanceof TooHighException){
                                System.out.println("Loadind WorldMap too many " +
                                    "blocks on tile:"+tileLine[0]);

                            }else if(e instanceof NumberFormatException){
                                System.out.println("Not valid integer on " +
                                        "line: "+lineCount+" -> "+line);
                            }
                            throw new WorldMapFormatException();
                        }
                        //If we get here we are good...
                    }
                }else if(lineCount==secondBlankLine){
                    //Check that this line is in fact blank
                    if(!line.equals("")){
                        throw new WorldMapFormatException();
                    }
                }else if(lineCount==(secondBlankLine+1)){
                    //this must be the start of the exits
                    if(!line.equals("exits")){
                        throw new WorldMapFormatException();
                    }
                    endOfFile = lineCount+totalTiles+1;
                }else if(lineCount<endOfFile){
                    //Check for empty line, not valid
                    if(line.equals("")){
                        throw new WorldMapFormatException();
                    }

                }
                lineCount++;
            }
        } catch (IOException e){
            System.err.println("Error: "+ e);
            //Can't throw an IOException, not sure what to throw so throw
            // File not found exception.
            throw new FileNotFoundException();
        }

    }
}
