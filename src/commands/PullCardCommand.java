package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.CardDeck;
import src.ija2016.model.cards.CardStack;

public class PullCardCommand implements UndoCommand{
  /*
   * Pull card from top of the Trash pack and place it on some working pack
   */
  protected CardStack workingPack;
  protected CardDeck pullPack;
  public PullCardCommand(CardStack workingPack, CardDeck pullStack)
  {
    this.workingPack = workingPack;
    this.pullPack = pullStack;
  }

  public boolean execute()
  {
    if(!workingPack.put(pullPack.pop()))
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
      pullPack.put(workingPack.pop());
  }

}
