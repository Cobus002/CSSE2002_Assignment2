package csse2002.block.world;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.*;

public class WorldMapTest {

    @Test
    public void testInitialisation() throws WorldMapInconsistentException,
            WorldMapFormatException, FileNotFoundException {
        String mapsPath = "sample_maps";
        File mapDir = new File(mapsPath);
        File dirListings[] = mapDir.listFiles();
        WorldMap tempMap;
        for (File file : dirListings) {
            try {
                tempMap = new WorldMap(mapsPath + "/" + file.getName());
                System.out.println("Success: " + file.getName());
            } catch (Exception e) {

                System.err.println(file.getName() + ": " + e);
            }
        }

        /*
        WorldMap testMap = new WorldMap("sample_maps/invalidTileIds.txt");
        try {
            testMap.saveMap("hello.out");

        } catch (IOException e) {
            System.err.print(e);

        }*/
    }

}