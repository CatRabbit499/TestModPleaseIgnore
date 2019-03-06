package nathanMeyer.mods.tmpi.items;

import nathanMeyer.mods.tmpi.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class ItemBasic extends ItemBlock{
	public ItemBasic(String name,Block blockIn,Item.Properties properties){
		super(blockIn,properties);
		setRegistryName(name);
		properties.group(ModItems.tabTMPI);
	}
}