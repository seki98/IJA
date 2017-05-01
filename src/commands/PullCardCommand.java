package commands;
import ija2016.model.cards.WorkingPack;
import ija2016.model.cards.Card;
import ija2016.model.cards.CardDeck;

public class PullCardCommand implements UndoCommand{
  protected WorkingPack targetStack;
  protected CardDeck pullPack;
  public PullCardCommand(WorkingPack targetStack, CardDeck pullStack)
  {
    this.targetStack = targetStack;
    this.pullPack = pullStack;
    System.out.println("PullCard created");
  }

  public void execute()
  {
    targetStack.put(pullPack.pop());
    pullPack.get().turnFaceUp();
  }

  public void undo()
  {
    pullPack.get().turnFaceDown();
      pullPack.put(targetStack.pop());
  }

}
