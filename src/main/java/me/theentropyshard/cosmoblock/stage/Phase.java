package me.theentropyshard.cosmoblock.stage;

import finalforeach.cosmicreach.blocks.Block;

import java.util.Arrays;
import java.util.List;

public class Phase {
    private final String name;
    private final int totalBlocks;
    private final List<Block> possibleBlocks;

    private int number;

    private Phase(String name, int totalBlocks, List<Block> possibleBlocks) {
        this.name = name;
        this.totalBlocks = totalBlocks;
        this.possibleBlocks = possibleBlocks;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private int totalBlocks;
        private Block[] possibleBlocks;

        private Builder() {

        }

        public Builder name(String name) {
            this.name = name;

            return this;
        }

        public Builder totalBlocks(int totalBlocks) {
            this.totalBlocks = totalBlocks;

            return this;
        }

        public Builder possibleBlocks(Block... possibleBlocks) {
            this.possibleBlocks = possibleBlocks;

            return this;
        }

        public Phase build() {
            return new Phase(this.name, this.totalBlocks, Arrays.asList(this.possibleBlocks));
        }
    }

    public String getName() {
        return this.name;
    }

    public int getTotalBlocks() {
        return this.totalBlocks;
    }

    public List<Block> getPossibleBlocks() {
        return this.possibleBlocks;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
