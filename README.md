# IJA
VUT FIT IJA 2016/2017

src/ija2016... Basic classes

src/game
- Game - game object, contains all stacks, CommandManager(undo/redo history)
- Main - contains main function. Wraps the whole program. Runs GUI
- GUISwing - unused

src/commands - classes needed for undo/redo history. These classes wrap all stack+cards manipulation.
# GAME methods
- ```game.cmdManager.execute(new SomeCommand(param1, param2))```
    to execute a command
- ```game.cmdManager.undo()```
    to undo a command
- ```game.saveGame(String name)```
    to save a game to ./saves/
- ```game.loadGame(String name)```
    to load a game from ./saves/
- ```game.showStacks()```
    to print all stacks to the CLI
- ```game.showHint(CardDeck src, CardDeck tar, Card c)```
    to receive Hint() object with hint for the next move.

Hint contains these attributes

- CardDeck src = stack from which a card is to be taken
- CardDeck tar = stack to which a card is to be put
- Card c = card which is to be selected and moved from src to tar

# COMMANDS
- public TurnCardPullStackCommand(CardDeck pullStack,CardDeck trashStack)
pullStack = Stack from which cards are drawn when no move is possible
trashStack = Stack where card is placed

- public PutStackCommand(WorkingPack sourceStack, WorkingPack targetStack, Card card)
sourceStack = Stack from which stack is moved to 'targetStack'. Only cards from "card" up to top are moved
targetStack = Stack where the mentioned stack is put
card = explained above

- public PullCardCommand(WorkingPack workingPack, CardDeck trashStack)
workingPack = stack to which the  card from trashStack will be placed
trashStack = top card from here is used

- public PutToTargetPackCommand(CardDeck sourceStack,TargetPack targetStack)
sourceStack = the stack from which top card is moved to targetStack
targetStack = one of the targetStacks

run 'ant run' in the directory where build.xml is located. Gui from src/game/Main.java is loaded.
