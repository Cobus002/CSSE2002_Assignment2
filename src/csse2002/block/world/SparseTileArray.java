package csse2002.block.world;

import java.util.ArrayList;
import java.util.List;

public class SparseTileArray {

    private List<Tile> sparseTileArray;

    public SparseTileArray(){
        sparseTileArray = new ArrayList<>();
    }

    public Tile getTile(Position position){
        //Todo implement function

        return new Tile();
    }


    public List<Tile> getTiles(){
        //Todo implement function
        return this.sparseTileArray;
    }
}
