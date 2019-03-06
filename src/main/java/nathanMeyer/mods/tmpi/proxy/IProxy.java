package nathanMeyer.mods.tmpi.proxy;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public interface IProxy{
	void setup(final FMLCommonSetupEvent event);
	
	void doClientStuff(final FMLClientSetupEvent event);
	
	void enqueueIMC(final InterModEnqueueEvent event);
	
	void processIMC(final InterModProcessEvent event);
	
	void serverStarting(final FMLServerStartingEvent event);
}