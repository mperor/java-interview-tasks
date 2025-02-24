package pl.mperor.interview.tasks.ranking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

/**
 * Task: Player Ranking System 🏆
 *
 * <p><b>Description:</b>
 * Your task is to develop a ranking system for players in a game using Java.
 * The application should allow adding players, updating their scores, and displaying the ranking
 * in a sorted manner based on their scores.
 *
 * <p><b>Requirements:</b>
 * - Implement a `Player` class with attributes: `name` (String) and `score` (int).
 * - Create a ranking system that maintains a collection of players and sorts them dynamically.
 * - Implement methods for:
 * - Adding a new player to the ranking.
 * - Updating the score of an existing player.
 * - Displaying the current ranking in descending order of scores.
 *
 * <p><b>Conditions:</b>
 * - The ranking should always be sorted from the highest to the lowest score.
 * - If a player's score is updated, their position in the ranking should be adjusted accordingly.
 * - Player names should be unique.
 *
 * <p><b>Example:</b>
 *
 * <p>Given the following players:
 * <pre>
 * Player("Alice", 1200)
 * Player("Bob", 1500)
 * Player("Charlie", 1300)
 * Player("Zeus", 1300)
 * </pre>
 * <p>
 * The ranking should be displayed as:
 * <pre>
 * 1. Bob - 1500 pts
 * 2. Charlie - 1300 pts
 * 2. Zeus - 1300 pts
 * 3. Alice - 1200 pts
 * </pre>
 * <p>
 * If Alice's score is increased by 400 points, the updated ranking should be:
 * <pre>
 * 1. Alice - 1600 pts
 * 2. Bob - 1500 pts
 * 3. Charlie - 1300 pts
 * 3. Zeus - 1300 pts
 * </pre>
 *
 * <p><b>Additional Points:</b>
 * - Use a suitable data structure (e.g., `TreeSet`, `PriorityQueue`, or `List`).
 * - Consider optimizing performance for frequent updates.
 * - Implement test cases to verify correct ranking behavior.
 *
 * <p><b>Expected Points:</b>
 * - <b>Code Quality:</b> Is the code well-structured and easy to understand?
 * - <b>Correctness:</b> Does the ranking update correctly after score changes?
 * - <b>Performance:</b> Is the chosen data structure efficient for ranking operations?
 */
public class PlayerRankingSystemTest {

    private Ranking<Player> ranking;

    private Player bob;
    private Player charlie;
    private Player zeus;
    private Player alice;

    @BeforeEach
    void setUp() {
        bob = new Player("Bob", 1500);
        charlie = new Player("Charlie", 1300);
        zeus = new Player("Zeus", 1300);
        alice = new Player("Alice", 1200);
        ranking = Ranking.of(
                alice,
                bob,
                charlie,
                zeus
        );
    }

    @Test
    public void shouldReturnLeaderFromPlayerRanking() {
        Assertions.assertEquals(bob, ranking.getLeader());
    }

    @Test
    public void shouldDisplayRankingWithPlayers() {
        var formatted = ranking.format((position, player) ->
                "%d. %s - %d pts%n".formatted(position, player.name(), player.score()), Player::score);
        System.out.println(formatted);
        Assertions.assertLinesMatch("""
                1. Bob - 1500 pts
                2. Charlie - 1300 pts
                2. Zeus - 1300 pts
                3. Alice - 1200 pts
                """.lines(), formatted.lines());
    }

    @Test
    public void shouldAllowIncreasePlayerScore() {
        alice = new Player(alice.name(), alice.score() + 400);
        ranking.replace(player -> alice.name().equals(player.name()), alice);
        Assertions.assertTrue(Objects.deepEquals(List.of(
                alice,
                bob,
                charlie,
                zeus
        ), ranking.list()));
    }

}
