package src;

import src.card.Card;
import src.card.SpecialCard;
import src.deck.DeckCard;
import src.player.Player;
import java.util.Scanner;

public class GoBoom {
    public static void main(String[] args) {
        Boolean gameOver = false;
        Game currentGame = new Game();
        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            System.out.print("> ");
            String input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case "x": 
                    gameOver = true;
                    break;
                case "d" :
                    if (currentGame.getDeckCard().getSize() > 0) {
                        currentGame.playerDrawCard();
                    } else {
                        System.out.println("\nDeck is empty.\n");
                    } 
                    break;
                case "reset" :
                    currentGame.resetScore();
                    currentGame.clearEveryValues();
                    currentGame.newGame();
                    break; 

                case "" :
                    // just for hiding error
                    break;
                default : 
                    // assuming its a card
                    if (currentGame.validatePlayerChoosenCard(input) == 0){
                        Player player = currentGame.getPlayers().get(currentGame.getcurrentPlayer());
                        Integer cardPosition = player.getCardPosition(input);
                        Card card = player.getCards().get(cardPosition);

                        currentGame.addCardToCenterAndChoosenCardFromPlayer(card);
                        currentGame.convertCardToCardValue();
                        currentGame.nextPlayer();
                        if (currentGame.getTrickCount() > 1){
                            currentGame.getleadCard();
                        }

                    } else {
                        System.out.println("\n" + currentGame.printError(currentGame.validatePlayerChoosenCard(input)) + "\n");
                    }

            }
            
            if (currentGame.checkIfDeckIsEmpty() == true && currentGame.checkIfPlayerCanPlay() == false ) {
                System.out.println("\nDeck is empty, Skipping to next player \n");
                currentGame.nextPlayer();
            } else if (currentGame.getChoosenCardfromPlayer().size() == 4) {
                System.out.println(currentGame.getCardValuefromPlayer());
                System.out.println(currentGame.compareCards());
                currentGame.winnerPlayer(currentGame.compareCards());
                System.out.println("*** " + currentGame.getcurrentPlayer() + " Wins Trick #" + currentGame.getTrickCount() + " ***");
                currentGame.addTrickCount();
                currentGame.clearChoosenCardFromPlayerAndCardValuefromPlayer();
                currentGame.clearCenter();
            } else if (currentGame.checkIfAnyPlayerFinishCards() == true ) {
                currentGame.calculateScore();
                currentGame.clearCenter();
                currentGame.clearChoosenCardFromPlayerAndCardValuefromPlayer();
                currentGame.clearPlayerCards();
                currentGame.newRound();
            }
            currentGame.printGameState();
        }
        scanner.close();
    }
}


