package nathanMeyer.mods.tmpi.proxy;

import nathanMeyer.mods.tmpi.TestModPleaseIgnore;
import nathanMeyer.mods.tmpi.command.CommandGamma;
import nathanMeyer.mods.tmpi.command.CommandSound;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@EventBusSubscriber(Dist.CLIENT)
public class ClientProxy extends nathanMeyer.mods.tmpi.proxy.CommonProxy{
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event){
	
	}
	
	@Override
	public void setup(final FMLCommonSetupEvent event){
		super.setup(event);
		TestModPleaseIgnore.LOGGER.info("ClientProxy preInit()");
	}
	
	@Override
	public void doClientStuff(final FMLClientSetupEvent event){
		super.doClientStuff(event);
		FMLJavaModLoadingContext.get().getModEventBus().register(CommandGamma.INSTANCE);
		FMLJavaModLoadingContext.get().getModEventBus().register(CommandSound.INSTANCE);
		TestModPleaseIgnore.LOGGER.info("ClientProxy doClientStuff()");
	}
	
	@Override
	public void enqueueIMC(final InterModEnqueueEvent event){
	
	}
	
	@Override
	public void processIMC(final InterModProcessEvent event){
	
	}
}