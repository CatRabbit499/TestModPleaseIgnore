package nathanMeyer.mods.tmpi.blocks;

import nathanMeyer.mods.tmpi.TestModPleaseIgnore;
import nathanMeyer.mods.tmpi.client.gui.GUIHandler;
import nathanMeyer.mods.tmpi.util.LocationManager;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockPlayerInventory extends BlockBasic{
	public static ResourceLocation INV_BLOCK_RL;
	
	public BlockPlayerInventory(){
		super(Material.ROCK,INV_BLOCK_RL = LocationManager.getLocation("invblock"));
	}
	
	@SubscribeEvent
	public void onInteract(PlayerInteractEvent event){
		EntityPlayer player = event.getEntityPlayer();
		onActivated(
				event.getWorld(),
				event.getPos(),
				event.getEntityPlayer(),
				event.getHand(),
				event.getFace(),
				event.getPos().getX(),
				event.getPos().getY(),
				event.getPos().getZ()
		);
	}
	
	private void onActivated(World world,BlockPos blockPos,EntityPlayer player,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ){
		if(!world.isRemote){
			TestModPleaseIgnore.LOGGER.info("BlockPlayerInventory onBlockActivated");
			GUIHandler.openInvseeGui((EntityPlayerMP)player,(EntityPlayerMP)player);
		}
	}
}