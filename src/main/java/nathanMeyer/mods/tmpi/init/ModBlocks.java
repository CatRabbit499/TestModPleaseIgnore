package nathanMeyer.mods.tmpi.init;

import nathanMeyer.mods.tmpi.blocks.BlockPlayerInventory;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ModBlocks{
	public static Block invBlock;
	
	public static void init(){
		invBlock = new BlockPlayerInventory("inventory_block",Material.ROCK);
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event){
		event.getRegistry().registerAll(invBlock);
	}
	
	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event){
		event.getRegistry()
		     .registerAll(new ItemBlock(invBlock,new Item.Properties().addToolType(ToolType.PICKAXE,2).group(ModItems.tabTMPI))
				     .setRegistryName("tmpi","inv_block"));
	}
	
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event){
		//registerRender(invBlock.asItem());
	}
	
	public static void registerRender(Item item){
		//ModelLoader.defaultModelGetter().apply(new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()),"inventory"));
	}
}