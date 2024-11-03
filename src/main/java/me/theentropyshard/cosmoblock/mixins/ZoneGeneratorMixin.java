package me.theentropyshard.cosmoblock.mixins;

import me.theentropyshard.cosmoblock.world.CosmoBlockZoneGenerator;
import finalforeach.cosmicreach.worldgen.ZoneGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZoneGenerator.class)
public class ZoneGeneratorMixin {
    @Inject(method = "registerZoneGenerators", at = @At("TAIL"))
    private static void registerCosmoBlockZoneGenerator(CallbackInfo ci) {
        ZoneGenerator.registerZoneGenerator(new CosmoBlockZoneGenerator());
    }
}
