package nathanMeyer.mods.tmpi.client.gui;

import mcp.MethodsReturnNonnullByDefault;
import nathanMeyer.mods.tmpi.client.gui.inventory.ContainerInvsee;
import nathanMeyer.mods.tmpi.client.gui.inventory.GUIInvsee;
import nathanMeyer.mods.tmpi.util.ChatUtils;
import nathanMeyer.mods.tmpi.util.LocationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static nathanMeyer.mods.tmpi.TestModPleaseIgnore.LOGGER;

public class GUIHandler{
	public static ResourceLocation GUI_INVSEE_RL = LocationManager.getLocation("invsee_rl");
	
	public static void openInvseeGui(EntityPlayerMP sender,EntityPlayerMP target){
		InvseeServer invseeServer = new InvseeServer(sender,target);
		NetworkHooks.openGui(sender,invseeServer,invseeServer::encode);
	}
	
	public static class InvseeClient{
		public static GuiScreen openGuiScreen(FMLPlayMessages.OpenContainer message){
			ResourceLocation invID = message.getId();
			LOGGER.info("openGuiScreen({})",invID);
			if(invID.equals(GUI_INVSEE_RL)){
				Minecraft mc = Minecraft.getInstance();
				PacketBuffer data = message.getAdditionalData();
				EntityPlayer sender = (EntityPlayer)mc.world.getWorld().getEntityByID(data.readInt());
				EntityPlayer target = (EntityPlayer)mc.world.getWorld().getEntityByID(data.readInt());
				assert sender!=null;
				assert target!=null;
				CommandSource source = sender.getCommandSource();
				LOGGER.info("{}[{}] : {}[{}]",
					sender.getClass().getName(),
					sender.getScoreboardName(),
					target.getClass().getName(),
					target.getScoreboardName()
				);
				ChatUtils.respondf(true,source,"[Client] {}.openGuiScreen({})",sender.getName(),invID);
				return new GUIInvsee(mc.player.inventory,target.inventory,mc.player);
			}
			return null;
		}
	}
	
	@MethodsReturnNonnullByDefault
	@ParametersAreNonnullByDefault
	public static class InvseeServer implements IInteractionObject{
		private final EntityPlayerMP sender;
		private final EntityPlayerMP other;
		
		public InvseeServer(EntityPlayerMP sender,EntityPlayerMP other){
			this.sender = sender;
			this.other = other;
		}
		
		@Override
		public Container createContainer(InventoryPlayer playerInventory,EntityPlayer playerEntity){
			ChatUtils.respond(playerEntity.getCommandSource(),true,"[Server] createContainer(playerInventory,playerEntity)");
			assert playerInventory.equals(sender.inventory) : playerInventory + "=/=" + sender.inventory;
			return new ContainerInvsee(sender.inventory,other.inventory,sender);
		}
		
		@Override
		public String getGuiID(){
			return GUI_INVSEE_RL.toString();
		}
		
		@Override
		public ITextComponent getName(){
			return new TextComponentString(GUI_INVSEE_RL.toString());
		}
		
		@Override
		public boolean hasCustomName(){
			return false;
		}
		
		@Nullable
		@Override
		public ITextComponent getCustomName(){
			return new TextComponentString("Mr. Poopy Butthole");
		}
		
		public void encode(PacketBuffer packetBuffer){
			packetBuffer.writeInt(sender.getEntity().getEntityId());
			packetBuffer.writeInt(other.getEntity().getEntityId());
		}
	}
}
