package commands;
import ija2016.model.cards.WorkingPack;
import ija2016.model.cards.Card;
import ija2016.model.cards.CardDeck;

public class TurnCardPullStackCommand implements UndoCommand{
  protected CardDeck pullStack;
  protected CardDeck trashStack;
  public TurnCardPullStackCommand(CardDeck pullStack,CardDeck trashStack)
  {
    this.pullStack = pullStack;
    this.trashStack = trashStack;
    System.out.println("Turn card created");
  }

  public void execute()
  {
      Card c =pullStack.pop();
    trashStack.put(c);
    c.turnFaceUp();
  }
  
  public void undo()
  {
    Card c =trashStack.pop();
    pullStack.put(c);
    c.turnFaceDown();
  }

}
