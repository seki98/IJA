package src.ija2016.model.cards;

public class TargetPack extends CardStackC {
    private Card.Color color;
    protected int top_card;

    public TargetPack(Card.Color color) {
        super(13);
        this.color = color;
    }

    public Card.Color color()
    {
        return this.color;
    }

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
