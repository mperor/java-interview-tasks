package pl.mperor.interview.tasks.structure;

import java.util.List;

interface CompositeBlock extends Block {
    List<Block> getBlocks();
}

