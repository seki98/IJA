package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.CardStack;

public class PutStackCommand implements UndoCommand{
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
      //System.out.println(targetStack.size());
    if(targetStack.put(card))
    {
        //System.out.println(targetStack.size());
        targetStack.pop();
        //System.out.println(targetStack.size());
        System.out.println("YES");
        return true;
    }
    return false;
  }

  public boolean execute()
  {
      CardStack cs = sourceStack.pop(card);
      if(cs == null)
          return false;
      if(!targetStack.put(cs))
          return false;
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
