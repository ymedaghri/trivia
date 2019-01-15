package com.adaptionsoft.games.uglytrivia;

public class Player {
    private String name;
    private boolean inPenaltyBox;
    private int coins = 0;
    private int position = 0;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setInPenaltyBox() {
        inPenaltyBox = true;
    }

    public boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    public int getCoins() {
        return coins;
    }

    public void incrementCoins() {
        coins++;
    }

    public int getPosition() {
        return position;
    }

    public void moveBy(int roll) {
        position += roll;
        if (position>11) position-=11;
    }
}
