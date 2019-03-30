package nathanMeyer.mods.tmpi.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

class ItemBlockBasic extends ItemBlock{
	ItemBlockBasic(Block blockIn,ResourceLocation location,Item.Properties properties){
		super(blockIn,properties);
		setRegistryName(location);
	}
}