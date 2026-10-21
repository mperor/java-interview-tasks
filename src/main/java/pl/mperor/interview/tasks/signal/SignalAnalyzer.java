package pl.mperor.interview.tasks.signal;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class SignalAnalyzer {

    private final List<String> signals;

    public SignalAnalyzer(List<String> signals) {
        this.signals = List.copyOf(signals);
    }

    public String readSecretMessage() {
        return IntStream.iterate(39, index -> index < signals.size(), index -> index + 40)
                .mapToObj(signals::get)
                .map(signal -> String.valueOf(signal.charAt(9)))
                .reduce("", String::concat);
    }

    public String findSignalWithMostDistinctLetters() {
        return signals.stream()
                .max(Comparator.comparingLong(SignalAnalyzer::countDistinctLetters))
                .orElseThrow();
    }

    public static long countDistinctLetters(String signal) {
        return signal.chars().distinct().count();
    }

    public List<String> findSignalsWithLettersDistanceAtMost(int maxDistance) {
        return signals.stream()
                .filter(signal -> lettersDistance(signal) <= maxDistance)
                .toList();
    }

    private static int lettersDistance(String signal) {
        var statistics = signal.chars().summaryStatistics();
        return statistics.getMax() - statistics.getMin();
    }
}
