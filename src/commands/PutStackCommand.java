package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;

public class PutStackCommand implements UndoCommand{
  protected WorkingPack sourceStack;
  protected WorkingPack targetStack;
  protected Card card;

  public PutStackCommand(WorkingPack sourceStack, WorkingPack targetStack, Card card)
  {
    this.sourceStack = sourceStack;
    this.targetStack = targetStack;
    this.card = card;
    System.out.println("PutCommand created");
  }

  public boolean execute()
  {
   if(!targetStack.put(sourceStack.pop(card)))
     return false;

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
