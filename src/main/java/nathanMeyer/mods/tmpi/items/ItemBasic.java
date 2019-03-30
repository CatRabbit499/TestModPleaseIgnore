package nathanMeyer.mods.tmpi.items;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemBasic extends Item{
	public ItemBasic(ResourceLocation location,Properties properties){
		super(properties);
		setRegistryName(location);
	}
}