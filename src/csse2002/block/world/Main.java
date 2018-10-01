package csse2002.block.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Main {
    //constants used for the available actions
    private static final String SYS_IN_STR = "System.in";
    private static String ERR_USAGE_MSG = "Usage: program inputMap actions " +
            "outputMap";
    private static final int ERR_CODE_INPUT = 1;
    private static final int ERR_CODE_LOAD_MAP_EXCEPT = 2;
    private static final int ERR_CODE_ACTIONS_EXCEPT = 3;
    private static final int ERR_CODE_PLAY_EXCEPT = 4;
    private static final int ERR_CODE_SAVE_GAME = 5;



    /**
     * Main() function is the entry point of the application and takes 3
     * arguments, input map file args[0] actions file args[1] and an output
     * map file args[2].
     * @param args
     */
    public static void main(String args[]){
        //Check the inputs
        if(args.length!=3){
            System.out.println(ERR_USAGE_MSG);
            System.exit(ERR_CODE_INPUT);
        }
        WorldMap myWorldMap=null;
        try{
            myWorldMap = new WorldMap(args[0]);

        }catch (Exception e){
            System.err.println(e);
            System.exit(ERR_CODE_LOAD_MAP_EXCEPT);
        }
        BufferedReader buffReaderActions=null;
        //Try to load all the readers
        try {
            if (args[1].equals(SYS_IN_STR)) {
                //use the System.in as the actions input
                buffReaderActions = new BufferedReader(new InputStreamReader(System.in));
            } else {
                buffReaderActions = new BufferedReader(new FileReader(args[1]));
            }
        }catch (Exception e ){
            System.err.println(e);
            System.exit(ERR_CODE_ACTIONS_EXCEPT);
        }
        //No errors so far... Let's Play =)
        try {
            Action.processActions(buffReaderActions, myWorldMap);
        } catch(ActionFormatException e){
            System.err.println(e);
            System.exit(ERR_CODE_PLAY_EXCEPT);
        }
        //Done playing =( .... Come back soon
        try{
            myWorldMap.saveMap(args[2]);
        } catch (Exception e){
            System.err.println(e);
            System.exit(ERR_CODE_SAVE_GAME);
        }


    }
}
