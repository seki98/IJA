package src.game;
import src.ija2016.model.cards.CardDeck;
import src.ija2016.model.cards.Card;

/**
 * This class represents Hints which are given to the player.
 * @param src - the index of the source stack from the beginning
 * @param tar - the index of the target stack from the beginning
 * @param cmd - command to be done
 * @param card - card that is to be mvoed
 */
public class Hint
{
    int src;
    int tar;
    Card c;
    int cmd;
    public Hint(int src, int tar, Card c, int cmd)
    {
        this.src = src;
        this.tar = tar;
        this.c   = c;
        this.cmd = cmd;
    }
}
