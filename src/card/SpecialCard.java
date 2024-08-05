package src.card;

public class SpecialCard extends Card {
    public SpecialCard(String suit, String name) {
        super(suit, name);
    }

    protected Integer generateCardValue() {
        if (super.name.equals("X")) {
            return 10 ;
        } else if (this.name.equals("J")) {
            return 11 ;
        } else if (this.name.equals("Q")) {
            return 12 ;
        } else if (this.name.equals("K")) {
            return 13 ;
        } else if (this.name.equals("A")) {
            return 14 ;
        } else {                            // was }
                                            //          return 0;
        return super.generateCardValue(); 
        }
    }
}