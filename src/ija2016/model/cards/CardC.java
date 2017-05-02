package src.ija2016.model.cards;

public class CardC implements Card {
    private Color c;
    private int value;
    private boolean faceup;

    public CardC(Color c, int value) {
        this.c = c;
        this.value = value;
        this.faceup = false;
    }

    public int compareValue(CardC c){
        return this.value - c.value();
    }

    public boolean isTurnedFaceUp(){
        return this.faceup;
    }

    public CardC.Color color() {
        return this.c;
    }

    public boolean turnFaceUp(){
        if(!this.faceup){
            this.faceup = true;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean turnFaceDown(){
        if(this.faceup){
            this.faceup = false;
            return true;
        }
        else {
            return false;
        }
    }
    public boolean similarColorTo(Card card) {
        return card.color().similarColorTo(this.c);
    }

    public String toString(){
        String out;
        switch(this.value) {
            case 1:     out="A";
                break;
            case 11:    out="J";
                break;
            case 12:    out="Q";
                break;
            case 13:    out="K";
                break;
            default:    out=""+this.value;
        }
        return out+"("+this.c+")";
    }

    public int value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardC card = (CardC) o;

        if (value != card.value) return false;
        return c == card.c;
    }

    @Override
    public int hashCode() {
        int result = c != null ? c.hashCode() : 0;
        result = 31 * result + value;
        return result;
    }

    @Override
    public int compareValue(Card c) {
        return (this.value - c.value());
    }
}
