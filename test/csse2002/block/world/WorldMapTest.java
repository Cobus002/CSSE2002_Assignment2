package csse2002.block.world;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

public class WorldMapTest {

    @Test
    public void testInitialisation() throws WorldMapInconsistentException,
            WorldMapFormatException, FileNotFoundException {
        WorldMap testMap = new WorldMap("foo.in");

        try {
            testMap.saveMap("hello.out");

        }catch(IOException e){
            System.err.print(e);

        }
    }

}