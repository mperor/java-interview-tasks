package pl.mperor.interview.tasks.signal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Wega Signals 📡
 *
 * <p>Scientists saved signals coming from space to a file. There are 1000 lines in the file.
 * Each line contains one non-empty word consisting of uppercase English letters.
 * The length of one word does not exceed 100 characters.
 *
 * <p>Write a program which answers the following questions:
 * <ol>
 *     <li>After joining the tenth letters of every fortieth word (starting with the fortieth word),
 *     we will receive a secret message. Write this message.</li>
 *     <li>Find the word that has the largest number of different letters.
 *     Write out the word and the number of its different letters.
 *     If there is more than one such word, write the first of them.</li>
 *     <li>Consider the distance of letters in the alphabet, e.g. the distance between A and B is equal to 1.
 *     Write all words where the distance between each two letters is at most 10.</li>
 * </ol>
 */
public class WegaSignalTest {

    private SignalAnalyzer analyzer;

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException {
        Path examples = Path.of(getClass().getClassLoader().getResource("wega/examples.txt").toURI());
        analyzer = new SignalAnalyzer(Files.readAllLines(examples));
    }

    @Test
    public void testReadSecretMessage() {
        assertEquals("NIECHCIMATURAPROSTABEDZIE", analyzer.readSecretMessage());
    }

    @Test
    public void testFindSignalWithMostDistinctLetters() {
        String signal = analyzer.findSignalWithMostDistinctLetters();

        assertEquals("AKLMNOPRSTWZA", signal);
        assertEquals(12, SignalAnalyzer.countDistinctLetters(signal));
    }

    @Test
    public void testFirstSignalWinsWhenDistinctLettersAreEqual() {
        var tiedAnalyzer = new SignalAnalyzer(List.of("AB", "ABC", "CBA", "AAB"));

        assertEquals("ABC", tiedAnalyzer.findSignalWithMostDistinctLetters());
    }

    @Test
    public void testFindSignalsWithLettersDistanceAtMost10() {
        List<String> expected = List.of(
                "AAAAAAAAAI",
                "AAAAAAAAAE",
                "AAAAAAAAAC",
                "AAAAAAAAAH",
                "AAAAAAAAAC",
                "AAAAAAAAAI",
                "AAAAAAAAAA",
                "BB",
                "AAAAAAAAAA",
                "AAAAAAAAAA",
                "AAAAAAAAAB",
                "AAAAAAAAAE",
                "AAAAAAAAAD",
                "AAAAAAAAAI",
                "AAAAAAAAAE"
        );

        assertEquals(expected, analyzer.findSignalsWithLettersDistanceAtMost(10));
    }
}
