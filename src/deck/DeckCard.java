package src.deck;

import src.card.*;
import src.player.*;
import java.util.ArrayList; // For Player Cards during the course of the game
import java.util.Collections; // For Randomizing deck

public class DeckCard {
    // c = Club, d = Diamond, h = heart , s = spade 
    private static final String[] CARD_SUIT = {"c", "d", "h", "s"};
    // X = 10 J = Jack, Q = Queen, K = King, A = Ace
    private static final String[] CARD_RANK = {"2", "3", "4", "5", "6", "7", "8", "9"}; 
    private static final String[] SPECIAL_CARD_RANK = {"X", "J", "Q", "K", "A"};

    private ArrayList<Card> cards = new ArrayList<>(); // 52 cards deck

    public DeckCard() {
    }

    public Integer getSize() {
        return cards.size();
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public Card drawCard() {
        Card c = cards.get(0);
        cards.remove(0);
        return c;
    }

    public void transferCardToPlayer(Player player, int amount) {
        if (amount > cards.size()) {
            return;
        }

        // Give the card to player n times.
        for (int i = 0; i < amount; i++) {
            // Pop front
            player.addCard(cards.get(0));
            cards.remove(0);
        }
    }


    public static DeckCard generateDeck() { //Generating 52 cards 
        DeckCard self = new DeckCard();
        while (self.cards.size() < 52) {
            for (int i = 0; i < CARD_SUIT.length; i++) {
                for (int j = 0; j < CARD_RANK.length; j++) {
                    self.cards.add(new Card(CARD_SUIT[i], CARD_RANK[j]));
                }
                for (int j = 0; j < SPECIAL_CARD_RANK.length; j++) {
                    self.cards.add(new SpecialCard(CARD_SUIT[i], SPECIAL_CARD_RANK[j]));
                }
            }
        }
        return self;
    }

    public static DeckCard generateRandomizedDeck() {
        DeckCard self = generateDeck();
        Collections.shuffle(self.cards);

        return self;
    }
}