package csse2002.block.world;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Action {
    static int MOVE_BUILDER = 0;
    static int MOVE_BLOCK = 1;
    static int DIG = 2;
    static int DROP = 3;

    private int primaryAction = -99;
    private String secondaryAction;
    //Valid list of actions
    private static List<String> ACTION_LIST = Arrays.asList("MOVE_BUILDER",
            "MOVE_BLOCK", "DIG", "DROP");


    public Action(int primaryAction, String secondaryAction) {
        this.primaryAction = primaryAction;
        if (!(primaryAction == DROP)) {
            this.secondaryAction = secondaryAction;
        }
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
            //TODO: Handle IOException
        }

        if (line == null) {
            //End of file
            return null;
        }

        String[] actionsText = line.split(" ", 2);

        //Check that the primaryAction is valid
        if (ACTION_LIST.contains(actionsText[0])) {
            Action action = new Action(ACTION_LIST.indexOf(actionsText[0]),
                    actionsText[1]);
            return action;
        } else {
            throw new ActionFormatException();

        }

    }


}
