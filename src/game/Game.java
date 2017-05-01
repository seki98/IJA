package game;
import java.util.Random;
import ija2016.model.board.AbstractFactorySolitaire;
import ija2016.model.board.FactoryKlondike;
import ija2016.model.cards.Card;
import ija2016.model.cards.CardDeck;
import ija2016.model.cards.CardStack;
import ija2016.model.cards.TargetPack;
import ija2016.model.cards.WorkingPack;

public class Game{
    CardDeck targetPack[] = new CardDeck[4];
    CardStack workingPack[] = new CardStack[7];
    CardDeck pullPack;
    CardDeck trashPack;

    CardDeck factoryDeck;
   protected AbstractFactorySolitaire factory;

   public Game()
   {
       factory = new FactoryKlondike();

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
      for (int cp = 0; cp <= 6; cp++)
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
      for(int i = 0; i <= 6; i++)
      {
         showStack(workingPack[i] );
      }
      for(int i = 0; i <= 3; i++)
      {
          showStack(targetPack[i] );
      }
      showStack(pullPack);
  }



}