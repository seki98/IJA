package src.game;
import src.ija2016.model.cards.CardDeck;
import src.ija2016.model.cards.Card;

public class Hint
{
    CardDeck src;
    CardDeck tar;
    Card c;
    public Hint(CardDeck src, CardDeck tar, Card c)
    {
        this.src = src;
        this.tar = tar;
        this.c   = c;
    }
}
