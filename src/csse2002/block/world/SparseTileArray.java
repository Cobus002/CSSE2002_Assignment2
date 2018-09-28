package csse2002.block.world;

import java.util.*;

public class SparseTileArray {
    //Private constants used for the exits
    private static final String NORTH = "north";
    private static final String EAST = "east";
    private static final String SOUTH = "south";
    private static final String WEST = "west";

    //List for the directions to make loops easier
    private static final List<String>  directionList = Arrays.asList(NORTH,
            EAST, SOUTH, WEST);
    private List<Tile> sparseTileArray;

    private Map<Position, Tile> sparseTileMap;

    public SparseTileArray(){
        sparseTileArray = new ArrayList<>();
        sparseTileMap = new HashMap<>();
    }

    public Tile getTile(Position position){
        return sparseTileMap.get(position);

    }


    public List<Tile> getTiles(){
        //Todo implement function
        return this.sparseTileArray;
    }

    private boolean checkSparseTileMapConsistent(){
        Iterator topLevelIterator = sparseTileMap.entrySet().iterator();

        while(topLevelIterator.hasNext()){
            //Get the map entry
            Map.Entry<Position, Tile> pair = (Map.Entry)topLevelIterator.next();

            Tile tileToCheck = pair.getValue();
            Position tileToCheckPosition = pair.getKey();
            Tile tempTile;
            boolean consistent = true;
            //Check that the nodes link back to one another
/*            if(tileToCheck.getExits().containsKey(NORTH)){
                //Check if the tiles are properly linked
                tempTile =
                        tileToCheck.getExits().get(NORTH).getExits().get(SOUTH);
                consistent = (tileToCheck == tempTile)? true:false;
            }
            if(tileToCheck.getExits().containsKey(SOUTH)){
                tempTile =
                        tileToCheck.getExits().get(SOUTH).getExits().get(NORTH);
                consistent = (tileToCheck == tempTile)? true:false;

            }
            if(tileToCheck.getExits().containsKey(EAST)){
                tempTile =
                        tileToCheck.getExits().get(EAST).getExits().get(WEST);
                consistent = (tileToCheck == tempTile)? true:false;

            }

            if(tileToCheck.getExits().containsKey(WEST)){
                tempTile =
                        tileToCheck.getExits().get(WEST).getExits().get(EAST);
                consistent = (tileToCheck == tempTile)? true:false;
            }*/

            Iterator dirIterator = directionList.iterator();
            while(dirIterator.hasNext()){
                //check the direction
                String direction = (String)dirIterator.next();
                String opositeDirection = NORTH; //Default
                if(!tileToCheck.getExits().containsKey(direction)){
                    //Make sure the tile does contain the exit
                    continue;
                }
                switch(direction){
                    case NORTH:
                        opositeDirection = SOUTH;
                        break;
                    case EAST:
                        opositeDirection = WEST;
                        break;
                    case SOUTH:
                        opositeDirection = NORTH;
                        break;
                    case WEST:
                        opositeDirection = EAST;
                        break;
                    default:
                        //Error not recognised direction
                        return false;
                }

                tempTile =
                        tileToCheck.getExits().get(direction).getExits().
                                get(opositeDirection);
                if((tempTile==null)||(tempTile!=tileToCheck)){
                    return false;
                }


            }

            //Check that same node doesn't have multiple positions
            Iterator innerLevelIterator = sparseTileMap.entrySet().iterator();
            while(innerLevelIterator.hasNext()){
                Map.Entry<Position, Tile> pairInner =
                        (Map.Entry)innerLevelIterator.next();

                Tile innerTile = pairInner.getValue();
                Position innerTilePosition = pairInner.getKey();

                if((innerTile == (Tile)pair.getValue())&&
                        (innerTilePosition!=tileToCheckPosition)){
                    consistent = false;
                }
            }


            if(!consistent){
                //if one entry is inconsistent then all no good
                return false;
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

        Position currPos = new Position(startingX, startingY);
        sparseTileMap.put(currPos, startingTile);
        //Now perform breadth-first search

        Queue<Position> nodesToVisit = new LinkedList<>();
        Set<Position> alreadyVisited = new HashSet<>();
        nodesToVisit.add(currPos);

        Map<String, Tile> exits;
        Tile tileAtPos;

        while(nodesToVisit.size()!=0){

            currPos = nodesToVisit.remove();
            tileAtPos = sparseTileMap.get(currPos);
            //Check the setup
            if(tileAtPos==null){
                System.out.println("Tile at position "+currPos.toString()+" " +
                        "is null");
                return;
            }

            if(!alreadyVisited.contains(currPos)){
                //First visit
                alreadyVisited.add(currPos);
                exits = tileAtPos.getExits();
                System.out.println(currPos.toString());

                int currPosX = currPos.getX();
                int currPosY = currPos.getY();
                if(exits.containsKey(NORTH)){
                    Tile tempTile = exits.get(NORTH);
                    Position newPos = new Position(currPosX, currPosY+1);
                    sparseTileMap.put(newPos, tempTile);
                    sparseTileArray.add(tempTile);
                    nodesToVisit.add(newPos);
                }

                if(exits.containsKey(EAST)){
                    Tile tempTile = exits.get(EAST);
                    Position newPos = new Position(currPosX+1, currPosY);
                    nodesToVisit.add(newPos);
                    sparseTileMap.put(newPos, tempTile);
                    sparseTileArray.add(tempTile);
                    //Check
                }
                if(exits.containsKey(SOUTH)){
                    Tile tempTile = exits.get(SOUTH);
                    Position newPos = new Position(currPosX, currPosY-1);
                    nodesToVisit.add(newPos);
                    sparseTileMap.put(newPos, tempTile);
                    sparseTileArray.add(tempTile);

                }

                if(exits.containsKey(WEST)){
                    Tile tempTile = exits.get(WEST);
                    Position newPos = new Position(currPosX-1, currPosY);
                    nodesToVisit.add(newPos);
                    sparseTileMap.put(newPos, tempTile);
                    sparseTileArray.add(tempTile);
                }
                //Exit first visit to tile
            }

        }
        //All entries added now check that they are all consistent
        if(!checkSparseTileMapConsistent()){
            //Error occurred, clear all loaded tiles
            sparseTileMap.clear();
            sparseTileArray.clear();
            throw new WorldMapInconsistentException();
        }
    }
}
