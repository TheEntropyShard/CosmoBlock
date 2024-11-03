package me.theentropyshard.cosmoblock.mixins;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.items.ItemCatalog;
import finalforeach.cosmicreach.ui.UIElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemCatalog.class)
public class ItemCatalogMixin {
    @Shadow
    UIElement buttonPrevPage;

    @Shadow
    UIElement buttonNextPage;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void render(Viewport uiViewport, ShapeRenderer shapeRenderer) {

    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void drawItems(Viewport uiViewport) {

    }

    @Inject(method = "show", at = @At("TAIL"))
    private void removeObjects(CallbackInfo ci) {
        GameState.IN_GAME.uiObjects.removeValue(this.buttonPrevPage, true);
        GameState.IN_GAME.uiObjects.removeValue(this.buttonNextPage, true);
    }
}
