package src.ija2016.model.cards;

public interface CardStack extends CardDeck{
    boolean put(CardStack stack);
    CardStack pop(Card card);
}
