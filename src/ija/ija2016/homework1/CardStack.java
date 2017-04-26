package ija.ija2016.homework1;
import ija.ija2016.homework2.model.cards.Card;

public class CardStack extends CardDeck implements ija.ija2016.homework2.model.cards.CardStack
{

  public CardStack(int size)
  {
    super(size);
  }


	public boolean put(ija.ija2016.homework2.model.cards.CardStack stack)
	{
	    if(stack.isEmpty())
	    	return false;
		if(this.top == -1 && stack.get().value() != 13)
			return false;
		
    for(int i = 0; i <= (stack.size()-1); i++)
		{
			this.stack[++this.top] = stack.get(i);
		}
		return true;
	}


	public CardStack pop(Card c)
	{
		return this.takeFrom(c);
	}



	public boolean put(Card card) {
		if (this.isEmpty() && card.value() != 13)
			return false;

		if (!this.isEmpty()) {
			if (this.get().value() != (card.value() + 1))
				return false;
			if (this.stack[this.top].similarColorTo(card))
				return false;
		}

		return this.putCard(card);
	}

	public CardStack takeFrom(Card card)
	{
		int bot = -1;
		//find where to begin cutting stack
		for(int i = 0; i < this.top; i++)
		{
			if( this.stack[i].equals(card) )
			{
				bot = i;
				break;
			}
		}
		if(bot < 0)
			throw new ClassCastException();

		//create copy of the stack
		int numOfCards = this.top - bot + 1;
		CardStack newStack = new CardStack(numOfCards);
		for(int i = bot; i <= this.top; i++)
		{
			newStack.putCard(this.stack[i]);
		}

		for(int i = 0; i < numOfCards; i++)
		{
			this.pop();
		}
		
		return newStack;
	}

	@Override
	public boolean equals(Object obj)
	{
		CardStack cs = (CardStack)obj;
		//if(this.size != cs.size)
			//return false;
		if(this.top != cs.top)
			return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		return this.size;
	}

}

