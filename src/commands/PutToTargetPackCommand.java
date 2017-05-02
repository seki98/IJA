package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.TargetPack;
import src.ija2016.model.cards.CardDeck;

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
