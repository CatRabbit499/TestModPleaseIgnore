package nathanMeyer.mods.tmpi.proxy;

import nathanMeyer.mods.tmpi.command.CommandGamma;
import nathanMeyer.mods.tmpi.command.CommandInvsee;
import nathanMeyer.mods.tmpi.command.CommandSound;
import nathanMeyer.mods.tmpi.command.CommandTestModPleaseIgnore;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import static nathanMeyer.mods.tmpi.TestModPleaseIgnore.LOGGER;

@EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ServerProxy implements IProxy{
	@Override
	public void setup(FMLCommonSetupEvent event){
	
	}
	
	@EventBusSubscriber(bus = Bus.FORGE)
	public static class ServerEvents{
		@SubscribeEvent
		public static void onServerStarting(FMLServerStartingEvent event){
			LOGGER.info("ServerProxy serverStarting()");
			CommandTestModPleaseIgnore.register(event.getCommandDispatcher());
			CommandInvsee.register(event.getCommandDispatcher());
			CommandGamma.register(event.getCommandDispatcher());
			CommandSound.register(event.getCommandDispatcher());
		}
	}
}