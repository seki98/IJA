package src.game;
import src.game.Hint;
import java.io.*;
import java.util.Random;
import src.ija2016.model.board.AbstractFactorySolitaire;
import src.ija2016.model.board.FactoryKlondike;
import src.ija2016.model.cards.Card;
import src.ija2016.model.cards.CardDeck;
import src.ija2016.model.cards.CardStack;
import src.ija2016.model.cards.TargetPack;
import src.ija2016.model.cards.WorkingPack;
import src.commands.CommandManager;
import src.commands.PutStackCommand;
import src.commands.PutToTargetPackCommand;
import src.commands.PullCardCommand;
import src.commands.TurnCardPullStackCommand;
import java.util.*;
import src.game.Hint;

public class Game implements java.io.Serializable{
    TargetPack targetPack[] = new TargetPack[4];
    WorkingPack workingPack[] = new WorkingPack[7];
    List<Card> randomCards;
    CardDeck pullPack;
    CardDeck trashPack;
    transient CommandManager cmdManager;

    CardDeck factoryDeck;
    transient protected AbstractFactorySolitaire factory;

    public Game() {
       factory = new FactoryKlondike();
       cmdManager = new CommandManager();

       factoryDeck = factory.createCardDeck();
       pullPack = factory.createPullDeck();
       trashPack = factory.createPullDeck();

       int i = 0;
       for (Card.Color curr_col : Card.Color.values()) {
           targetPack[i++] = (TargetPack)factory.createTargetPack(curr_col);
       }

       for (i = 0; i <= 6; i++) {
           workingPack[i] = (WorkingPack)factory.createWorkingPack();
       }

        randomCards = shuffleCards();
        spreadCardsToWorkingPack();
        spreadCardsToPullPack();

        Hint h;
        for(int y = 0; y > 55; y++)
        {
            h = showHint();

            if(h.cmd == 1)
                cmdManager.executeCommand(new PullCardCommand((WorkingPack)h.src, (CardDeck)h.tar));
            if(h.cmd == 2)
                cmdManager.executeCommand(new PutStackCommand((WorkingPack)h.src, (WorkingPack)h.tar, h.c));
            if(h.cmd == 3)
                cmdManager.executeCommand(new PutToTargetPackCommand((CardDeck)h.src, (TargetPack)h.tar));
            if(h.cmd == 4)
                cmdManager.executeCommand(new TurnCardPullStackCommand(this.pullPack, this.trashPack));
        }
    }

   public List<Card> shuffleCards()
   {
       List<Card> randomCards = new ArrayList();
       for (int i = 0; i <= 51; i++) {
           randomCards.add(factoryDeck.get(i));
       }
       Collections.shuffle(randomCards);
       return randomCards;
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
              Card c = randomCards.get(0);
              //System.out.println(c.toString());
              workingPack[cp].forcePut(factory.createCard(c.color(), c.value()));
              if((1 + y) == count) {
                  workingPack[cp].get().turnFaceUp();
              }
              randomCards.remove(c);
          }
          count++;
      }
  }

  public void spreadCardsToPullPack()
  {
      while(true)
      {
          if(randomCards.isEmpty())
              break;
         Card c = randomCards.get(0);
          c.turnFaceDown();
          pullPack.forcePut(factory.createCard(c.color(), c.value()));
          randomCards.remove(c);

      }
  }

  public void showStack (CardDeck stack)
  {
      System.out.println("--------"+stack.size()+"---------");

      for(int i = 0; i < stack.size(); i++)
      {
          Card c = stack.get(i);
          if(c != null)
              System.out.println(c);
          else {
              break;
          }
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

  public void saveGame(String name)
  {

      try
      {
          FileOutputStream fileOut =
                  new FileOutputStream("./saves/" + name);
          ObjectOutputStream out = new ObjectOutputStream(fileOut);
          out.writeObject(this);
          out.close();
          fileOut.close();
          //System.out.printf("Serialized data is saved in /tmp/employee.ser");
      }catch(IOException e) {
          e.printStackTrace();
      }
  }
  public Game loadGame(String name)
  {

      Game loadedGame;
      try {
          FileInputStream fileIn = new FileInputStream("./saves/" + name);
          ObjectInputStream in = new ObjectInputStream(fileIn);
          loadedGame = (Game) in.readObject();
          in.close();
          fileIn.close();
      }catch(IOException i) {
          i.printStackTrace();
          return null;
      }catch(ClassNotFoundException c) {
          System.out.println("Game class not found");
          c.printStackTrace();
          return null;
      }
      loadedGame.cmdManager = new CommandManager();
      return loadedGame;
  }

  public Hint showHint()
  {
        //try to put to target pack
      for(WorkingPack src : this.workingPack)
      {
          for(TargetPack tar : this.targetPack) {
              if (this.cmdManager.hint(new PutToTargetPackCommand(src, tar))) {
                  System.out.println("working to target pack:to target src:" + src.size() + " tar:" + tar.size() + "_______" + src.get() + "_________");
                  return new Hint(src, tar, src.get(), 3);
              }
          }
      }
      //pull from pullpack and place to targetPack
      for(TargetPack tar : this.targetPack)
      {
          CardDeck src = this.trashPack;
          if (this.cmdManager.hint(new PutToTargetPackCommand(src, tar))) {
              System.out.println("pullpack to targetpack:to target src:" + src.size() + " tar:" + tar.size() + "_______" + src.get() + "_________");
              return new Hint(src, tar, src.get(), 3);
          }
      }
        //move stack from working pack ot another working pack
      for(WorkingPack src : this.workingPack)
      {
          for(int i = 0; i < src.size(); i++)
          {
             Card c = src.get(i);
             if(c.isTurnedFaceUp())
             for(WorkingPack tar : this.workingPack)
             {
                 //System.out.println("tar was:"+tar.size());
                 if(this.cmdManager.hint(new PutStackCommand(src, tar, c))) {
                     System.out.println("working to working?src:"+src.size()+" tar:"+tar.size()+"_______"+ c +"_________");
                     return new Hint(src, tar, c, 2);
                 }
             }
          }
      }
      //pull from pullpack and place somewhere
      for(WorkingPack tar : this.workingPack)
      {
          if(this.cmdManager.hint(new PullCardCommand(tar, this.trashPack))) {
              System.out.println("pull to somewhere:src:" + this.trashPack.size() + " tar:" + tar.size() + "_______" + this.trashPack.get() + "_________");
              return new Hint(this.trashPack, tar, this.trashPack.get(), 1);
          }
      }
      //turn card on pullpack
    System.out.println("PULL FROM PULL!!!");
    return new Hint(this.pullPack, this.trashPack, null,4);
  }



}
