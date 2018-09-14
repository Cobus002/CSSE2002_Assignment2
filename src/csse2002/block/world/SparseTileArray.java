package csse2002.block.world;

import java.util.*;

public class SparseTileArray {

    private List<Tile> sparseTileArray;

    private Map<Position, Tile> sparseTileMap;

    public SparseTileArray(){
        sparseTileArray = new ArrayList<>();
        sparseTileMap = new TreeMap<>();
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

        Position currPos = new Position(startingX, startingY);
        sparseTileMap.put(currPos, startingTile);
        //Now perform breadth-first search

        Queue<Position> nodesToVisit = new LinkedList<>();
        Set<Position> alreadyVisited = new HashSet<>();
        nodesToVisit.add(currPos);

        while(nodesToVisit.size()!=0){

            currPos = nodesToVisit.remove();
            Tile tileAtPos = sparseTileMap.get(currPos);
            //Check the setup
            if(!alreadyVisited.contains(currPos)){
                //First visit
                alreadyVisited.add(currPos);
                System.out.println(currPos.toString());
                Map<String, Tile> exits = tileAtPos.getExits();

                int currPosX = currPos.getX();
                int currPosY = currPos.getY();
                if(exits.containsKey("north")){
                    Tile tempTile = exits.get("north");
                    Position newPos = new Position(currPosX, currPosY+1);
                    nodesToVisit.add(newPos);
                    sparseTileMap.put(newPos, tempTile);
                }

                if(exits.containsKey("south")){
                    Tile tempTile = exits.get("south");
                    Position newPos = new Position(currPosX, currPosY-1);
                    nodesToVisit.add(newPos);
                    sparseTileMap.put(newPos, tempTile);

                }

                if(exits.containsKey("east")){
                    Tile tempTile = exits.get("east");
                    Position newPos = new Position(currPosX+1, currPosY);
                    nodesToVisit.add(newPos);
                    sparseTileMap.put(newPos, tempTile);

                }

                if(exits.containsKey("west")){
                    Tile tempTile = exits.get("west");
                    Position newPos = new Position(currPosX-1, currPosY);
                    nodesToVisit.add(newPos);
                    sparseTileMap.put(newPos, tempTile);

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
