package nathanMeyer.mods.tmpi.blocks;

import nathanMeyer.mods.tmpi.client.gui.GUIHandler;
import nathanMeyer.mods.tmpi.util.LocationManager;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockPlayerInventory extends BlockBasic{
	public static ResourceLocation INV_BLOCK_RL;
	
	public BlockPlayerInventory(){
		super(Material.ROCK,INV_BLOCK_RL = LocationManager.getLocation("invblock"));
	}
	
	@Override
	public boolean onBlockActivated(
		IBlockState state,World worldIn,BlockPos pos,EntityPlayer player,EnumHand hand,EnumFacing side,float hitX,float hitY,float hitZ
	){
		IInventory inventory = player.inventory;
		if(inventory!=null){
			if(player instanceof EntityPlayerMP && !(player instanceof FakePlayer)){
				EntityPlayerMP entityPlayerMP = (EntityPlayerMP)player;
				GUIHandler.openInvseeGui(entityPlayerMP,entityPlayerMP);
			}
			player.addStat(StatList.OPEN_CHEST);
		}
		return true;
	}
}