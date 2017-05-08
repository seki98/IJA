package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.CardDeck;
import src.ija2016.model.cards.CardStack;

  /**
   * Pull card from top of the Trash pack and place it on some working pack
   */
public class PullCardCommand implements UndoCommand{
  protected WorkingPack workingPack;
  protected CardDeck pullStack;
  protected CardDeck trashStack;
  public PullCardCommand(WorkingPack workingPack, CardDeck trashStack)
  {
    this.workingPack = (WorkingPack)workingPack;
    this.trashStack = trashStack;
    this.pullStack = pullStack;
  }

  public boolean execute()
  {
    if(trashStack.get() == null)
      return false;
//    System.out.println(trashStack.get());
    if(!workingPack.put(trashStack.get()))
      return false;
    trashStack.pop();
    return true;
  }

  public boolean hint()
  {
    if(trashStack.get() == null)
      return false;
    if(workingPack.put(trashStack.get()))
    {
      workingPack.pop();
      return true;
    }
    return false;
  }

  public void undo()
  {
    trashStack.put(workingPack.pop());
  }

}
