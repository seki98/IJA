package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.CardDeck;

public class PutCardCommand implements UndoCommand{
  protected CardDeck sourceStack;
  protected WorkingPack targetStack;
  protected Card card;
  public PutCardCommand(CardDeck sourceStack, WorkingPack targetStack, Card card)
  {
    this.sourceStack = sourceStack;
    this.targetStack = targetStack;
    this.card = card;
  }

  public boolean execute()
  {
    if(!targetStack.put(card))
      return false;
    sourceStack.get().turnFaceUp();
    return true;
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
  public void undo()
  {
      Card c = sourceStack.get();
      c.turnFaceDown();
    sourceStack.put(card);
  }

}
