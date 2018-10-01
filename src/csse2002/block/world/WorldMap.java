package csse2002.block.world;

import java.io.*;
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
    //Private varaibles used in the WorldMap
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
                    Builder builder) throws WorldMapInconsistentException {
        worldBuilder = builder;
        worldStartPosition = startPosition;
        worldTileArray = new SparseTileArray();
        worldTileArray.addLinkedTiles(startingTile, startPosition.getX(),
                startPosition.getY());
    }

    /**
     * stringListToBlockList() function is just a helper function used in the
     * WorldMap(filename) constructor. It returns a List of blocks given an
     * array of Strings. If there are invalid blocks types the function
     * returns null.
     * @param stringListOfBlocks
     * @return
     */
    private List<Block> stringListToBlockList(String stringListOfBlocks[]) {
        List<Block> blockList = new LinkedList<>();
        if((stringListOfBlocks.length == 1)&&(stringListOfBlocks[0].equals(""))){
            //Empty list
            return blockList;
        }
        for (String block : stringListOfBlocks) {
            switch (block) {
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
     * This addExitsToTile() function takes a tile map, an id and a string of
     * exits. It then attempts to add the exits in the string to the tile
     * with id in the tileMap. The function returns true on success and false
     * if an error occurs.
     * @param tileMap
     * @param tileId
     * @param exits
     * @return
     */
    private boolean addExitsToTile(Map<Integer, Tile> tileMap,
                                   Integer tileId, String exits) {
        //Format:<name1>:<id1>,<name2>:<id2>, ... ,<nameN>:<idN>

        Tile tileToUpdate = tileMap.get(tileId);
        if (tileToUpdate == null) {
            return false;
        }

        String exitsArray[] = exits.split(",");
        for (int i = 0; i < exitsArray.length; i++) {
            String tempString[] = exitsArray[i].split(":", 2);
            if(tempString[0].equals("")){
                //No exit on this tile
                return true;
            }else if (!DIR_LIST.contains(tempString[0])) {
                return false;
            }
            try {
                Integer tempId = Integer.parseInt(tempString[1]);
                Tile tempTile = tileMap.get(tempId);
                tileMap.get(tileId).addExit(tempString[0], tempTile);

            } catch (Exception e) {
                if (e instanceof NumberFormatException) {
                    System.err.println("Not valid number");
                } else if (e instanceof NullPointerException) {
                    System.err.println("Null pointer exception triggered");

                }
                return false;
            }
        }
        //If we get here all is good
        return true;
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
        int startingX = 0;
        int startingY = 0;
        int secondBlankLine = 0;
        int endOfFile = 0;
        int totalTiles = 0;
        String builderName = null;
        List<Block> startingBlocks = new LinkedList<>();
        List<Block> tileBlocksTempList = new LinkedList<>();
        Map<Integer, Tile> tileMap = new LinkedHashMap<>();

        try {
            while ((line = buffIn.readLine()) != null) {
                if (lineCount == 0) {
                    try {
                        startingX = Integer.parseInt(line);
                    } catch (NumberFormatException e) {
                        throw new WorldMapFormatException();
                    }
                } else if (lineCount == 1) {
                    try {
                        startingY = Integer.parseInt(line);
                    } catch (NumberFormatException e) {
                        throw new WorldMapFormatException();
                    }
                } else if (lineCount == 2) {
                    builderName = line;
                } else if (lineCount == 3) {
                    //If blank then inventory is empty:
                    if (line.equals("")) {
                        //Do nothing and just continue to the nex line
                    }
                    //Can have empty starting inventory:
                    String inventoryList[] = line.split(",");
                    startingBlocks = stringListToBlockList(inventoryList);
                    if (startingBlocks == null) {
                        throw new WorldMapFormatException();
                    }
                } else if (lineCount == 4) {
                    //This line needs to be blank
                    if (!line.equals("")) {
                        throw new WorldMapFormatException();
                    }
                } else if (lineCount == 5) {
                    //Read in the tiles
                    String line4[] = line.split(":", 2);
                    if (line4.length != 2) {
                        throw new WorldMapFormatException();
                    }
                    if (!line4[0].equals("total")) {
                        throw new WorldMapFormatException();
                    }
                    try {
                        totalTiles = Integer.parseInt(line4[1]);
                        if (!(totalTiles > 0)) {
                            throw new WorldMapFormatException();
                        }
                        secondBlankLine = lineCount + totalTiles + 1;
                    } catch (NumberFormatException e) {
                        throw new WorldMapFormatException();
                    }
                } else if (lineCount < secondBlankLine) {
                    //Create tile with blocks
                    //Check if missing lines are present
                    if (line.equals("")) {
                        throw new WorldMapFormatException();
                    }
                    String tileLine[] = line.split(" ", 2);
                    tileBlocksTempList.clear();
                    try {
                        tileBlocksTempList =
                                stringListToBlockList(tileLine[1].split(","));
                    }catch (Exception e){
                        //Error with array index
                        throw new WorldMapFormatException();
                    }
                    if (tileBlocksTempList == null) {
                        //Error occured, may have invalid block type
                        throw new WorldMapFormatException();
                    } else {
                        try {
                            Tile tempTile = new Tile(tileBlocksTempList);
                            Integer tempInt = Integer.parseInt(tileLine[0]);
                            if (!(tempInt >= 0)
                                    || (tempInt > (totalTiles - 1))) {
                                throw new NumberFormatException();
                            }
                            tileMap.put(tempInt, tempTile);
                        } catch (Exception e) {
                            if (e instanceof TooHighException) {
                                /*System.out.println("Loadind WorldMap too " +
                                        "many" +
                                        " " +
                                        "blocks on tile:" + tileLine[0]);*/
                            } else if (e instanceof NumberFormatException) {
                                /*System.out.println("Not valid integer on " +
                                        "line: " + lineCount + " -> " + line);*/
                            }
                            throw new WorldMapFormatException();
                        }
                        //If we get here we are good...
                    }
                } else if (lineCount == secondBlankLine) {
                    //Check that this line is in fact blank
                    if (!line.equals("")) {
                        throw new WorldMapFormatException();
                    }
                } else if (lineCount == (secondBlankLine + 1)) {
                    //this must be the start of the exits
                    if (!line.equals("exits")) {
                        throw new WorldMapFormatException();
                    }
                    endOfFile = lineCount + totalTiles + 1;
                } else if (lineCount < endOfFile) {
                    //Check for empty line, not valid
                    if (line.equals("")) {
                        throw new WorldMapFormatException();
                    }

                    String exitsLine[] = line.split(" ", 2);
                    try {
                        int id = Integer.parseInt(exitsLine[0]);
                        //check that the id is non negative and is within N-1
                        // range
                        if (!(id >= 0) || id > (totalTiles - 1)) {
                            throw new NumberFormatException();
                        }
                        if (!addExitsToTile(tileMap, id, exitsLine[1])) {
                            //Error occured adding the exits to the tile
                            throw new WorldMapFormatException();
                        }
                    } catch (Exception e) {
                        if (e instanceof NumberFormatException) {
                            System.err.println(e);
                        }
                        //Always throw this error since it is a problem with
                        // the file format.
                        throw new WorldMapFormatException();
                    }
                } else if (lineCount > endOfFile) {
                    //There shouldn't be anymore text so throw a
                    // WorldFormatException
                    throw new WorldMapFormatException();

                }
                lineCount++;
            }
        } catch (IOException e) {
            System.err.println("Error: " + e);
            //Can't throw an IOException, not sure what to throw so throw
            // File not found exception.
            throw new FileNotFoundException();
        }
        //If we get here all the data has been successfully loaded.
        //May still have inconsistency errors
        if (startingBlocks.size() > 0) {
            try {
                worldBuilder = new Builder(builderName, tileMap.get(0),
                        startingBlocks);
            } catch (InvalidBlockException e) {
                throw new WorldMapFormatException();
            }
        } else {
            worldBuilder = new Builder(builderName, tileMap.get(0));
        }
        //Now populate the WorldMap variables
        worldStartPosition = new Position(startingX, startingY);
        worldTileArray = new SparseTileArray();
        worldTileArray.addLinkedTiles(tileMap.get(0), startingX, startingY);
    }


    /**
     * getBuilder() function to return the current world Builder.
     * @return
     */
    public Builder getBuilder() {
        return this.worldBuilder;
    }

    /**
     * getStartPosition() function is used to get the start position of the
     * current WorldMap.
     * @return
     */
    public Position getStartPosition() {
        return this.worldStartPosition;
    }

    /**
     * getTile() is used to get the tile at the position given.
     * @param position
     * @return
     */
    public Tile getTile(Position position) {
        return this.worldTileArray.getTile(position);
    }

    /**
     * getTiles() function is used to get a breadth-first ordered List of
     * tiles that are currently loaded into the WorldMap.
     * @return
     */
    public List<Tile> getTiles() {
        return this.worldTileArray.getTiles();
    }

    /**
     * This saveMap() function saves the current WolrdMap state in text
     * format in the filename provided. The format of the saved file will be
     * the same as for reading a saved file.
     *
     * @param filename
     * @throws IOException
     */
    public void saveMap(String filename) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(filename));

        //Print the start position to the file
        writer.print(worldStartPosition.getX() + "\n");
        writer.print(worldStartPosition.getY() + "\n");
        writer.print(worldBuilder.getName() + "\n");
        int inventoryCount = worldBuilder.getInventory().size();
        int blockCount = 0;
        Iterator inventoryIterator = worldBuilder.getInventory().iterator();
        while (inventoryIterator.hasNext()) {
            Block currBlock = (Block) inventoryIterator.next();
            if (blockCount < inventoryCount - 1) {
                writer.print(currBlock.getBlockType() + ",");
            } else {
                writer.print(currBlock.getBlockType() + "\n");
            }
            blockCount++;
        }
        //Print empty line to the file
        writer.print("\n");
        //Now for the tiles in the WorldMap
        int tileCount = getTiles().size();
        writer.print("total:" + tileCount + "\n");
        Iterator tileIterator = getTiles().iterator();
        while (tileIterator.hasNext()) {
            Tile currTile = (Tile) tileIterator.next();
            int tileIndex = getTiles().indexOf(currTile);
            writer.print(tileIndex + " ");

            int tileBlockCount = currTile.getBlocks().size();
            Iterator tileBlocksIterator = currTile.getBlocks().iterator();
            while (tileBlocksIterator.hasNext()) {
                Block currBlock = (Block) tileBlocksIterator.next();
                if (tileBlockCount > 1) {
                    writer.print(currBlock.getBlockType() + ",");
                } else {
                    writer.print(currBlock.getBlockType() + "\n");
                }
                tileBlockCount--;
            }
        }
        //Add the empty line between tile and exits section
        writer.print("\n");
        writer.print("exits\n");
        tileIterator = getTiles().iterator();
        while (tileIterator.hasNext()) {
            Tile currTile = (Tile) tileIterator.next();
            writer.print(getTiles().indexOf(currTile) + " ");
            Iterator exitsIterator = currTile.getExits().entrySet().iterator();
            int exitsCount = currTile.getExits().size();
            while (exitsIterator.hasNext()) {
                Map.Entry pair = (Map.Entry) exitsIterator.next();
                String exitDir = (String) pair.getKey();
                Tile exitTile = (Tile) pair.getValue();
                if (exitsCount > 1) {
                    writer.print(exitDir + ":" + getTiles().indexOf(exitTile)
                            + ",");
                } else {
                    writer.print(exitDir + ":" +
                            getTiles().indexOf(exitTile) + "\n");
                }
                exitsCount--;
            }
        }

        //Flush the output buffer
        writer.flush();
        //Close the file
        writer.close();
    }
}
