package nathanMeyer.mods.tmpi.proxy;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

@EventBusSubscriber
public class CommonProxy{
	public void setup(final FMLCommonSetupEvent event){
	
	}
	
	public void doClientStuff(final FMLClientSetupEvent event){
	
	}
	
	public void enqueueIMC(final InterModEnqueueEvent event){
	
	}
	
	public void processIMC(final InterModProcessEvent event){
	
	}
}
