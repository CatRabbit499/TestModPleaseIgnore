package nathanMeyer.mods.tmpi.client.gui.inventory;

import nathanMeyer.mods.tmpi.util.GLColors;
import nathanMeyer.mods.tmpi.util.LocationManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GUIInvsee extends GuiContainer{
	private static final ResourceLocation GUI_INVSEE_BG = LocationManager.getLocation("textures/gui/invseegui.png");
	public static String remotePlayerName;
	private String localname = "${Otherplayer}";
	
	public GUIInvsee(IInventory playerSenderInventory,IInventory playerTargetInventory,EntityPlayer playerSender){
		super(new ContainerInvsee(playerSenderInventory,playerTargetInventory,playerSender));
	}
	
	@Override
	public void render(int mouseX,int mouseY,float partialTicks){
		this.drawDefaultBackground();
		super.render(mouseX,mouseY,partialTicks);
		this.renderHoveredToolTip(mouseX,mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX,int mouseY){
		GlStateManager.color3f(1.0F,1.0F,1.0F);
		if(remotePlayerName!=null && !remotePlayerName.equalsIgnoreCase(localname)){
			localname = this.getRemotePlayerName();
		}
		this.fontRenderer.drawString(localname,7,-10,GLColors.DARK_GREEN.decimal);
		this.fontRenderer.drawString(I18n.format("tmpi.gui.invself"),7,84,GLColors.DARK_GRAY.decimal);
		remotePlayerName = null;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,int mouseX,int mouseY){
		GlStateManager.color4f(1.0F,1.0F,1.0F,1.0F);
		this.mc.getTextureManager().bindTexture(GUI_INVSEE_BG);
		int i = (this.width - this.xSize)/2;
		int j = (this.height - this.ySize)/2;
		this.drawTexturedModalRect(i,j - 17,0,0,this.xSize,this.ySize + 27);
	}
	
	@Override
	public boolean allowCloseWithEscape(){
		return true;
	}
	
	@Override
	public boolean doesGuiPauseGame(){
		return true;
	}
	
	private String getRemotePlayerName(){
		return remotePlayerName;
	}
}
