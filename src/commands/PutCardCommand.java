package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;

public class PutCardCommand implements UndoCommand{
  protected WorkingPack sourceStack;
  protected WorkingPack targetStack;
  protected Card card;
  public PutCardCommand(WorkingPack sourceStack, WorkingPack targetStack, Card card)
  {
    this.sourceStack = sourceStack;
    this.targetStack = targetStack;
    this.card = card;
    System.out.println("PutCommand created");
  }

  public void execute()
  {
    targetStack.put(card);
    Card c = sourceStack.get();
    c.turnFaceUp();
  }
  
  public void undo()
  {
      Card c = sourceStack.get();
      c.turnFaceDown();
    sourceStack.put(card);
  }

}
