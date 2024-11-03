package me.theentropyshard.cosmoblock.mixins;

import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.entities.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Shadow
    public transient Player player;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void onDeath() {
        this.player.inventory.dropAllItems(this.player.getZone(), this.player.getPosition());
    }
}
