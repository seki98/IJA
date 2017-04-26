package ija.ija2016.homework2.model.board;

import ija.ija2016.homework1.CardStack;
import ija.ija2016.homework1.CardDeck;
import ija.ija2016.homework1.Card;
public class FactoryKlondike implements AbstractFactorySolitaire
{

    public CardDeck createCardDeck()
    {
        CardDeck cd = CardDeck.createStandardDeck();
        return cd;
    }

    public Card createCard(Card.Color color, int value)
    {
        if(value <= 0 || value >= 14)
            return null;

        Card card = new Card(color, value);
        return card;
    }

    public CardDeck createTargetPack(Card.Color color)
    {
        CardDeck targetPack = new CardDeck(100, color);
        return targetPack;
    }

    public CardStack createWorkingPack()
    {
        CardStack cs = new CardStack(100);
        return cs;
    }
}
