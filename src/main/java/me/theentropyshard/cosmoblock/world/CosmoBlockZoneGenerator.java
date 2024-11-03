package me.theentropyshard.cosmoblock.world;

import com.badlogic.gdx.math.Vector2;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.world.Chunk;
import finalforeach.cosmicreach.world.Zone;
import finalforeach.cosmicreach.worldgen.ChunkColumn;
import finalforeach.cosmicreach.worldgen.ZoneGenerator;

public class CosmoBlockZoneGenerator extends ZoneGenerator {
    @Override
    public String getSaveKey() {
        return "cosmoblock:world";
    }

    @Override
    public void create() {

    }

    @Override
    public Vector2 getSpawnPoint(Vector2 spawnpoint, int attempt) {
        return new Vector2(8, 8);
    }

    @Override
    public void generateForChunkColumn(Zone zone, ChunkColumn column) {
        Chunk chunk = zone.getChunkAtChunkCoords(0, 6, 0);

        if (chunk == null) {
            chunk = new Chunk(0, 6, 0);
            chunk.initChunkData();
            zone.addChunk(chunk);
            column.addChunk(chunk);
        }

        chunk.setBlockState(Block.DIRT.getDefaultBlockState(), 7, 15, 7);
    }

    @Override
    protected String getName() {
        return "CosmoBlock";
    }

    @Override
    public int getDefaultRespawnYLevel() {
        return -16;
    }
}
