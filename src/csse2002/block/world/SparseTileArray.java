package csse2002.block.world;

import java.util.*;

public class SparseTileArray {

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

    public void addLinkedTiles(Tile startingTile, int startingX,
                               int startingY)
            throws WorldMapInconsistentException {

        sparseTileMap.clear();
        //Check cleared
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
                if(exits.containsKey("north")){
                    Tile tempTile = exits.get("north");
                    Position newPos = new Position(currPosX, currPosY+1);
                    nodesToVisit.add(newPos);
                    sparseTileMap.put(newPos, tempTile);
                    //Check
                }

                if(exits.containsKey("south")){
                    Tile tempTile = exits.get("south");
                    Position newPos = new Position(currPosX, currPosY-1);
                    nodesToVisit.add(newPos);
                    sparseTileMap.put(newPos, tempTile);
                    //Check
                }

                if(exits.containsKey("east")){
                    Tile tempTile = exits.get("east");
                    Position newPos = new Position(currPosX+1, currPosY);
                    nodesToVisit.add(newPos);
                    sparseTileMap.put(newPos, tempTile);
                    //Check
                }

                if(exits.containsKey("west")){
                    Tile tempTile = exits.get("west");
                    Position newPos = new Position(currPosX-1, currPosY);
                    nodesToVisit.add(newPos);
                    sparseTileMap.put(newPos, tempTile);
                    //Check
                }

                if(exits.containsKey("blah")){
                    throw new WorldMapInconsistentException();
                }
                //Exit first visit to tile
            }

        }

        //Check the tile



            }


}
