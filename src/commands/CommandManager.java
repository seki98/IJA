package src.commands;
import src.commands.Command;
import src.commands.UndoCommand;
import java.util.Stack;
import java.util.*;

public class CommandManager
{
    private Stack commandStack = new Stack();

    public boolean executeCommand(Command cmd)
    {
        System.out.println("do something");
        if(cmd.execute())
        {
            if (cmd instanceof UndoCommand)
            {
                commandStack.push(cmd);
            }
            return true;
        }

        System.out.println("CANT EXECUTE, BACKEND PROBLEM");
        return false;
    }

    public boolean hint(Command cmd) {
        if (cmd.hint())
            return true;
        return false;
    }


    public void undo()
    {
        if (!commandStack.empty())
        {
            UndoCommand cmd = (UndoCommand)commandStack.pop();
            cmd.undo();
        }
    }
}
