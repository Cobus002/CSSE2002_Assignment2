package csse2002.block.world;

import java.util.*;

/***
 * SparseTileArray is an array representation of tiles arranged in a linked
 * node tree structure. The tiles are read and saved via a breadth-first
 * search algorithm.
 *
 * @author Jacobus Hoffmann
 * @version 999 =)
 */
public class SparseTileArray {
    //Private constants used for the exits
    private static final String NORTH = "north";
    private static final String EAST = "east";
    private static final String SOUTH = "south";
    private static final String WEST = "west";

    //List for the directions to make loops easier
    private static final List<String> DIRECTION_LIST = Arrays.asList(NORTH,
            EAST, SOUTH, WEST);
    private List<Tile> sparseTileArray;

    private Map<Position, Tile> sparseTileMap;

    public SparseTileArray(){
        sparseTileArray = new ArrayList<>();
        sparseTileMap = new LinkedHashMap<>();
    }

    public Tile getTile(Position position){
        if(position == null){
            return null;
        }
        return sparseTileMap.get(position);
    }

    public List<Tile> getTiles(){
        return this.sparseTileArray;
    }

    /***
     * checkNodeForConsistency() function checks that if the node has a
     * neighbour, then that neighbour links back to the node.
     * @param testTile
     * @return
     */
    private boolean checkNodeForConsistency(Tile testTile) {
        Iterator dirIterator = DIRECTION_LIST.iterator();
        Tile tempTile;
        while (dirIterator.hasNext()) {
            //check the direction
            String direction = (String) dirIterator.next();
            String oppositeDirection; //Default
            if (!testTile.getExits().containsKey(direction)) {
                //Make sure the tile does contain the exit
                continue;
            }
            switch (direction) {
                case NORTH:
                    oppositeDirection = SOUTH;
                    break;
                case EAST:
                    oppositeDirection = WEST;
                    break;
                case SOUTH:
                    oppositeDirection = NORTH;
                    break;
                case WEST:
                    oppositeDirection = EAST;
                    break;
                default:
                    //Error not recognised direction
                    return false;
            }
            tempTile =
                    testTile.getExits().get(direction).getExits().
                            get(oppositeDirection);
            if ((tempTile == null) || (tempTile != testTile)) {
                return false;
            }
        }

        //If we get here all is good
        return true;
    }

    /***
     * checkSparseTileMapConsistent() function checks if there are no
     * duplicate positions for a single Tile object. Returns true if there
     * are no duplicates.
     * @return
     */
    private boolean checkSparseTileMapConsistent(){
        Iterator topLevelIterator = sparseTileMap.entrySet().iterator();

        while(topLevelIterator.hasNext()){
            //Get the map entry
            Map.Entry<Position, Tile> pair = (Map.Entry)topLevelIterator.next();
            Tile tileToCheck = pair.getValue();
            Position tileToCheckPosition = pair.getKey();
            //Check that same node doesn't have multiple positions
            Iterator innerLevelIterator = sparseTileMap.entrySet().iterator();
            while(innerLevelIterator.hasNext()){
                Map.Entry<Position, Tile> pairInner =
                        (Map.Entry)innerLevelIterator.next();

                Tile innerTile = pairInner.getValue();
                Position innerTilePosition = pairInner.getKey();

                if((innerTile == tileToCheck)&&
                        (innerTilePosition!=tileToCheckPosition)){
                    return false;
                }
            }
        }
        //if we get here then all the entries where consistent
        return true;
    }


    /***
     * Add a set of linked tiles to the sparseTileMap. The tiles are linked
     * via exits "north", "east", "south" and "west".
     * @param startingTile
     * @param startingX
     * @param startingY
     * @throws WorldMapInconsistentException
     */
    public void addLinkedTiles(Tile startingTile, int startingX,
                               int startingY)
            throws WorldMapInconsistentException {
        //Clear map of any old data
        sparseTileMap.clear();
        //Check that the

        Queue<Position> nodesToVisit = new LinkedList<>();
        Set<Position> alreadyVisited = new LinkedHashSet<>();
        Map<Position, Tile> toVisitMap = new HashMap<>();

        Position currPos = new Position(startingX, startingY);
        toVisitMap.put(currPos, startingTile);
        //Now perform breadth-first search
        nodesToVisit.add(currPos);

        Map<String, Tile> exits;
        Tile tileAtPos;

        while(nodesToVisit.size()!=0){
            currPos = nodesToVisit.remove();
            tileAtPos = toVisitMap.get(currPos);
            //Make sure path links back to self, if not throw error and exit
            if(!checkNodeForConsistency(tileAtPos)){
                throw new WorldMapInconsistentException();
            }
            //Check the setup
            if(tileAtPos==null){
                System.out.println("Tile at position "+currPos.toString()+" " +
                        "is null");
                return;
            }
            if(!alreadyVisited.contains(currPos)){
                //First visit
                alreadyVisited.add(currPos);
                sparseTileMap.put(currPos, tileAtPos);
                sparseTileArray.add(tileAtPos);
                exits = tileAtPos.getExits();

                int currPosX = currPos.getX();
                int currPosY = currPos.getY();
                Iterator dirIterator = DIRECTION_LIST.iterator();
                while(dirIterator.hasNext()){
                    Position newPos = new Position(0,0);//default
                    String direction = (String)dirIterator.next();
                    switch (direction){
                        case NORTH:
                            newPos = new Position(currPosX, currPosY+1);
                            break;
                        case SOUTH:
                            newPos = new Position(currPosX, currPosY-1);
                            break;
                        case EAST:
                            newPos = new Position(currPosX+1, currPosY);
                            break;
                        case WEST:
                            newPos = new Position(currPosX-1, currPosY);
                            break;
                    }
                    if(exits.containsKey(direction)){
                        nodesToVisit.add(newPos);
                        toVisitMap.put(newPos, exits.get(direction));
                    }
                }
                //Exit first visit to tile
            }

        }

        if(!checkSparseTileMapConsistent()){
            //Error occurred, clear all loaded tiles
            sparseTileMap.clear();
            sparseTileArray.clear();
            throw new WorldMapInconsistentException();
        }
    }
}
