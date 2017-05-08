package src.game;
import src.ija2016.model.cards.CardDeck;
import src.ija2016.model.cards.Card;

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
