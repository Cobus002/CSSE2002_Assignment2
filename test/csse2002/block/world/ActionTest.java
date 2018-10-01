package csse2002.block.world;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class ActionTest {

    //Constant directions used in the test file
    private static final String NORTH = "north";
    private static final String EAST = "east";
    private static final String SOUTH = "south";
    private static final String WEST = "west";
    //Test actions text file used to test load actions
    private static final String ACTIONS_TXT = "actions.in";

    /**
     * Test that an Action is loaded correctly and that the assigned primary
     * and secondary cations match that given.
     */
    @Test
    public void loadAction() {
        //Create the test actions
        Action testAction1 = new Action(Action.MOVE_BLOCK, NORTH);
        Action testAction2 = new Action(Action.DROP, "0");
        //Check the first test
        assertEquals(Action.MOVE_BLOCK, testAction1.getPrimaryAction());
        assertEquals(NORTH, testAction1.getSecondaryAction());
        //Check the second test
        assertEquals(Action.DROP, testAction2.getPrimaryAction());
        assertEquals("0", testAction2.getSecondaryAction());
    }

    /**
     * Test the invalid last line of the file actions.in throws
     * ActionFormatException. However rest of file should work
     *
     * @throws ActionFormatException
     */
    @Test(expected = ActionFormatException.class)
    public void loadActionFormatException1() throws ActionFormatException {
        //Create the test actions
        BufferedReader actionsBuff = null;
        try {
            actionsBuff = new BufferedReader(new FileReader(ACTIONS_TXT));
        } catch (Exception e) {
            //File not found or something
        }

        Action testAction;
        while ((testAction = Action.loadAction(actionsBuff)) != null) {

            switch (testAction.getPrimaryAction()) {
                case Action.DIG:
                    System.out.println("DIG");
                    break;
                case Action.DROP:
                    System.out.println("DROP");
                    break;
                case Action.MOVE_BLOCK:
                    System.out.print("MOVE_BLOCK" +
                            testAction.getSecondaryAction());
                    break;
                case Action.MOVE_BUILDER:
                    System.out.println("MOVE_BUILDER" +
                            testAction.getSecondaryAction());
                    break;
            }

        }

    }

    @Test(expected = ActionFormatException.class)
    public void preocessActions() throws ActionFormatException {
        WorldMap testMap = null;
        BufferedReader actionBuff = null;

        try {
            testMap = new WorldMap("foo.in");
            actionBuff = new BufferedReader(new FileReader("actions.in"));
        } catch (Exception e) {
            System.out.println("Err");
        }
        //Process the loaded actions on the world
        Action.processActions(actionBuff, testMap);


    }


}