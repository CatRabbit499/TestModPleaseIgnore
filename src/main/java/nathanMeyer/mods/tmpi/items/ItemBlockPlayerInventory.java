package nathanMeyer.mods.tmpi.items;

import nathanMeyer.mods.tmpi.util.LocationManager;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class ItemBlockPlayerInventory extends ItemBlockBasic{
	public static ResourceLocation TEST_ITEM_RL;
	
	public ItemBlockPlayerInventory(Block blockIn,Properties properties){
		super(blockIn,TEST_ITEM_RL = LocationManager.getLocation("invblock"),properties);
	}
}
