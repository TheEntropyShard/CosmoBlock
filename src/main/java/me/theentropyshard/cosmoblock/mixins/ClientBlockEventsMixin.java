package me.theentropyshard.cosmoblock.mixins;

import com.badlogic.gdx.math.Vector3;
import me.theentropyshard.cosmoblock.CosmoBlockMod;
import finalforeach.cosmicreach.ClientBlockEvents;
import finalforeach.cosmicreach.blocks.BlockPosition;
import finalforeach.cosmicreach.blocks.BlockState;
import finalforeach.cosmicreach.world.Zone;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientBlockEvents.class)
public abstract class ClientBlockEventsMixin {

    @Shadow
    public abstract boolean placeBlock(Zone zone, BlockState targetBlockState, BlockPosition blockPos, Vector3 intersection);

    @Inject(method = "breakBlock", at = @At("TAIL"))
    private void onBlockBreak(Zone zone, BlockPosition blockPos, double timeSinceLastInteract, CallbackInfo ci) {
        if (blockPos.getGlobalX() != 7 || blockPos.getGlobalY() != 111 || blockPos.getGlobalZ() != 7) {
            return;
        }

        CosmoBlockMod.brokeBlock();

        blockPos.localX = CosmoBlockMod.THE_BLOCK_X;
        blockPos.localY = CosmoBlockMod.THE_BLOCK_Y;
        blockPos.localZ = CosmoBlockMod.THE_BLOCK_Z;

        this.placeBlock(zone, CosmoBlockMod.getRandomBlockState(), blockPos, Vector3.Zero);
    }
}
