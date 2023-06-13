package user.nicolai.codeteach.container;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import user.nicolai.codeteach.CodeTeach;

public class DisplayTeachScreen extends AbstractContainerScreen<TeachContainer> {

    public static ResourceLocation TEXTURE = new ResourceLocation(CodeTeach.MODID, "textures/gui/teach_menu.png"); //Texturen af menuen

    public DisplayTeachScreen(TeachContainer p_97741_, Inventory p_97742_, Component p_97743_) { //Opretter skærmen
        super(p_97741_, p_97742_, p_97743_);
    }
    @Override
    protected void init() { //Initialize
        super.init();
    }
    @Override
    protected void renderBg(PoseStack poseStack, float ParticalTick, int MouseX, int MouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader); //Sætter skærmen til at skulle vise en ny overlay (menu)
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F); //Sætter farven på baggrunden i engine
        RenderSystem.setShaderTexture(0, TEXTURE); //Sætter texturen på baggrunden i engine
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(poseStack, x, y, 0, 0, imageWidth, imageHeight); //Renderer menuen på skærmen til brugeren ud efter størrelsen af billedet x og y
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack); //Render teksten over menuen
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
