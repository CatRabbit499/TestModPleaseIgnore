package nathanMeyer.mods.tmpi.init;

import nathanMeyer.mods.tmpi.items.ItemBlockPlayerInventory;
import nathanMeyer.mods.tmpi.items.ItemIngot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class ModItems{
	@ObjectHolder("tmpi:ingotitem")
	public static ItemIngot itemIngot;
	
	@ObjectHolder("tmpi:invblock")
	public static ItemBlockPlayerInventory itemBlockPlayerInventory;
	
	public static final ItemGroup tabTMPI = new ItemGroup("tab_tmpi"){
		@Override
		public ItemStack createIcon(){
			return new ItemStack(itemIngot);
		}
		
		@Override
		public boolean hasSearchBar(){
			return true;
		}
	}.setBackgroundImageName("item_search.png");
	
	public static void registerItems(IForgeRegistry<Item> registry){
		registry.register(new ItemBlockPlayerInventory(ModBlocks.invBlock,new Item.Properties().group(tabTMPI)).getItem());
		registry.register(new ItemIngot());
	}
}