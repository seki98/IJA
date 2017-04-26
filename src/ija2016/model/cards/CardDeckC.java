package ija2016.model.cards;

public class CardDeckC implements CardDeck {
    protected int size;
    protected Card[] deck;
    protected int top=-1;

    public CardDeckC(int init_s) {
        this.size = init_s - 1;
        this.deck = new Card[init_s];
    }

    /**
     * Vrátí kartu z vrcholu zásobníku (karta zůstává na zásobníku).
     * Pokud je balíček prázdný, vrací null.
     * @return Karta z vrcholu balíčku
     */
    public Card get(){
        return this.deck[this.top];
    }

    /**
     * Vrátí kartu na uvedenem indexu. Spodni karta je na indexu 0, vrchol je na indexu size()-1.
     * Pokud je balíček prázdný, nebo index mimo rozsah, vrací null.
     * @param idx Pozice karty v balicku
     * @return Karta z vrcholu balíčku
     */
    public Card get(int idx){
        if((idx > this.size) || (idx < 0)){
            return null;
        }
        return this.deck[idx];
    }

    /**
     * Test, zda je balíček karet prázdný.
     * @return Vrací true, pokud je balíček prázdný.
     */
    public boolean isEmpty(){
        return (this.top == -1);
    }

    /**
     * Vloží kartu na vrchol balíčku.
     * @param c Vkládaná karta
     * @return Úspěšnost akce.
     */
    public boolean put(Card c){
        if(this.top == this.size){
            return false;
        }
        else {
            this.top++;
            this.deck[this.top] = c;
            return true;
        }
    }

    /**
     * Odebere kartu z vrcholu balíčku. Pokud je balíček prázdný, vrací null.
     * @return  Karta z vrcholu balíčku
     */
    public Card pop() {
        if(this.top == -1){
            return null;
        }
        else {
            return this.deck[this.top--];
        }
    }

    /**
     * @return Aktuální počet karet v balíčku.
     */
    public int size() {
        return (this.top+1);
    }

    public static CardDeck createStandardDeck() {
        CardDeck newdeck = new CardDeckC(52);

        for (Card.Color curr_col : Card.Color.values()) {
            for (int i = 1; i <= 13; i++) {
                newdeck.put(new CardC(curr_col, i));
            }
        }

        return newdeck;
    }

}
