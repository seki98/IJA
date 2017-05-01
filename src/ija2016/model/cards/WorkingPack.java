package ija2016.model.cards;

public class WorkingPack extends CardStackC {
    private int top_card;
    private Card.Color top_color;

    public WorkingPack() {
        super(13);
        this.top_card = 13;
        this.top_color = null;
    }

    @Override
    public boolean put(Card c) {
        if ((!c.color().similarColorTo(this.top_color)) && (c.value() == this.top_card)) {
            this.top_card--;
            this.top_color = c.color();

            this.top++;
            deck[this.top] = c;
            return true;
        }
        return false;
    }

    public boolean forcePut(Card c)
    {
        this.top++;
        deck[this.top] = c;
        return true;
    }
}
