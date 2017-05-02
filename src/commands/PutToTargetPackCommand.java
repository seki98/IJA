package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.TargetPack;
import src.ija2016.model.cards.CardDeck;

public class PutToTargetPackCommand implements UndoCommand{
  protected TargetPack targetStack;
  protected CardDeck sourceStack;
  public PutToTargetPackCommand(CardDeck sourceStack,TargetPack targetStack)
  {
    this.sourceStack = sourceStack;
    this.targetStack = targetStack;
  }

  public boolean execute()
  {
    if(!targetStack.put(sourceStack.pop()))
      return false;
    return true;
  }
  public boolean hint()
  {
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
