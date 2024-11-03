package me.theentropyshard.cosmoblock.mixins;

import com.badlogic.gdx.math.Vector2;
import me.theentropyshard.cosmoblock.CosmoBlockMod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.UI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGame.class)
public class InGameMixin extends GameState {

    @WrapOperation(method = "loadWorld(Lfinalforeach/cosmicreach/world/World;)V", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/entities/player/Player;setPosition(FFF)V"))
    private void doShit(Player instance, float x, float y, float z, Operation<Void> original) {
        if (x == 0.0f && y == 300.0f && z == 0.0f) {
            original.call(instance, 7.5f, 112.0f, 7.5f);
        } else {
            original.call(instance, x, y, z);
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderBrokenBlocks(CallbackInfo ci) {
        if (!UI.renderUI) {
            return;
        }

        Vector2 textDim = new Vector2();
        FontRenderer.getTextDimensions(super.uiViewport, CosmoBlockMod.stats, textDim);

        GameState.batch.begin();

        String phaseText;

        if (CosmoBlockMod.finished) {
            phaseText = "< Finished >";
        } else {
            phaseText = "Phase: " + CosmoBlockMod.currentPhase.getName();
        }

        FontRenderer.drawText(
            GameState.batch,
            super.uiViewport,
            phaseText,
            (float) super.uiViewport.getScreenWidth() / 2 - textDim.x,
            0 - textDim.y / 2
        );

        FontRenderer.drawText(
            GameState.batch,
            super.uiViewport,
            CosmoBlockMod.stats,
            (float) super.uiViewport.getScreenWidth() / 2 - textDim.x,
            textDim.y
        );

        GameState.batch.end();
    }
}
