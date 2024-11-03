package me.theentropyshard.cosmoblock.mixins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.theentropyshard.cosmoblock.CosmoBlockMod;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.UI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UI.class)
public class UiMixin {

    @Unique
    private static final Color background = new Color(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, 0.4f);

    @Shadow
    public static boolean renderUI;

    @Shadow
    private Viewport uiViewport;

    @Shadow
    ShapeRenderer shapeRenderer;

    @Inject(method = "render", at = @At("TAIL"))
    private void drawBrokenBlocks(CallbackInfo ci) {
        if (!UiMixin.renderUI) {
            return;
        }

        Vector2 textDim = new Vector2();
        FontRenderer.getTextDimensions(this.uiViewport, CosmoBlockMod.stats, textDim);
        float textX = this.uiViewport.getWorldWidth() / 2 - textDim.x;
        float textY = 0 - textDim.y / 2;

        Gdx.gl.glEnable(3042);
        Gdx.gl.glDepthFunc(519);
        Gdx.gl.glBlendFunc(770, 771);
        Gdx.gl.glCullFace(1028);

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        this.shapeRenderer.setColor(UiMixin.background);
        this.shapeRenderer.rect(textX - 5, textY - 5, textX + textDim.x + 15, (textY + textDim.y + 15) * 2 + 1);
        this.shapeRenderer.end();
    }
}
