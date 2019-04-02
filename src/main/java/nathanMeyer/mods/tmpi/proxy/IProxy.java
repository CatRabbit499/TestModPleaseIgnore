package nathanMeyer.mods.tmpi.proxy;

import nathanMeyer.mods.tmpi.init.ModBlocks;
import nathanMeyer.mods.tmpi.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static nathanMeyer.mods.tmpi.TestModPleaseIgnore.LOGGER;

public interface IProxy{
	void setup(final FMLCommonSetupEvent event);
	
	@EventBusSubscriber(bus = Bus.MOD)
	class RegistryEvents{
		@SubscribeEvent
		public static void registerTiles(RegistryEvent.Register<TileEntityType<?>> event){
			LOGGER.info("registerTiles({})",event);
			ModBlocks.registerTiles(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event){
			LOGGER.info("registerBlocks({})",event);
			ModBlocks.registerBlocks(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event){
			LOGGER.info("registerBlocks({})",event);
			ModItems.registerItems(event.getRegistry());
		}
	}
}
