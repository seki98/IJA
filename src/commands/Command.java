package src.commands;

/**
 * inteface for commands that do not have history
 */
public interface Command
{
  public boolean execute();
  public boolean hint();
}
