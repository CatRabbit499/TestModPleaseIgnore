package nathanMeyer.mods.tmpi.client.gui;

import nathanMeyer.mods.tmpi.client.gui.inventory.ContainerInvsee;
import nathanMeyer.mods.tmpi.client.gui.inventory.GUIInvsee;
import nathanMeyer.mods.tmpi.constants.LocationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
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
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GUIHandler{
	public static ResourceLocation GUI_INVSEE_RL = LocationManager.getLocation("invsee_rl");
	
	public static void openInvseeGui(EntityPlayerMP sender,EntityPlayerMP target){
		InvseeServer invseeServer = new InvseeServer(sender,target);
		NetworkHooks.openGui(sender,invseeServer,invseeServer::encode);
	}
	
	public static class InvseeServer implements IInteractionObject{
		private final EntityPlayerMP sender;
		private final EntityPlayerMP other;
		
		public InvseeServer(EntityPlayerMP sender,EntityPlayerMP other){
			this.sender = sender;
			this.other = other;
		}
		
		@Override
		public Container createContainer(InventoryPlayer playerInventory,EntityPlayer playerEntity){
			return new ContainerInvsee(sender.inventory,other.inventory);
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
		
		@Override
		public ITextComponent getCustomName(){
			return null;
		}
		
		public void encode(PacketBuffer packetBuffer){
			packetBuffer.writeBytes(toBytes(sender));
			packetBuffer.writeBytes(toBytes(other));
		}
		
		private byte[] toBytes(EntityPlayerMP playerMP){
			byte[] stream = null;
			try(
					ByteArrayOutputStream baos = new ByteArrayOutputStream();ObjectOutputStream oos = new ObjectOutputStream(baos)
			){
				oos.writeObject(playerMP);
				stream = baos.toByteArray();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			return stream;
		}
	}
	
	public static class InvseeClient{
		public static GuiScreen getInvseeGui(FMLPlayMessages.OpenContainer message){
			ResourceLocation invID = message.getId();
			System.out.println("InvseeClient GUI Element: " + invID);
			if(invID.equals(GUI_INVSEE_RL)){
				Minecraft mc = Minecraft.getInstance();
				PacketBuffer data = message.getAdditionalData();
				EntityPlayerMP sender = fromBytes(data.readByteArray());
				EntityPlayerMP target = fromBytes(data.readByteArray());
				return new GUIInvsee(sender.inventory,target.inventory);
			}
			return null;
		}
		
		private static EntityPlayerMP fromBytes(byte[] stream){
			EntityPlayerMP playerMP = null;
			try(
					ByteArrayInputStream bais = new ByteArrayInputStream(stream);ObjectInputStream ois = new ObjectInputStream(bais)
			){
				playerMP = (EntityPlayerMP)ois.readObject();
			}
			catch(IOException|ClassNotFoundException e){
				e.printStackTrace();
			}
			return playerMP;
		}
	}
}
