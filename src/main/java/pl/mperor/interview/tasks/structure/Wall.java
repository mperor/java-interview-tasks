package pl.mperor.interview.tasks.structure;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Wall implements Structure {
    private final List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks.stream().filter(block -> block.getColor().equals(color))
                .findFirst();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return blocks.stream().filter(block -> block.getMaterial().equals(material))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return blocks.size();
    }
}

