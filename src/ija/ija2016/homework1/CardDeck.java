package ija.ija2016.homework1;
import ija.ija2016.homework2.model.cards.Card;
public class	CardDeck implements ija.ija2016.homework2.model.cards.CardDeck
{
	protected int size;	
	protected Card[] stack;
	protected int top;
      Card.Color color;

	public CardDeck(int size)
	{
		this.stack = new Card[size];
		this.size = size;
		this.top = -1;
	}
	public CardDeck(int size, Card.Color color)
	{
		this.stack = new Card[size];
		this.size = size;
		this.top = -1;
    this.color = color;
	}
	public static CardDeck createStandardDeck()
	{
		CardDeck newDeck = new CardDeck(52);
		CardDeck.createColorDeck(Card.Color.CLUBS, newDeck);
		CardDeck.createColorDeck(Card.Color.DIAMONDS, newDeck);
		CardDeck.createColorDeck(Card.Color.HEARTS, newDeck);
		CardDeck.createColorDeck(Card.Color.SPADES, newDeck);
		return newDeck;
	}
	public static CardDeck createColorDeck(Card.Color col, CardDeck newDeck){
		for(int i = 1; i <= 13; i++)
		{
			newDeck.putCard(new ija.ija2016.homework1.Card(col, i));
		}
		return newDeck;
	}
	public Card get(int index)
	{
			if(index > this.top || this.isEmpty())
				return null;
			return this.stack[index];
	}
	public Card get()
	{
		if(this.isEmpty())
			return null;
		return this.stack[this.top];
	}
	public boolean isEmpty()
	{
		return (this.top == -1);
	}
	public Card pop()
	{
		return this.stack[this.top--];
	}
	public boolean putCard(Card card)
	{
		return (this.stack[++this.top] = card) != null;
	}
	public boolean put(Card card)
	{
    if(this.color != null && this.color != card.color())
      return false;
    if((this.top == -1) && (card.value() != 1))
      return false;
    if(this.top >= 0 && this.color == null)
    {
      if(this.stack[this.top].value() != (card.value() + 1))
        return false;
      if(this.stack[this.top].similarColorTo(card))
        return false;
    }
    else if(this.top >= 0)
    {
      if(this.stack[this.top].value() != (card.value() - 1))
        return false;
    }
    return (this.stack[++this.top] = card) != null;
	}
	public int size(){return this.top + 1;}
}
