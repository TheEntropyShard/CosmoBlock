package me.theentropyshard.cosmoblock.mixins;

import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.io.SaveLocation;
import finalforeach.cosmicreach.world.World;
import me.theentropyshard.cosmoblock.CosmoBlockMod;
import me.theentropyshard.cosmoblock.stage.Phase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Mixin(InGame.class)
public class ProgressLoadMixin {
    @Inject(method = "loadWorld(Lfinalforeach/cosmicreach/world/World;)V", at = @At("TAIL"))
    private void loadProgress(World world, CallbackInfo ci) {
        try {
            String worldSaveFolderLocation = SaveLocation.getWorldSaveFolderLocation(world);
            Path dir = Paths.get(worldSaveFolderLocation);

            if (!Files.exists(dir)) {
                return;
            }

            Path file = dir.resolve("cosmoblock_progress.txt");

            if (!Files.exists(file)) {
                return;
            }

            List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

            if (lines.size() < 2) {
                return;
            }

            CosmoBlockMod.totalBlocksBroken = Integer.parseInt(lines.get(0));
            Phase phaseByName = CosmoBlockMod.getPhaseByName(lines.get(1));

            if (phaseByName == null) {
                CosmoBlockMod.totalBlocksBroken = 0;
                phaseByName = CosmoBlockMod.phases.get(0);
            }

            CosmoBlockMod.currentPhase = phaseByName;

            CosmoBlockMod.updateStats();
        } catch (IOException e) {
            CosmoBlockMod.LOGGER.error("Could not load progress", e);
        }
    }
}
