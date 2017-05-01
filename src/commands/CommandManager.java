package commands;
import commands.Command;
import commands.UndoCommand;
import java.util.Stack;
import java.util.*;

public class CommandManager
{
    private Stack commandStack = new Stack();

    public void executeCommand(Command cmd)
    {
        cmd.execute();
        if (cmd instanceof UndoCommand)
        {
            commandStack.push(cmd);
        }
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
