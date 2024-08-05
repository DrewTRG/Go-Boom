package src.player;

import java.util.ArrayList; // For Player Cards during the course of the game
import src.card.*;

public class Player {
    private ArrayList<Card> cards = new ArrayList<>();
    private Integer score;

    public Player() {
        this.score = 0;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equals(card.getName()) &&
                cards.get(i).getSuit().equals(card.getSuit())) {
                cards.remove(i);
            }
        }
    }

    public Card drawCard() {
        Card c = cards.get(0);
        cards.remove(0);
        return c;
    }


    public Integer getCardPosition(String Input) {
        Integer cardPosition = null;
        String suit = Input.substring(0,1);
        String rank = Input.substring(1);
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equalsIgnoreCase(rank) &&
                cards.get(i).getSuit().equalsIgnoreCase(suit)) {
                    cardPosition = i;
            }
        }
        return cardPosition;
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public Integer getCardSize() {
        return this.cards.size();
    }

    public void addScore() {
        this.score++;
    }

    public Integer getScore() {
        return this.score;  
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}