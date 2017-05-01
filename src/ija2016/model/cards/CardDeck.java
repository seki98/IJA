package ija2016.model.cards;

public interface CardDeck {

    Card get();
    Card get(int idx);
    boolean isEmpty();
    Card pop();
    boolean put(Card c);
    int size();
    boolean NullIndex(int index);
    boolean forcePut(Card c);
}
