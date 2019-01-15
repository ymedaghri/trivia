package com.adaptionsoft.games.uglytrivia;

public enum Category {

    POP("Pop"), ROCK("Rock"), SCIENCE("Science"), SPORT("Sports");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
