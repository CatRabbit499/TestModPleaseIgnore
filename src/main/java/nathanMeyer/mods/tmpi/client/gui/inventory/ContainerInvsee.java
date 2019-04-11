package nathanMeyer.mods.tmpi.client.gui.inventory;

import mcp.MethodsReturnNonnullByDefault;
import nathanMeyer.mods.tmpi.util.ChatUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ContainerInvsee extends Container{
	private IInventory invSender;
	private IInventory invTarget;
	private EntityPlayer player;
	
	public ContainerInvsee(IInventory sender,IInventory target,EntityPlayer player){
		this.invSender = sender;
		this.invTarget = target;
		this.player = player;
		ChatUtils.respond(getPlayer().getCommandSource(),true,"[Client] ContainerInvsee(sender,target,player)!");
		layoutGui();
		invTarget.openInventory(this.player);
	}
	
	private void layoutGui(){
		ChatUtils.respond(getPlayer().getCommandSource(),true,"[Client] layoutGui()");
		for(int k = 0;k<3;++k) // Target Main
			for(int i1 = 0;i1<9;++i1)
				this.inventorySlots.add(new Slot(invTarget,i1 + k*9 + 9,8 + i1*18,k*18));
		for(int l = 0;l<9;++l) // Target Hotbar
			this.inventorySlots.add(new Slot(invTarget,l,8 + l*18,58));
		/* ------ Intended Inventory Divider ------ */
		for(int k = 0;k<3;++k) // Sender Main
			for(int i1 = 0;i1<9;++i1)
				this.inventorySlots.add(new Slot(invSender,i1 + k*9 + 9,8 + i1*18,k*18 + 94));
		for(int l = 0;l<9;++l) // Sender Hotbar
			this.inventorySlots.add(new Slot(invSender,l,8 + l*18,58 + 94));
	}
	
	public IInventory getInvTarget(){
		return this.invTarget;
	}
	public EntityPlayer getPlayer(){
		return this.player;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn){
		return true;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn){
		super.onContainerClosed(playerIn);
		this.invTarget.closeInventory(playerIn);
	}
	
	@Override
	public void detectAndSendChanges(){
		try{
			super.detectAndSendChanges();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}