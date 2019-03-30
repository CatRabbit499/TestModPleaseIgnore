package nathanMeyer.mods.tmpi.proxy;

import nathanMeyer.mods.tmpi.TestModPleaseIgnore;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ServerProxy extends CommonProxy{
	@Override
	public void setup(final FMLCommonSetupEvent event){
		super.setup(event);
		TestModPleaseIgnore.LOGGER.info("ServerProxy preInit()");
	}
	
	@Override
	public void enqueueIMC(final InterModEnqueueEvent event){
	
	}
	
	@Override
	public void processIMC(final InterModProcessEvent event){
	
	}
	
	@SubscribeEvent
	public void serverStarting(FMLServerStartingEvent event){
		TestModPleaseIgnore.LOGGER.info("ServerProxy serverStarting()");
	}
}