package src.ija2016.model.cards;

public class CardStackC extends CardDeckC implements CardStack{

    /**
     * Konstruktor
     */
    public CardStackC(int init_s) {
        super(init_s);
        this.size = init_s - 1;
        this.deck = new Card[init_s];
    }

    /**
     * Vloží karty ze zásobníku stack na vrchol zásobníku. Karty vkládá ve stejném pořadí,
     * v jakém jsou uvedeny v zásobníku stack.
     * @param stack Zásobník vkládaných karet.
     * @return Uspěšnost akce.
     */
    public boolean put(CardStack stack){
        if(stack.isEmpty()) {
            System.out.println("well");
            return true;
        }
        for(int i=0; i<stack.size(); i++){
            if(!this.put(stack.get(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * Metoda odebere ze zásobníku sekvenci karet od zadané karty až po vrchol zásobníku.
     * Pokud je hledaná karta na vrcholu, bude v sekvenci pouze jedna karta.
     * @param card Hledaná karta
     * @return  Zásobník karet obsahující odebranou sekvenci.
     *          Pokud hledaná karta v zásobníku není, vrací null.
     */
    public CardStack pop(Card card){
        int i = 0;

        while(!this.get(i).equals(card)){
            if(i < this.top){
                i++;
            }
            else {
                return null;
            }
        }

        CardStack out = new CardStackC(this.top - i + 1);

        for(int j = i; j<=this.top; j++){
            out.put(this.get(j));
        }

        for(int j = this.top; j>=i; j--){
            this.top--;
        }

        return out;
    }

}
