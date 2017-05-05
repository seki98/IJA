package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.CardDeck;
import src.ija2016.model.cards.CardStack;

public class PullCardCommand implements UndoCommand{
  protected CardStack targetStack;
  protected CardDeck pullPack;
  public PullCardCommand(CardStack targetStack, CardDeck pullStack)
  {
    this.targetStack = targetStack;
    this.pullPack = pullStack;
    System.out.println("PullCard created");
  }

  public boolean execute()
  {
    if(!targetStack.put(pullPack.pop()))
      return false;
    pullPack.get().turnFaceUp();
    return true;
  }

  public boolean hint()
  {
    return true;
  }

  public void undo()
  {
    pullPack.get().turnFaceDown();
      pullPack.put(targetStack.pop());
  }

}
