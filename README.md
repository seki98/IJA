# IJA
VUT FIT IJA 2016/2017

src/ija2016... Basic classes

src/game
- Game - game object, contains all stacks, CommandManager(undo/redo history)
- Main - contains main function. Wraps the whole program. Runs GUI
- GUISwing - unused

src/commands - classes needed for undo/redo history. These classes wrap all stack+cards manipulation.


run 'ant run' in the directory where build.xml is located. Gui from src/game/Main.java is loaded.
