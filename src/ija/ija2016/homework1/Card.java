package ija.ija2016.homework1;

public class Card implements ija.ija2016.homework2.model.cards.Card{

	public Color color;
	public int value;
	private boolean facingUp;

	public Card(Card.Color c, int value)
	{
		this.color = c;
		this.value = value;
		this.facingUp = false;
	}

	public Card.Color color()
	{
		return this.color;
	}

	public boolean isTurnedFaceUp()
	{
		return this.facingUp;
	}

	public boolean turnFaceUp()
	{
		if(this.facingUp)
			return false;
		else
			return this.facingUp = true;
	}

	public boolean similarColorTo(ija.ija2016.homework2.model.cards.Card c)
	{
		if(this.color() == Color.DIAMONDS || this.color() == Color.HEARTS ) {
			if (c.color() == Color.DIAMONDS || c.color() == Color.HEARTS) {
				return true;
			}
		}
        else
		{
			if(c.color() == Color.SPADES || c.color() == Color.CLUBS)
				return true;
		}
		return false;

	}

	public int compareValue(ija.ija2016.homework2.model.cards.Card c)
	{
		return (this.value - c.value());
	}



	public int value()
	{
		return this.value;
	}

	public String toString()
	{
		switch(this.value)
		{
			case(11):
				return "J"+ "(" + this.color.toString() + ")";

			case(12):
				return "Q"+ "(" + this.color.toString() + ")";

			case(13):
				return "K"+ "(" + this.color.toString() + ")";

			case(1):
				return "A"+ "(" + this.color.toString() + ")";
		}
		return this.value + "(" + this.color.toString() + ")";
	}

	@Override
	public boolean equals(Object newC)
	{
		Card card = (Card)newC;
		if(this.color != card.color)
			return false;
		if(this.value != card.value)
			return false;
		return true;
	}

	public int hashCode()
	{
		return this.value;
	}
}

