package me.theentropyshard.cosmoblock.mixins;

import com.badlogic.gdx.math.Vector3;
import me.theentropyshard.cosmoblock.CosmoBlockMod;
import finalforeach.cosmicreach.world.Zone;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Zone.class)
public class ZoneMixin {
    @Shadow
    public Vector3 spawnPoint;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public boolean validateSpawnPoint() {
        return true;
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public boolean calculateSpawn() {
        this.spawnPoint = CosmoBlockMod.SPAWN_POINT;

        return true;
    }
}
