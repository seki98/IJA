package src.commands;
import src.commands.Command;

/**
 * Interface for commands that can be undone
 */
public interface UndoCommand extends Command{
  public void undo();
}
