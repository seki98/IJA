package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.CardDeck;

public class TurnCardPullStackCommand implements UndoCommand{
  protected CardDeck pullStack;
  protected CardDeck trashStack;
  public TurnCardPullStackCommand(CardDeck pullStack,CardDeck trashStack)
  {
    this.pullStack = pullStack;
    this.trashStack = trashStack;
    System.out.println("Turn card created");
  }

  public boolean execute()
  {
      Card c = pullStack.pop();
    if(!trashStack.put(c))
      return false;
    c.turnFaceUp();
    return true;
  }

  public boolean hint()
  {
    return true;
  }
  public void undo()

  {
    Card c = trashStack.pop();
    pullStack.put(c);
    c.turnFaceDown();
  }

}
