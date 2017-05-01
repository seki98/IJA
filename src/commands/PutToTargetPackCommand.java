package commands;
import ija2016.model.cards.WorkingPack;
import ija2016.model.cards.Card;
import ija2016.model.cards.TargetPack;
import ija2016.model.cards.CardDeck;

public class PutToTargetPackCommand implements UndoCommand{
  protected TargetPack targetStack;
  protected CardDeck sourceStack;
  public PutToTargetPackCommand(TargetPack targetStack, CardDeck sourceStack)
  {
    this.sourceStack = sourceStack;
    this.targetStack = targetStack;
    System.out.println("PutCommand created");
  }

  public void execute()
  {
    targetStack.put(sourceStack.pop());
  }

  public void undo()
  {
    sourceStack.put(targetStack.pop());
  }

}
