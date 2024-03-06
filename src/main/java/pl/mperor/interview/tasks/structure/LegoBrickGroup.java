package pl.mperor.interview.tasks.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LegoBrickGroup implements CompositeBlock {

    private final List<LegoBrick> group;

    private LegoBrickGroup(List<LegoBrick> group) {
        this.group = group;
    }

    @Override
    public String getColor() {
        return group.stream()
                .map(LegoBrick::getColor)
                .collect(Collectors.joining(","));
    }

    @Override
    public String getMaterial() {
        if (group.isEmpty())
            throw new IllegalStateException("Group is empty!");

        return group.get(0).getMaterial();
    }

    @Override
    public List<Block> getBlocks() {
        return new ArrayList<>(group);
    }

    public static LegoBrickGroup of(LegoBrick... bricks) {
        return new LegoBrickGroup(Arrays.stream(bricks).toList());
    }

}
