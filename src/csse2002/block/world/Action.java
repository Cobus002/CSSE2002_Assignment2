package csse2002.block.world;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Action {
    //Constants used for actions
    public static final int MOVE_BUILDER = 0;
    public static final int MOVE_BLOCK = 1;
    public static final int DIG = 2;
    public static final int DROP = 3;

    private int primaryAction = -99;
    private String secondaryAction;
    //Valid list of primary and secondary actions
    private static final String MOVE_BUILDER_STR = "MOVE_BUILDER";
    private static final String MOVE_BLOCK_STR = "MOVE_BLOCK";
    private static final String DIG_STR = "DIG";
    private static final String DROP_STR = "DROP";
    private static List<String> PRIMARY_ACTION_LIST = Arrays.asList("MOVE_BUILDER",
            "MOVE_BLOCK", "DIG", "DROP");
    private static List<String> SECONDARY_ACTION_LIST = Arrays.asList("north"
            , "south", "east", "west");
    //Error messages
    private static final String ERR_MSG_NO_EXIT = "No exit this way";
    private static final String ERR_MSG_TOO_HIGH = "Too high";
    private static final String ERR_MSG_TOO_LOW = "Too low";
    private static final String ERR_MSG_INV_BLOCK = "Cannot use that block";
    private static final String ERR_MSG_INV_ACTION = "Error: Invalid action";

    //Action messages
    private static final String DIG_MSG = "Top block on current tile removed";
    private static final String DROP_MSG = "Dropped a block from inventory";
    private static final String MOVE_BLOCK_MSG = "Moved block ";
    private static final String MOVE_BUILDER_MSG = "Moved builder ";



    public Action(int primaryAction, String secondaryAction) {
        this.primaryAction = primaryAction;
        this.secondaryAction = secondaryAction;
    }

    public int getPrimaryAction() {
        return this.primaryAction;
    }

    public String getSecondaryAction() {
        return this.secondaryAction;
    }

    public static Action loadAction(BufferedReader reader) throws
            ActionFormatException {
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            //There was an issue reading the reader
            throw new ActionFormatException();
        }

        if (line == null) {
            //End of file
            return null;
        }
        String actionsText[] = line.split(" ", 2);
        if(actionsText.length>2){
            //there is an error
            throw new ActionFormatException();
        }

        if(!PRIMARY_ACTION_LIST.contains(actionsText[0])){
            //the primary action is not recognised
            throw new ActionFormatException();
        }

        //all checks performed now create the action
        if(actionsText[0].equals(MOVE_BLOCK_STR) ||
                actionsText[0].equals(MOVE_BUILDER_STR) ||
                actionsText[0].equals(DROP_STR)){
            if(actionsText.length!=2){
                //Error no secondary action listed
                throw new ActionFormatException();
            }
            Action action =
                    new Action(PRIMARY_ACTION_LIST.indexOf(actionsText[0]),
                            actionsText[1]);
            return action;
        }else if(actionsText[0].equals(DIG_STR)){
            if(actionsText.length!=1){
                //There should be no secondary action for dig
                throw new ActionFormatException();
            }
            Action action =
                    new Action(PRIMARY_ACTION_LIST.indexOf(actionsText[0]),
                            "");
            return action;

        }else{
            return null;
        }
    }

    /**
     * Perform a given action read in from the buffered reader on the
     * WorldMap given by startingMap.
     * @param reader
     * @param startingMap
     * @throws ActionFormatException
     */
    public static void processActions(BufferedReader reader,
                                      WorldMap startingMap) throws
            ActionFormatException{
        //TODO implement function
    }


    /**
     * Process a given action on a WorldMAp given by map
     * @param action
     * @param map
     */
    public static void processAction(Action action, WorldMap map){
        //TODO implement function
        try {
            Builder myBuilder = map.getBuilder();
            switch (action.getPrimaryAction()) {
                case DIG:
                    myBuilder.digOnCurrentTile();
                    System.out.println(DIG_MSG);
                    break;
                case DROP:
                    int itemIndex =
                            Integer.parseInt(action.getSecondaryAction());
                    myBuilder.dropFromInventory(itemIndex);
                    System.out.println(DROP_MSG);
                    break;
                case MOVE_BLOCK:
                    myBuilder.getCurrentTile().
                            moveBlock(action.secondaryAction);
                    System.out.println(MOVE_BLOCK_MSG);
                    break;
                case MOVE_BUILDER:
                    Tile tileToMoveTo =
                            myBuilder.getCurrentTile().getExits().
                                    get(action.secondaryAction);
                    myBuilder.moveTo(tileToMoveTo);
                    System.out.println(MOVE_BUILDER_MSG+action.secondaryAction);
                    break;
            }
        }catch (Exception e){
            if(e instanceof NoExitException){
                System.out.println(ERR_MSG_NO_EXIT);
            }else if(e instanceof TooHighException){
                System.out.println(ERR_MSG_TOO_HIGH);
            }else if(e instanceof TooLowException){
                System.out.println(ERR_MSG_TOO_LOW);
            }else if(e instanceof InvalidBlockException){
                System.out.println(ERR_MSG_INV_BLOCK);
            }else{
                System.out.println(ERR_MSG_INV_ACTION);

            }
        }

    }
}
