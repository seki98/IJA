package ija2016.model.board;

import ija2016.model.cards.*;

public class FactoryKlondike extends AbstractFactorySolitaire {
    /**
     * Vytváří objekt reprezentující balíček karet.
     * @return Balíček karet
     */
    public CardDeck createCardDeck(){
        return CardDeckC.createStandardDeck();
    }

    /**
     * Vytvoří objekt reprezentující kartu.
     * @param color Barva karty
     * @param value Hodnota karty v rozsahu 1 až 13
     * @return Objekt karty. Pokud je některý z parametrů neplatný (objekt nelze vytvořit), vrací null
     */
    public Card createCard(Card.Color color, int value){
        if((color != Card.Color.CLUBS) && (color != Card.Color.DIAMONDS)
            && (color != Card.Color.HEARTS) && (color != Card.Color.SPADES))
            return null;

        if((value < 1) || (value > 13)){
            return null;
        }

        return new CardC(color,value);
    }

    /**
     * Vytváří objekt reprezentující cílový balíček. Cílem hráče je vložit
     * všechny karty zadané barvy do cílového balíčku.
     * @param color Barva celého balíčku
     * @return Cílový balíček
     */
    public CardDeck createTargetPack(Card.Color color){
        return new TargetPack(color);
    }

    /**
     * Vytváří objekt reprezentující pracovní pole pro karty.
     * @return Pracovní pole
     */
    public CardStack createWorkingPack(){
        return new WorkingPack();
    }
}
