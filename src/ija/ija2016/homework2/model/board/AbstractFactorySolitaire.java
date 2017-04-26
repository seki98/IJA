package ija.ija2016.homework2.model.board;

import ija.ija2016.homework1.CardDeck;
import ija.ija2016.homework1.CardStack;
import ija.ija2016.homework1.Card;
public abstract interface AbstractFactorySolitaire {
    public abstract CardDeck createCardDeck();

    public abstract Card createCard(Card.Color color, int value);

    public abstract CardDeck createTargetPack(Card.Color color);

    public abstract CardStack createWorkingPack();
}


