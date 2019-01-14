package com.adaptionsoft.games.uglytrivia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    @Test
    @DisplayName("Adding a user displays information on the viewer")
    public void ShouldDisplayInformationOnTheViewerAndReturnTrueWhenAddingAUser() {
        // Arrange
        ViewerMock viewerMock = new ViewerMock();
        Game game = new Game(viewerMock);

        // Act
        boolean added = game.add("Alan");

        // Assert
        assertThat(added).isTrue();
        assertThat(viewerMock.textOutputs).containsExactly("Alan was added", "They are player number 1");

    }

    public static class ViewerMock implements Viewable {

        public ArrayList<String> textOutputs = new ArrayList<>();

        @Override
        public void display(String text) {
            textOutputs.add(text);
        }
    }
}