package src.ija2016.model.cards;

public class TargetPack extends CardStackC {
    private int top_card;
    private Card.Color color;

    public TargetPack(Card.Color color) {
        super(13);
        this.color = color;
        this.top_card = 1;
        this.deck = new Card[13];
    }

    @Override
    public boolean put(Card c) {
        if((c.value() == top_card) && (c.color().similarColorTo(this.color))){
            this.top_card++;
            this.top++;
            deck[this.top] = c;
            return true;
        }
        return false;
    }
}
