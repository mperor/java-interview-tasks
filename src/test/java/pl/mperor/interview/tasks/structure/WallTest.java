package pl.mperor.interview.tasks.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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