package commands;
import commands.Command;

public interface UndoCommand extends Command{
  public void undo();
}
