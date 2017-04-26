package ija.ija2016.homework2.model.cards;

public interface Card{

	public enum Color{
		CLUBS("C"), DIAMONDS("D"), HEARTS("H"), SPADES("S");

		private final String color;
		private Color(final String color)
		{
			this.color = color;
		}
		public String toString()
		{
			return this.color;
		}
	}

	public Card.Color color();
	public boolean isTurnedFaceUp();
	public boolean turnFaceUp();
	public boolean similarColorTo(Card c);
	public int compareValue(Card c);
	public int value();
}

