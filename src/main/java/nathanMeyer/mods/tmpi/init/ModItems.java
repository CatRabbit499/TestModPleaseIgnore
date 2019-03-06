package nathanMeyer.mods.tmpi.init;

import nathanMeyer.mods.tmpi.items.ItemBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ModItems{
	private static Item testItem;
	public static final ItemGroup tabTMPI = new ItemGroup("tab_tmpi"){
		@Override
		public ItemStack createIcon(){
			return new ItemStack(testItem);
		}
		
		@Override
		public boolean hasSearchBar(){
			return true;
		}
	}.setBackgroundImageName("item_search.png");
	
	public static void init(){
		testItem = new ItemBasic("test_item",ModBlocks.invBlock,new Item.Properties().addToolType(ToolType.PICKAXE,1));
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event){
		event.getRegistry().registerAll(testItem);
	}
	
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event){
		registerRender(testItem);
	}
	
	private static void registerRender(Item item){
		//ModelLoader.defaultModelGetter().apply(new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()),"inventory"));
	}
}