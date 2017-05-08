package src.ija2016.model.cards;

/**
 * This class represents 4 target packs
 */
public class TargetPack extends CardStackC {
    private Card.Color color;
    protected int top_card;

    /**
     * each targetPack has its own color
     * @param color - color of the target pack
     */
    public TargetPack(Card.Color color) {
        super(13);
        this.color = color;
    }

    /**
     * @return - returns color of the targetPack
     */
    public Card.Color color()
    {
        return this.color;
    }

    /**
     * TargetPack only accepts the same color for each card and they must be ordered reversed
     * @param c - card to be put to stack
     * @return - true if success
     */
    @Override
    public boolean put(Card c) {
        if(get() == null)
            top_card = 0;
        else
            top_card = get().value();

        //System.out.println("CVALUE: "+c.value() + " TOPCARD: "+top_card+" colors:"+c.color()+this.color());
        if( (c.value() == (top_card+1)) && (c.color() == this.color) ){
            forcePut(c);
            return true;
        }
        return false;
    }
}
