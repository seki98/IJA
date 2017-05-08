package src.ija2016.model.cards;

/**
 * This class represents 7 working stacks which may contain from 0 up to 20 cards
 */
public class WorkingPack extends CardStackC {
    private int top_card;
    public int packnum;
    private Card.Color top_color;

    public WorkingPack(int packnum) {
        super(52);
        this.packnum = packnum;
    }

    @Override
    public boolean put(Card c) {
        if(this.size() == 0)
        {
            this.top++;
            deck[this.top] = c;
            return true;
        }
        if ((!c.color().similarColorTo(deck[this.top].color()))
                      && (c.value() == deck[this.top].value() - 1)) {

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
