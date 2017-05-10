package src.commands;
import src.ija2016.model.cards.WorkingPack;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.CardStack;

/**
 * Take stack(beginning with "card") from sourceStack(workingStack) and place it
 * on top of the targetStack(workingStack)
 */
public class PutStackCommand implements UndoCommand{
  protected WorkingPack sourceStack;
  protected WorkingPack targetStack;
  protected Card card;
  protected boolean srcwashidden;
  protected boolean tarwashidden;


  public PutStackCommand(WorkingPack sourceStack, WorkingPack targetStack, Card card)
  {
    this.sourceStack = sourceStack;
    this.targetStack = targetStack;
    this.tarwashidden = true;
    this.srcwashidden = true;
    this.card = card;
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

  public boolean execute()
  {
      if(sourceStack == targetStack)
          return false;
      //System.out.println("SRC: "+sourceStack.packnum + " TAR: "+targetStack.packnum);
      if(!card.isTurnedFaceUp())
          return false;
      CardStack cs = sourceStack.pop(card);
      if(cs == null) {
          return false;
      }

      if(!targetStack.put(cs)) {
          int i = 0;
          while(true)
          {

              if(cs.get(i) == null)
                  break;
              sourceStack.forcePut(cs.get(i));
              i++;
          }
          //if(!sourceStack.put(cs))
             //System.out.println("Tu je ryza");
          return false;
      }
    if(sourceStack.size() >= 1)
        sourceStack.get().turnFaceUp();
    return true;
  }
  
  public void undo()
  {
      Card c = sourceStack.get();
      //c.turnFaceDown();
    CardStack cs = targetStack.pop(card);
    int i = 0;
    while(true)
    {

        if(cs.get(i) == null)
            break;
        sourceStack.forcePut(cs.get(i));
        i++;
    }
  }

}
