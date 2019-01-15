package com.adaptionsoft.games.uglytrivia;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class QuestionsDeck {

    Map<Category, LinkedList<String>> questionsByCategory = new HashMap<>();

    public QuestionsDeck() {

        questionsByCategory.put(Category.POP, new LinkedList<>());
        questionsByCategory.put(Category.SCIENCE, new LinkedList<>());
        questionsByCategory.put(Category.SPORT, new LinkedList<>());
        questionsByCategory.put(Category.ROCK, new LinkedList<>());

        for (int i = 0; i < 50; i++) {
            questionsByCategory.putIfAbsent(Category.POP, new LinkedList<>()).add("Pop QuestionsDeck " + i);
            questionsByCategory.putIfAbsent(Category.SCIENCE, new LinkedList<>()).add("Science QuestionsDeck " + i);
            questionsByCategory.putIfAbsent(Category.SPORT, new LinkedList<>()).add("Sports QuestionsDeck " + i);
            questionsByCategory.putIfAbsent(Category.ROCK, new LinkedList<>()).add("Rock QuestionsDeck " + i);
        }

    }

    public String pick(Category category) {
        return questionsByCategory.get(category).removeFirst();
    }
}
