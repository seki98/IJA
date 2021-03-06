package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.CardDeck;

public class TurnCardPullStackCommand implements UndoCommand{
  /*
   * Take (not visible) card from top of the PullStack,
   * set it visible and place on top of the trashStack
   */
  protected CardDeck pullStack;
  protected CardDeck trashStack;
  public TurnCardPullStackCommand(CardDeck pullStack,CardDeck trashStack)
  {
    this.pullStack = pullStack;
    this.trashStack = trashStack;
  }

  public boolean execute()
  {
    //turn packs if pullstack is empty
    if(pullStack.get() == null) {
      if (trashStack.get() != null)
        while (true) {
          if (trashStack.get() == null)
            break;
          pullStack.put(trashStack.pop());
          pullStack.get().turnFaceDown();

        }
    }
    else{
      Card c = pullStack.pop();
      c.turnFaceUp();
      //no cards to be drawn, pull stack exhausted
      if(!trashStack.put(c))
        return false;
    }
    return true;
  }

  public boolean hint()
  {
    if(trashStack.put(pullStack.get()))
        {
          trashStack.pop();
          return true;
        }
    return false;
  }
  public void undo()
  //TODO
  {
    if (trashStack.get() == null)
      while (true) {
        if (pullStack.get() == null)
          return;
        trashStack.put(pullStack.pop());
        trashStack.get().turnFaceUp();
      }
    Card c = trashStack.pop();
    pullStack.put(c);
    c.turnFaceDown();
  }

}
