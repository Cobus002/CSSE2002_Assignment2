package csse2002.block.world;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class ActionTest {

    @Test
    public void getPrimaryAction() {
    }

    @Test
    public void getSecondaryAction() {
    }

    @Test
    public void loadAction() throws ActionFormatException{

        //open the file with all the
        BufferedReader in;

        try {
            in = new BufferedReader(new FileReader("foo.in"));
        }catch(FileNotFoundException e){
            //File not found
            return;
        }
        //Load the actions
        Action action1 = Action.loadAction(in);
        Action action2 = Action.loadAction(in);
        assertEquals(Action.MOVE_BUILDER, action1.getPrimaryAction());
        assertEquals(Action.MOVE_BLOCK, action2.getPrimaryAction());


    }
}