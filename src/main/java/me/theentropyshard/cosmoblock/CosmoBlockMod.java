package me.theentropyshard.cosmoblock;

import com.badlogic.gdx.math.Vector3;
import dev.crmodders.cosmicquilt.api.entrypoint.ModInitializer;
import finalforeach.cosmicreach.blocks.Block;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.chat.Chat;
import me.theentropyshard.cosmoblock.stage.Phase;
import org.quiltmc.loader.api.ModContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CosmoBlockMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("CosmoBlock");

    public static final int THE_BLOCK_X = 7;
    public static final int THE_BLOCK_Y = 15;
    public static final int THE_BLOCK_Z = 7;

    public static final Vector3 SPAWN_POINT = new Vector3(7.5f, 112.0F, 7.5f);

    public static int totalBlocksBroken;
    public static Phase currentPhase;
    public static boolean finished;

    public static String stats;

    private static int phaseCounter;
    public static final Map<Integer, Phase> phases = new HashMap<>();

    static {
        CosmoBlockMod.addPhase(
            Phase.newBuilder()
                .name("Forest")
                .totalBlocks(250)
                .possibleBlocks(
                    Block.DIRT,
                    Block.GRASS,
                    Block.TREELOG,
                    Block.getInstance("base:leaves_poplar")
                )
                .build()
        );

        CosmoBlockMod.addPhase(
            Phase.newBuilder()
                .name("Mountains")
                .totalBlocks(550)
                .possibleBlocks(
                    Block.DIRT,
                    Block.GRASS,
                    Block.TREELOG,
                    Block.SNOW,
                    Block.getInstance("base:leaves_poplar"),
                    Block.getInstance("base:stone_gabbro")
                )
                .build()
        );

        CosmoBlockMod.addPhase(
            Phase.newBuilder()
                .name("Beach")
                .totalBlocks(1000)
                .possibleBlocks(
                    Block.SAND,
                    Block.TREELOG,
                    Block.COCONUT,
                    Block.getInstance("base:leaves"),
                    Block.getInstance("base:stone_gravel")
                )
                .build()
        );

        CosmoBlockMod.addPhase(
            Phase.newBuilder()
                .name("Cave")
                .totalBlocks(1900)
                .possibleBlocks(
                    Block.DIRT,
                    Block.STONE_BASALT,
                    Block.getInstance("base:stone_gabbro"),
                    Block.getInstance("base:stone_limestone"),
                    Block.getInstance("base:stone_gravel"),
                    Block.getInstance("base:magma"),
                    Block.getInstance("base:stone_gabbro"),
                    Block.getInstance("base:ore_bauxite"),
                    Block.getInstance("base:ore_iron"),
                    Block.getInstance("base:ore_gold")
                )
                .build()
        );

        CosmoBlockMod.addPhase(
            Phase.newBuilder()
                .name("Winter")
                .totalBlocks(3100)
                .possibleBlocks(
                    Block.SNOW,
                    Block.DIRT
                )
                .build()
        );

        CosmoBlockMod.addPhase(
            Phase.newBuilder()
                .name("Moon")
                .totalBlocks(5000)
                .possibleBlocks(
                    Block.STONE_BASALT,
                    Block.LUNAR_SOIL,
                    Block.getInstance("base:ore_iron")
                )
                .build()
        );

        CosmoBlockMod.addPhase(
            Phase.newBuilder()
                .name("Factory")
                .totalBlocks(8000)
                .possibleBlocks(
                    Block.WOODPLANKS,
                    Block.getInstance("base:aluminium_panel"),
                    Block.getInstance("base:asphalt"),
                    Block.getInstance("base:bricks"),
                    Block.getInstance("base:c4"),
                    Block.getInstance("base:crate_wooden"),
                    Block.getInstance("base:furnace"),
                    Block.getInstance("base:glass"),
                    Block.getInstance("base:hazard"),
                    Block.getInstance("base:light"),
                    Block.getInstance("base:metal_panel")
                )
                .build()
        );

        CosmoBlockMod.currentPhase = CosmoBlockMod.phases.get(0);

        CosmoBlockMod.updateStats();
    }

    public static void addPhase(Phase phase) {
        phase.setNumber(CosmoBlockMod.phaseCounter++);

        CosmoBlockMod.phases.put(phase.getNumber(), phase);
    }

    public static Phase getPhaseByName(String phaseName) {
        for (Phase phase : CosmoBlockMod.phases.values()) {
            if (phase.getName().equals(phaseName)) {
                return phase;
            }
        }

        CosmoBlockMod.LOGGER.warn("Could not find phase with name '" + phaseName + "' (actually null: " + (phaseName == null) + ")");

        return null;
    }

    @Override
    public void onInitialize(ModContainer mod) {
        CosmoBlockMod.LOGGER.info("CosmoBlock initialized!");
    }

    public static void brokeBlock() {
        if (CosmoBlockMod.finished) {
            return;
        }

        CosmoBlockMod.totalBlocksBroken++;

        CosmoBlockMod.updateStats();

        if (CosmoBlockMod.totalBlocksBroken == CosmoBlockMod.currentPhase.getTotalBlocks()) {
            CosmoBlockMod.nextStage();

            CosmoBlockMod.updateStats();

            if (CosmoBlockMod.finished) {
                return;
            }

            Chat.MAIN_CLIENT_CHAT.addMessage(null, "You have entered phase " +
                (CosmoBlockMod.currentPhase.getNumber() + 1) +
                " - " + CosmoBlockMod.currentPhase.getName() + "!");
            Chat.MAIN_CLIENT_CHAT.addMessage(null, "Blocks:");
            CosmoBlockMod.currentPhase.getPossibleBlocks().forEach(block -> {
                Chat.MAIN_CLIENT_CHAT.addMessage(null, " - " + block.getName());
            });
        }
    }

    public static void nextStage() {
        Phase nextPhase = CosmoBlockMod.phases.get(CosmoBlockMod.currentPhase.getNumber() + 1);

        if (nextPhase == null) {
            CosmoBlockMod.finished = true;

            Chat.MAIN_CLIENT_CHAT.addMessage(null, "You have completed the last phase!");

            return;
        }

        CosmoBlockMod.currentPhase = nextPhase;
    }

    public static void updateStats() {
        CosmoBlockMod.stats = "Blocks broken: " + CosmoBlockMod.totalBlocksBroken + "/" + CosmoBlockMod.currentPhase.getTotalBlocks();
    }

    private static <T> T getRandomItem(List<T> list) {
        return list.get((int) (Math.random() * list.size()));
    }

    public static BlockState getRandomBlockState() {
        Block block = CosmoBlockMod.getRandomItem(CosmoBlockMod.currentPhase.getPossibleBlocks());

        if (block.getStringId().equals("base:light")) {
            return block.blockStates.values().toArray().random();
        }

        return block.getDefaultBlockState();
    }
}
