package pl.mperor.interview.tasks.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

/**
 * The Wall 🧱
 *
 * <p>The task is to analyze the following code and implement the {@code findBlockByColor}, {@code findBlocksByMaterial}, count methods in the {@link Wall} class - preferably avoiding code duplication and placing all the logic in the Wall class.
 * Taking into account in the analysis and implementation of the CompositeBlock interface!
 *
 * <p><strong>Source code:</strong></p>
 * <pre>
 * import java.util.List;
 * import java.util.Optional;
 *
 * interface Structure {
 *     // Returns any element with the specified color
 *     Optional<Block> findBlockByColor(String color);
 *
 *     // Returns all elements made of the given material
 *     List<Block> findBlocksByMaterial(String material);
 *
 *     // Returns the number of all elements forming the structure
 *     int count();
 * }
 *
 * public class Wall implements Structure {
 *     private List<Block> blocks;
 *
 *     public Optional<Block> findBlockByColor(String color) {
 *         // Method implementation
 *         return null; // Change to appropriate implementation
 *     }
 *
 *     public List<Block> findBlocksByMaterial(String material) {
 *         // Method implementation
 *         return null; // Change to appropriate implementation
 *     }
 *
 *     public int count() {
 *         // Method implementation
 *         return 0; // Change to appropriate implementation
 *     }
 * }
 *
 * interface Block {
 *     String getColor();
 *     String getMaterial();
 * }
 *
 * interface CompositeBlock extends Block {
 *     List<Block> getBlocks();
 * }
 * </pre>
 */
class WallTest {

    private Structure structure;

    @BeforeEach
    void setUp() {
        structure = new Wall(List.of(
                LegoBrick.ofColor("red"),
                LegoBrick.ofColor("blue"),
                new Brick(),
                new CinderBlock(),
                LegoBrickGroup.of(LegoBrick.ofColor("blue"), LegoBrick.ofColor("red"))
        ));
    }

    @Test
    void findBlockByColor() {
        Optional<Block> redBlock = structure.findBlockByColor("red");
        Assertions.assertEquals("red", redBlock.get().getColor());
    }

    @Test
    void findBlocksByMaterial() {
        List<Block> blocks = structure.findBlocksByMaterial("plastic");
        blocks.stream().forEach(block -> Assertions.assertEquals("plastic", block.getMaterial()));
    }

    @Test
    void count() {
        Assertions.assertEquals(5, structure.count());
    }
}