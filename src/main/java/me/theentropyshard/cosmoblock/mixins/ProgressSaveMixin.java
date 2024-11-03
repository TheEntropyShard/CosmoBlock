package me.theentropyshard.cosmoblock.mixins;

import finalforeach.cosmicreach.io.ChunkSaver;
import finalforeach.cosmicreach.io.SaveLocation;
import finalforeach.cosmicreach.world.World;
import me.theentropyshard.cosmoblock.CosmoBlockMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Mixin(ChunkSaver.class)
public class ProgressSaveMixin {
    @Inject(method = "saveWorld", at = @At("TAIL"))
    private static void saveProgress(World world, CallbackInfo ci) {
        String worldSaveFolderLocation = SaveLocation.getWorldSaveFolderLocation(world);
        try {
            Path dir = Paths.get(worldSaveFolderLocation);
            Files.createDirectories(dir);

            Path file = dir.resolve("cosmoblock_progress.txt");

            String s = CosmoBlockMod.totalBlocksBroken + "\n";
            s += CosmoBlockMod.currentPhase.getName();

            Files.writeString(file, s, StandardCharsets.UTF_8);
        } catch (IOException e) {
            CosmoBlockMod.LOGGER.error("Could not save progress", e);
        }
    }
}
