package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Game {
    private final Viewable viewer;
    ArrayList<Player> players = new ArrayList<>();
    QuestionsDeck questionDeck = new QuestionsDeck();
    Board board=new Board();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game(Viewable viewer) {
        this.viewer = viewer;
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));

        viewer.display(playerName + " was added");
        viewer.display("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        viewer.display(getCurrentPlayerName() + " is the current player");
        viewer.display("They have rolled a " + roll);

        if (getCurrentPlayer().isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                viewer.display(getCurrentPlayerName() + " is getting out of the penalty box");
                doSomethingRelatedToPlaces(roll);
            } else {
                viewer.display(getCurrentPlayerName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            doSomethingRelatedToPlaces(roll);
        }

    }

    private String getCurrentPlayerName() {
        return getCurrentPlayer().getName();
    }

    private Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    private void doSomethingRelatedToPlaces(int roll) {
        getCurrentPlayer().moveBy(roll);

        viewer.display(getCurrentPlayerName()
                + "'s new location is "
                + getCurrentPlayer().getPosition());
        viewer.display("The category is " + currentCategory().getName());
        askQuestion();
    }

    private void askQuestion() {
        viewer.display(questionDeck.pick(currentCategory()));
    }


    private Category currentCategory() {
        return board.getCategorieFor(getCurrentPlayer().getPosition());
    }

    public boolean correctAnswer() {
        if (getCurrentPlayer().isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                viewer.display("Answer was correct!!!!");
                return doSomethingRelatedToGoldCoins();
            } else {
                nextPlayerTurn();
                return true;
            }


        } else {

            viewer.display("Answer was corrent!!!!");
            return doSomethingRelatedToGoldCoins();
        }
    }

    private boolean doSomethingRelatedToGoldCoins() {
        getCurrentPlayer().incrementCoins();
        viewer.display(getCurrentPlayerName()
                + " now has "
                + getCurrentPlayer().getCoins()
                + " Gold Coins.");

        boolean notAWinner = !didWin();
        nextPlayerTurn();

        return notAWinner;
    }

    private boolean didWin() {
        return getCurrentPlayer().getCoins() == 6;
    }

    public boolean wrongAnswer() {
        viewer.display("QuestionsDeck was incorrectly answered");
        viewer.display(getCurrentPlayerName() + " was sent to the penalty box");
        getCurrentPlayer().setInPenaltyBox();
        nextPlayerTurn();
        return true;
    }

    private void nextPlayerTurn() {
        if (++currentPlayer == players.size()) currentPlayer = 0;
    }


}
