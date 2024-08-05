package src.card;

public class Card {
    protected String suit;
    protected String name;
    protected Integer value;

    public Card(String suit, String name) {
        this.suit = suit;
        this.name = name;

        // Generate card value
        this.value = this.generateCardValue();
    }

    public String getSuit() {
        return this.suit;
    }

    public String getName() {
        return this.name;
    }

    public Integer getValue() {
        return this.value;
    }

    protected Integer generateCardValue() {
        try {
            return Integer.parseInt(this.name);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}