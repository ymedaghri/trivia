package com.adaptionsoft.games.uglytrivia;

public class Board {
    Category[] positions = new Category[12];

    public Board() {

        for (int i = 0; i < 3; i++) {
            positions[4*i] = Category.POP;
            positions[4*i + 1] = Category.SCIENCE;
            positions[4*i + 2] = Category.SPORT;
            positions[4*i + 3] = Category.ROCK;
        }
    }

    public Category getCategorieFor(int position) {
        return positions[position];
    }
}
