package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.CardStack;

public class PutStackCommand implements UndoCommand{
  /*
   * Take stack(beginning with "card") from sourceStack(workingStack) and place it
   * on top of the targetStack(workingStack)
   */
  protected WorkingPack sourceStack;
  protected WorkingPack targetStack;
  protected Card card;

  public PutStackCommand(WorkingPack sourceStack, WorkingPack targetStack, Card card)
  {
    this.sourceStack = sourceStack;
    this.targetStack = targetStack;
    this.card = card;
  }

  public boolean hint()
  {
    if(targetStack.put(card))
    {
        targetStack.pop();
        return true;
    }
    return false;
  }

  public boolean execute()
  {
      if(!card.isTurnedFaceUp())
          return false;
      CardStack cs = sourceStack.pop(card);
      if(cs == null)
          return false;

      if(!targetStack.put(cs)) {
          sourceStack.put(cs);
          return false;
      }
    if(sourceStack.size() >= 1)
        sourceStack.get().turnFaceUp();
    return true;
  }
  
  public void undo()
  {
      Card c = sourceStack.get();
      c.turnFaceDown();
    sourceStack.put(targetStack.pop(card));
  }

}
