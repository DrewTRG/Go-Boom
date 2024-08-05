package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import src.card.*;
import src.deck.*;
import src.player.*;

public class Game {
    private final Integer NUMBER_OF_PLAYERS = 4;
    private final Integer NUMBER_OF_CARD_PER_PLAYER = 7;
    private final String[] PLAYER_NAME = {"Player1", "Player2", "Player3", "Player4"};
    
    private Map<String,Player> players = new HashMap<String,Player>();
    private Map<String,Card> choosenCardfromPlayer = new HashMap<String, Card>();
    private Map<String,Integer> cardValuefromPlayer = new HashMap<String, Integer>();
    private ArrayList<Card> center = new ArrayList<>();
    private Card LeadCard;
    private DeckCard deck;
    private Integer currentPlayer; 
    private Integer trickCount;

    public Game() {
        // Initialize game
        this.deck = DeckCard.generateRandomizedDeck();
        this.trickCount = 1;

        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            this.players.put(PLAYER_NAME[i], new Player());
            this.deck.transferCardToPlayer(this.players.get(PLAYER_NAME[i]), NUMBER_OF_CARD_PER_PLAYER);
        }
        // First Trick, Lead Card from Deck
        this.center.add(this.deck.drawCard());
        getleadCard();
        currentPlayer();
        printGameState();
    }

    public Map<String,Player> getPlayers() {
        return this.players;
    }

    public Map<String,Card>  getChoosenCardfromPlayer() {
        return this.choosenCardfromPlayer;
    }

    public Map<String,Integer> getCardValuefromPlayer() {
        return this.cardValuefromPlayer;
    }

    public DeckCard getDeckCard() {
        return this.deck;
    }

    public void addTrickCount() {
        this.trickCount++;
    }

    public Integer getTrickCount() {
        return this.trickCount;
    }

    public String getcurrentPlayer() {
        return PLAYER_NAME[this.currentPlayer];
    }

    public ArrayList<Card> getcenterCard() {
        return this.center;
    }

    public void clearCenter() {
        this.center.clear();
    }

    public Card getleadCard() {
        this.LeadCard = this.center.get(0);
        return this.LeadCard;
    }

    public void currentPlayer() {
        Card centerCard = this.center.get(0);
        if (centerCard.getName().contains("A") || centerCard.getName().contains("5") || centerCard.getName().contains("9") || centerCard.getName().contains("K")) {
            this.currentPlayer = 0;
        } else if (centerCard.getName().contains("2") || centerCard.getName().contains("6") || centerCard.getName().contains("X")) {
            this.currentPlayer = 1;
        } else if (centerCard.getName().contains("3") || centerCard.getName().contains("7") || centerCard.getName().contains("J")) {
            this.currentPlayer = 2;
        } else if (centerCard.getName().contains("4") || centerCard.getName().contains("8") || centerCard.getName().contains("Q")) {
            this.currentPlayer = 3;
        }
    }

    public void nextPlayer() {
        this.currentPlayer++;
        if (this.currentPlayer > 3) {
            this.currentPlayer = 0;
        }
    }

    public Boolean validatePlayerInputToLeadCard(Card card) { //Validate whether the Lead Card's suit or rank matches with user input card;
        Boolean cardStatus = false;

        for (int i = 0; i < center.size(); i++) {
            if (this.LeadCard.getSuit().equalsIgnoreCase(card.getSuit())){
                cardStatus = true;
            } else if (this.LeadCard.getName().equalsIgnoreCase(card.getName())) {
                cardStatus = true;
            }
        }
        return cardStatus;
    }

    public void addCardToCenterAndChoosenCardFromPlayer(Card card) {
        this.center.add(card);
        this.choosenCardfromPlayer.put(getcurrentPlayer(), card);
        getPlayers().get(getcurrentPlayer()).removeCard(card);
    }

    public void clearChoosenCardFromPlayerAndCardValuefromPlayer() {
        this.choosenCardfromPlayer.clear();
        this.cardValuefromPlayer.clear();
    }

    public void convertCardToCardValue() {
        for (String i : this.getChoosenCardfromPlayer().keySet()) {
            this.getCardValuefromPlayer().put(i , this.getChoosenCardfromPlayer().get(i).getValue());
        }
    }

    public String compareCards() {
        String winningPlayer = null;
        for (String i : this.getCardValuefromPlayer().keySet()) {
            boolean isWinningPlayer = true;
            for (String j : this.getCardValuefromPlayer().keySet()) {
                if (!i.equals(j) && this.getCardValuefromPlayer().get(i) <= this.getCardValuefromPlayer().get(j)) {
                    isWinningPlayer = false;
                    break;
                }
            }
            if (isWinningPlayer) {
                winningPlayer = i;
                break;
            }
        }
        if (winningPlayer == null) {
            for (String i : this.getChoosenCardfromPlayer().keySet()) {
                if (this.getChoosenCardfromPlayer().get(i).equals(getleadCard())) {
                    winningPlayer = i;

                } else if (winningPlayer == null) {
                        winningPlayer = this.getcurrentPlayer();
                    }
            }
        }
        if (winningPlayer == null) {
            winningPlayer = this.getcurrentPlayer();
        }
        return winningPlayer;
    }

    public void winnerPlayer(String player) {
        for (int i = 0; i < PLAYER_NAME.length; i++) {
            if (PLAYER_NAME[i].equals(player)) {
                this.currentPlayer = i;
            }
        }
    }

    public void playerDrawCard() {
        getPlayers().get("Player" + String.valueOf(this.currentPlayer + 1)).addCard(this.deck.drawCard());
    }

    public Boolean checkIfPlayerCanPlay() {
        Boolean playerCanPutCardtoCenter = false;
        for (Card card : getPlayers().get(getcurrentPlayer()).getCards()) {
            if (card.getSuit().equals(this.LeadCard.getSuit()) || 
                (card.getName().equals(this.LeadCard.getName()))) {
                    playerCanPutCardtoCenter = true;
            }
        }
        return playerCanPutCardtoCenter;
    }

    public Boolean checkIfDeckIsEmpty() {
        Boolean deckIsEmpty = false;
        if (this.deck.getSize() == 0) {
            deckIsEmpty = true;
        }
        return deckIsEmpty;
    }

    public Boolean checkIfAnyPlayerFinishCards() {
        Boolean playerFinishCards = false;
        for (int i = 0; i < PLAYER_NAME.length; i++ ) {
            if (getPlayers().get(PLAYER_NAME[i]).getCardSize() == 0) {
                playerFinishCards = true;
            }
        }
        return playerFinishCards;
    }

    public void calculateScore() {
        for (int i = 0; i < PLAYER_NAME.length; i++ ) {
            Integer playerScore = 0;
            for (Card card : getPlayers().get(PLAYER_NAME[i]).getCards()) {
                // Ace Card
                if (card.getValue() == 14 ){
                    playerScore += 1;
                // King/Queen/Jack card
                } else if (card.getValue() > 10 && card.getValue() < 14) {
                    playerScore += 10;
                } else {
                    playerScore += card.getValue();
                }
            }
            getPlayers().get(PLAYER_NAME[i]).setScore(playerScore);
        }
    }

    // Helper function

    public void printGameState() { 
        System.out.println("Trick #" + getTrickCount());

        // Print players' cards
        for (int i = 1; i <= 4; i++) {
            System.out.print("Player" + String.valueOf(i) + ": ");
            for (Card playerCard : getPlayers().get("Player" + String.valueOf(i)).getCards()) {
                System.out.print("[" + playerCard.getSuit() + playerCard.getName() + "] ");
            }
            System.out.println("");
        }

        // Print center cards
        System.out.print("Center: ");
        for (Card centerCard : getcenterCard()) {
            System.out.print("[" + centerCard.getSuit() + centerCard.getName() + "] ");
        }
        System.out.println("");

        // Print deck
        System.out.print("Deck: " );
        for (Card deck: deck.getCards()) {
            System.out.print("[" + deck.getSuit() + deck.getName() + "] ");
        }
        System.out.println("");

        // Print scores
        System.out.print("Score: ");
        for (String playerName : PLAYER_NAME) {
            System.out.print(playerName + " = " + players.get(playerName).getScore() + " | ");
        }
        System.out.println("");

        // Print current player  
        System.out.println("Turn: " + getcurrentPlayer());
    }

    public Integer validatePlayerChoosenCard(String input) {
        Player player = getPlayers().get(getcurrentPlayer());
        Integer cardPosition = player.getCardPosition(input);
        Card card;
        if (cardPosition == null) {
            // Invalid input or You don't have this card!
            return 1;
        } else {
            card = player.getCards().get(cardPosition);
        } 
        
        if (validatePlayerInputToLeadCard(card) == false) {
            if (getcenterCard().size() == 0) {
                return 0;
            } else {
            // Selected Card does not match with the Suit or Rank of the Lead Card
            return 2;
            }
        } else {
            // both condition met
            return 0;
        }
    }

    public String printError(Integer errorcode) {
        String error;
        if (errorcode == 1) {
           error = "Invalid input or You don't have this card!" ;
        } else if (errorcode == 2) {
            error = "Selected Card does not match with the Suit or Rank of the Lead Card";
        } else {
            error = null;
        }
        return error;
    }

    public void clearEveryValues() {
        this.players.clear();
        this.choosenCardfromPlayer.clear();
        this.cardValuefromPlayer.clear();
        this.center.clear();
    }

    public void clearPlayerCards() {
        for (int i = 0; i < PLAYER_NAME.length; i++ ) {
            getPlayers().get(PLAYER_NAME[i]).getCards().clear();
        }
    }

    public void resetScore() {
        for (String playerName : PLAYER_NAME) {
            players.get(playerName).setScore(0);
        }
    }

    public void newGame() {
        this.deck = DeckCard.generateRandomizedDeck();
        this.trickCount = 1;

        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            this.players.put(PLAYER_NAME[i], new Player());
            this.deck.transferCardToPlayer(this.players.get(PLAYER_NAME[i]), NUMBER_OF_CARD_PER_PLAYER);
        }
        // First Trick, Lead Card from Deck
        this.center.add(this.deck.drawCard());
        this.getleadCard();
        this.currentPlayer();
    }

    public void newRound() {
        this.deck = DeckCard.generateRandomizedDeck();
        this.trickCount = 1;

        this.center.add(this.deck.drawCard());
        this.getleadCard();
        this.currentPlayer();
    }
    

}