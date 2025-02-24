package pl.mperor.interview.tasks.ranking;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

class Ranking<T> {

    private final TreeSet<T> items;

    private Ranking(Comparator<T> comparator, T... varargs) {
        this.items = new TreeSet<>(comparator);
        items.addAll(Arrays.asList(varargs));
    }

    public void add(T item) {
        items.add(item);
    }

    public void replace(Predicate<T> filter, T item) {
        deleteIf(filter);
        add(item);
    }

    public void deleteIf(Predicate<T> filter) {
        items.removeIf(filter);
    }

    public T getLeader() {
        return items.first();
    }

    public List<T> list() {
        return items.stream().toList();
    }

    public static <T extends Comparable<T>> Ranking<T> of(T... varargs) {
        return new Ranking<>(Comparable::compareTo, varargs);
    }

    public static <T> Ranking<T> of(Comparator<T> comparator, T... varargs) {
        return new Ranking<>(comparator, varargs);
    }

    public String format(BiFunction<Integer, T, String> formatter) {
        return format(formatter, Function.identity());
    }

    public <R> String format(BiFunction<Integer, T, String> formatter, Function<T, R> positionUniqueValueMapper) {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        T previous = null;
        for (T item : items) {
            if (previous == null || !positionUniqueValueMapper.apply(item).equals(positionUniqueValueMapper.apply(previous))) {
                counter++;
            }
            String formatted = formatter.apply(counter, item);
            builder.append(formatted);
            previous = item;
        }
        return builder.toString();
    }

}
