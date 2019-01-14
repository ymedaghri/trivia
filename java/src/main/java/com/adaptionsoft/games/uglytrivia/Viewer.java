package com.adaptionsoft.games.uglytrivia;

public class Viewer implements Viewable {

    @Override
    public void display(String text) {
        System.out.println(text);
    }

}