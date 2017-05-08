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
        doSomeTurns(5555);

    }

    public void doSomeTurns(int max)
    {

        Hint h;
        for(int y = 0; y < max; y++)
        {
            h = showHint();
            //System.out.println("from " + h.src + " to " + h.tar + " cmd: " + h.cmd);
            if(h.cmd == 1)
                cmdManager.executeCommand(new PullCardCommand(workingPack[h.src-6],trashPack));
            if(h.cmd == 2)
                cmdManager.executeCommand(new PutStackCommand(workingPack[h.src-6], workingPack[h.tar-6], h.c));
            if(h.cmd == 3) {
                if(h.src >= 6 && h.src <=12)
                    cmdManager.executeCommand(new PutToTargetPackCommand(workingPack[h.src-6], targetPack[h.tar-2]));
                else
                    cmdManager.executeCommand(new PutToTargetPackCommand(trashPack, targetPack[h.tar-2]));
            }
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
      int i = 5;
      int y = 1;
      for(WorkingPack src : this.workingPack)
      {
          i++;
          y = 1;
          for(TargetPack tar : this.targetPack) {
              y++;
              if (this.cmdManager.hint(new PutToTargetPackCommand(src, tar))) {
                  //System.out.println("working to target pack:to target src:" + src.size() + " tar:" + tar.size() + "_______" + src.get() + "_________");
                  return new Hint(i, y, src.get(), 3);
              }
          }
      }
      //pull from pullpack and place to targetPack
      i = 0; y = 1;
      for(TargetPack tar : this.targetPack)
      {
          y++;
          CardDeck src = this.trashPack;
          if (this.cmdManager.hint(new PutToTargetPackCommand(src, tar))) {
              //System.out.println("pullpack to targetpack:to target src:" + src.size() + " tar:" + tar.size() + "_______" + src.get() + "_________");
              return new Hint(1, y, src.get(), 3);
          }
      }
        //move stack from working pack ot another working pack
      int sr = 5;
      for(WorkingPack src : this.workingPack)
      {
          sr++;
          for(i = 0; i < src.size(); i++)
          {
             Card c = src.get(i);
             y = 5;
             if(c.isTurnedFaceUp()) {
                 for (WorkingPack tar : this.workingPack) {
                     y++;
                     //System.out.println("tar was:"+tar.size());
                     if (this.cmdManager.hint(new PutStackCommand(src, tar, c))) {
                         //System.out.println(sr + " " + y + "working to working?src:" + src.size() + " tar:" + tar.size() + "_______" + c + "_________");
                         return new Hint(sr, y, c, 2);
                     }
                 }
             }
          }
      }
      //pull from pullpack and place somewhere
      i = 5;
      for(WorkingPack tar : this.workingPack)
      {
          i++;
          if(this.cmdManager.hint(new PullCardCommand(tar, this.trashPack))) {
              //System.out.println("pull to somewhere:src:" + this.trashPack.size() + " tar:" + tar.size() + "_______" + this.trashPack.get() + "_________");
              return new Hint(i, 1, this.trashPack.get(), 1);
          }
      }
      //turn card on pullpack
    //System.out.println("PULL FROM PULL!!!");
    return new Hint(0, 1, null,4);
  }



}
