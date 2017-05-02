package src.ija2016.model.cards;
import java.io.*;

public interface Card extends java.io.Serializable{
    public enum Color {
        CLUBS("C"), DIAMONDS("D"), HEARTS("H"), SPADES("S");

        private final String color;

        private Color(String color) {
            this.color = color;
        }

        public String toString() {
            return this.color;
        }

        public boolean similarColorTo(Card.Color c){
            if((this == Color.CLUBS) || (this == Color.SPADES)){
                return ((c==Color.CLUBS) || (c == Color.SPADES));
            }
            else if((this == Color.DIAMONDS) || (this == Color.HEARTS)){
                return ((c == Color.DIAMONDS) || (c == Color.HEARTS));
            }
            else {
                return false;
            }
        }
    }

    int compareValue(Card c);

    boolean isTurnedFaceUp();

    Card.Color color();

    boolean similarColorTo(Card c);

    int value();

    boolean turnFaceUp();
    boolean turnFaceDown();
}