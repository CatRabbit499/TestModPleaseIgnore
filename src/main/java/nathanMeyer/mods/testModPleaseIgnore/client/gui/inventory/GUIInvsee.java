package nathanMeyer.mods.testModPleaseIgnore.client.gui.inventory;

import nathanMeyer.mods.testModPleaseIgnore.constants.DecimalColors;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GUIInvsee extends GuiContainer{
    public GUIInvsee(InventoryPlayer playerSenderInventory, InventoryPlayer playerTargetInventory, World world, BlockPos table){
        super(new ContainerInvsee(playerSenderInventory,playerTargetInventory,world,table));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("tmpi:textures/gui/invseegui.png"));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j-17, 0, 0, this.xSize, this.ySize + 27);
    }

    public static String remotePlayerName;
    String localname = "${Otherplayer}";
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        GlStateManager.color(1.0F,1.0F,1.0F);
        if(remotePlayerName != null && !remotePlayerName.equalsIgnoreCase(localname)){
            localname = this.getRemotePlayerName();
        }
        this.fontRenderer.drawString(localname,7,-10, DecimalColors.DARK_GREEN.decimal);
        this.fontRenderer.drawString(I18n.format("tmpi.gui.invself"),7,84, DecimalColors.DARK_GRAY.decimal);
        remotePlayerName = null;
    }

    public String getRemotePlayerName(){
        return remotePlayerName;
    }
}
