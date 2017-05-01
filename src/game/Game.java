package game;
import java.util.Random;
import ija2016.model.board.AbstractFactorySolitaire;
import ija2016.model.board.FactoryKlondike;
import ija2016.model.cards.Card;
import ija2016.model.cards.CardDeck;
import ija2016.model.cards.CardStack;
import ija2016.model.cards.TargetPack;
import ija2016.model.cards.WorkingPack;
import commands.CommandManager;

public class Game{
    CardDeck targetPack[] = new CardDeck[4];
    CardStack workingPack[] = new CardStack[7];
    CardDeck pullPack;
    CardDeck trashPack;
    CommandManager cmdManager;

    CardDeck factoryDeck;
   protected AbstractFactorySolitaire factory;

   public Game()
   {
       factory = new FactoryKlondike();
       CommandManager cmdManager = new CommandManager();

       factoryDeck = factory.createCardDeck();
       pullPack = factory.createPullDeck();
       trashPack = factory.createPullDeck();

       int i = 0;
       for (Card.Color curr_col : Card.Color.values()) {
           targetPack[i++] = factory.createTargetPack(curr_col);
       }

       for (i = 0; i <= 6; i++)
       {
           workingPack[i] = factory.createWorkingPack();
       }

       spreadCardsToWorkingPack();
       spreadCardsToPullPack();
       //showStacks();


   }

  public void spreadCardsToWorkingPack()
  {
      Random randomGenerator = new Random();
      int count = 1;
      int rand = 0;
      //cp = current pack(index)
      //count - number of cards that should be pushed to stack
      //rand - randomly chosen index for random card
      for (int cp = 0; cp < 7; cp++)
      {
          for(int y = 0; y < count; y++)
          {
              while(true)
              {
                  rand = randomGenerator.nextInt(53);
                  if(factoryDeck.get(rand) != null)
                      break;
              }

              Card c = factoryDeck.get(rand);
              workingPack[cp].forcePut(factory.createCard(c.color(), c.value()));
              if((1 + y) == count) {
                  workingPack[cp].get().turnFaceUp();
              }
              factoryDeck.NullIndex(rand);
          }
          count++;
      }
  }

  public void spreadCardsToPullPack()
  {
      for(int i = 0; i <= 52; i++)
      {
         Card c = factoryDeck.get(i);
          if(c != null) {
              c.turnFaceDown();
              pullPack.forcePut(factory.createCard(c.color(), c.value()));

          }

      }
  }

  public void showStack (CardDeck stack)
  {
      System.out.println("-----------------");
      int i = 0;
      while(true)
      {
          Card c = stack.get(i);
          if(c != null)
              System.out.println(c.toString());
          else {
              break;
          }
          i++;
      }
  }
  public void showStacks()
  {
      System.out.println("WORKING PACKS");
      for(int i = 0; i <= 6; i++)
      {
         showStack(workingPack[i] );
      }
      System.out.println("***END WORKING PACKS");
      System.out.println("TARGET PACKS");
      for(int i = 0; i <= 3; i++)
      {
          showStack(targetPack[i] );
      }
      System.out.println("***END TARGET PACKS");
      System.out.println("PULL PACK");
      showStack(pullPack);
      System.out.println("***END PULL PACK");
      System.out.println("TRASH PACK");
      showStack(trashPack);
      System.out.println("***END TRASH PACK");
  }



}