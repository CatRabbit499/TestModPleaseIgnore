package nathanMeyer.mods.tmpi.items;

import nathanMeyer.mods.tmpi.util.LocationManager;
import nathanMeyer.mods.tmpi.init.ModItems;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

public class ItemIngot extends ItemBasic{
	public static ResourceLocation TEST_ITEM_RL;
	
	public ItemIngot(){
		super(
				TEST_ITEM_RL = LocationManager.getLocation("ingotitem"),
				new Properties().group(ModItems.tabTMPI).addToolType(ToolType.PICKAXE,1)
		);
	}
}
