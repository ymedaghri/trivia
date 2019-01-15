package com.adaptionsoft.games.uglytrivia;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class GameTest {

    private ViewerMock viewerMock;
    private Game game;

    @BeforeEach
    void setUp() {
        viewerMock = new ViewerMock();
        game = new Game(viewerMock);
    }

    @Test
    @DisplayName("Should display information on the viewer and return true when adding a user")
    public void testAddUser() {

        // Act
        boolean added = game.add("Peter Paul");

        // Assert
        assertThat(added).isTrue();
        assertThat(viewerMock.textOutputs).containsExactly("Peter Paul was added", "They are player number 1");

    }

    @Test
    @DisplayName("Should display the number of users")
    public void testHowManyUsers() {
        // Act
        Arrays.asList("Leonardo", "Peter Paul", "Vincent").forEach(user -> game.add(user));

        // Assert
        assertThat(game.howManyPlayers()).isEqualTo(3);
    }

    @Test
    @DisplayName("Current player should change to next one after a wrong answer")
    public void testChangeCurrentPlayerAfterWrongAnswer() {
        // Arrange
        game.add("Leonardo");
        game.add("Peter Paul");
        game.add("Vincent");
        viewerMock.clearAll();

        // Act
        game.roll(2);

        // Assert
        assertThat(viewerMock.textOutputs).contains("Leonardo is the current player");
        viewerMock.clearAll();

        // Act
        game.wrongAnswer();
        game.roll(3);

        // Assert
        assertThat(viewerMock.textOutputs).contains("Peter Paul is the current player");
    }

    @Test
    @DisplayName("Current player should change to next one after a correct answer")
    public void testChangeCurrentPlayerAfterCorrectAnswer() {
        // Arrange
        game.add("Vincent");
        game.add("Leonardo");
        game.add("Peter Paul");
        viewerMock.clearAll();

        // Act
        game.roll(2);

        // Assert
        assertThat(viewerMock.textOutputs).contains("Vincent is the current player");
        viewerMock.clearAll();

        // Act
        game.correctAnswer();
        game.roll(3);

        // Assert
        assertThat(viewerMock.textOutputs).contains("Leonardo is the current player");
    }

    @Test
    @DisplayName("Should go to penalty box when giving a wrong answer")
    public void shouldGoToPenaltyBox() {
        // Arrange
        game.add("Leonardo");
        game.add("Peter Paul");
        game.add("Vincent");

        // Act
        game.roll(2);
        game.wrongAnswer();

        // Assert
        assertThat(viewerMock.textOutputs).contains("Leonardo was sent to the penalty box");
    }

    @ParameterizedTest(name = "Rolling {0}")
    @ValueSource(ints = {1, 3, 5, 7, 9, 11})
    @DisplayName("Should get out of penalty box when rolling an odd number")
    public void shouldGetOutOfPenaltyBox(int roll) {
        // Arrange
        game.add("Leonardo");

        // Act
        game.roll(2);
        game.wrongAnswer();

        // Assert
        assertThat(viewerMock.textOutputs).contains("Leonardo was sent to the penalty box");
        viewerMock.clearAll();

        // Act
        game.roll(roll);

        // Assert
        assertThat(viewerMock.textOutputs).contains("Leonardo is getting out of the penalty box");
    }

    @ParameterizedTest(name = "Rolling {0}")
    @ValueSource(ints = {2, 4, 6, 8, 10, 12})
    @DisplayName("Should not get out of penalty box when rolling an even number")
    public void shouldNotGetOutOfPenaltyBox(int roll) {
        // Arrange
        game.add("Leonardo");

        // Act
        game.roll(2);
        game.wrongAnswer();

        // Assert
        assertThat(viewerMock.textOutputs).contains("Leonardo was sent to the penalty box");
        viewerMock.clearAll();

        // Act
        game.roll(roll);

        // Assert
        assertThat(viewerMock.textOutputs).contains("Leonardo is not getting out of the penalty box");
    }

    @Test
    @DisplayName("Should collect Gold coins when not in penalty box and giving a correct answer")
    public void shouldCollectCoinsWhenCorrectAndNotInPenaltyBox() {
        // Arrange
        game.add("Leonardo");
        game.add("Peter Paul");
        viewerMock.clearAll();

        // Act
        game.correctAnswer();

        // Assert
        assertThat(viewerMock.textOutputs).containsExactly("Answer was corrent!!!!", "Leonardo now has 1 Gold Coins.");
    }

    @Test
    @DisplayName("Should collect Gold coins when in penalty box and getting out of it and giving a correct answer")
    public void shouldCollectCoinsWhenCorrectAndInPenaltyBoxAndGettingOut() {
        // Arrange
        game.add("Leonardo");
        game.add("Peter Paul");
        game.wrongAnswer();
        game.roll(2);
        game.correctAnswer();
        viewerMock.clearAll();

        // Act
        game.roll(3);
        game.correctAnswer();

        // Assert
        assertThat(viewerMock.textOutputs).containsExactly("Leonardo is the current player",
                "They have rolled a 3",
                "Leonardo is getting out of the penalty box",
                "Leonardo's new location is 3",
                "The category is Rock",
                "Rock QuestionsDeck 0",
                "Answer was correct!!!!",
                "Leonardo now has 1 Gold Coins.");
    }

    @Test
    @DisplayName("Should not collect Gold coins when in penalty box and not getting out of it and giving a correct answer")
    public void shouldNotCollectCoinsWhenCorrectAndInPenaltyBoxAndNotGettingOut() {
        // Arrange
        game.add("Leonardo");
        game.add("Peter Paul");
        game.wrongAnswer();
        game.roll(2);
        game.correctAnswer();
        viewerMock.clearAll();

        // Act
        game.roll(2);
        game.correctAnswer();

        // Assert
        assertThat(viewerMock.textOutputs).containsExactly("Leonardo is the current player",
                "They have rolled a 2",
                "Leonardo is not getting out of the penalty box");
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @MethodSource("categoriesProvider")
    @DisplayName("Should display the corresponding category for each location")
    public void testCategoriesMappedtoLocations(String category, List<Integer> locations) {

        locations.forEach(location -> {
            viewerMock=new ViewerMock();
            Game game=new Game(viewerMock);
            game.add("Peter Paul");
            game.roll(location);

            assertThat(viewerMock.textOutputs).contains("Peter Paul's new location is "+location, Index.atIndex(4));
            assertThat(viewerMock.textOutputs).contains("The category is "+category, Index.atIndex(5));
        });


    }

    static Stream<Arguments> categoriesProvider() {
        return Stream.of(
                of("Pop", asList(0, 4, 8)),
                of("Science", asList(1, 5, 9)),
                of("Sports", asList(2, 6, 10)),
                of("Rock", asList(3, 7, 11))
        );
    }

    public static class ViewerMock implements Viewable {

        public ArrayList<String> textOutputs = new ArrayList<>();

        @Override
        public void display(String text) {
            textOutputs.add(text);
        }

        public void clearAll() {
            textOutputs = new ArrayList<>();
        }
    }
}