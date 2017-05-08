package src.commands;
import src.commands.Command;
import src.commands.UndoCommand;
import java.util.Stack;
import java.util.*;

/**
 * Command manager representing a stack and a supervisor of the game history
 */
public class CommandManager
{
    private Stack commandStack = new Stack();

    /**
     *  this function executes the command
     * @param cmd - command to be executed and put to stack
     * @return - true if success
     */
    public boolean executeCommand(Command cmd)
    {
        //System.out.println("do something");
        if(cmd.execute())
        {
            if (cmd instanceof UndoCommand)
            {
                commandStack.push(cmd);
            }
            return true;
        }

        //System.out.println("CANT EXECUTE, BACKEND PROBLEM");
        return false;
    }

    /**
     * This function tries to to the same as executeCommand but is undone immediately, just to get info if it is possible
     * @param cmd - command to be done
     * @return - true if success
     */
    public boolean hint(Command cmd) {
        if (cmd.hint())
            return true;
        return false;
    }


    /**
     * This function does neccessary steps to go 1 step in the history
     */
    public void undo()
    {
        if (!commandStack.empty())
        {
            UndoCommand cmd = (UndoCommand)commandStack.pop();
            cmd.undo();
        }
    }
}
