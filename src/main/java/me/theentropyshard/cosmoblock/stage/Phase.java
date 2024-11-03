package me.theentropyshard.cosmoblock.stage;

import finalforeach.cosmicreach.blocks.Block;

import java.util.List;

public class Phase {
    private final String name;
    private final int number;
    private final int totalBlocks;
    private final List<Block> possibleBlocks;

    public Phase(String name, int number, int totalBlocks, List<Block> possibleBlocks) {
        this.name = name;
        this.number = number;
        this.totalBlocks = totalBlocks;
        this.possibleBlocks = possibleBlocks;
    }

    public String getName() {
        return this.name;
    }

    public int getNumber() {
        return this.number;
    }

    public int getTotalBlocks() {
        return this.totalBlocks;
    }

    public List<Block> getPossibleBlocks() {
        return this.possibleBlocks;
    }
}
