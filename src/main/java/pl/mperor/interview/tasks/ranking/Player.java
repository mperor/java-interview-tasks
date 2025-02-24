package pl.mperor.interview.tasks.ranking;

import java.util.Comparator;

public record Player(String name, int score) implements Comparable<Player> {

    @Override
    public int compareTo(Player other) {
        return Comparator.comparingInt(Player::score).reversed()
                .thenComparing(Player::name)
                .compare(this, other);
    }
}
