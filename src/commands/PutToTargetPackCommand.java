package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.TargetPack;
import src.ija2016.model.cards.CardDeck;

public class PutToTargetPackCommand implements UndoCommand{
  /* 
   * Take card from top of the sourceStack(TrashPack/WorkingPack) and place it
   * to the targetPack
   */
  protected TargetPack targetStack;
  protected CardDeck sourceStack;
  public PutToTargetPackCommand(CardDeck sourceStack,TargetPack targetStack)
  {
    this.sourceStack = sourceStack;
    this.targetStack = targetStack;
  }

  public boolean execute()
  {
    if(sourceStack.get() == null)
      return false;
    if(targetStack.put(sourceStack.get()))
    {
      sourceStack.pop();
      if(sourceStack.size() >= 1)
        sourceStack.get().turnFaceUp();
    }
    return true;
  }
  public boolean hint()
  {
    if(sourceStack.get() == null)
      return false;
    if(targetStack.put(sourceStack.get())) {
      targetStack.pop();
      return true;
    }
    return false;
  }

  public void undo()
  {
    sourceStack.put(targetStack.pop());
  }

}
