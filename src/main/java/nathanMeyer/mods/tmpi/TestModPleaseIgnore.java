package nathanMeyer.mods.tmpi;

import nathanMeyer.mods.tmpi.client.gui.GUIHandler;
import nathanMeyer.mods.tmpi.config.TMPIConfig;
import nathanMeyer.mods.tmpi.proxy.ClientProxy;
import nathanMeyer.mods.tmpi.proxy.IProxy;
import nathanMeyer.mods.tmpi.proxy.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod(TestModPleaseIgnore.MODID)
public class TestModPleaseIgnore{
	public static final String MODID = "tmpi";
	public static final String VERSION = "${Version}";
	public static final String NAME = "Test Mod Please Ignore";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	private static final IProxy PROXY = DistExecutor.runForDist(()->ClientProxy::new,()->ServerProxy::new);
	
	public TestModPleaseIgnore(){
		FluidRegistry.enableUniversalBucket();
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(Type.COMMON,TMPIConfig.commonSpec,"tmpi.toml");
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::enqueueIMC);
		modEventBus.addListener(this::processIMC);
		modEventBus.addListener(this::doClientStuff);
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY,()->GUIHandler.InvseeClient::openGuiScreen);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event){
		PROXY.setup(event);
	}
	
	private void enqueueIMC(final InterModEnqueueEvent event){
		InterModComms.sendTo("forge","helloworld",()->{
			LOGGER.info("Main enqueueIMC({})",event);
			return "Hello world";
		});
	}
	
	private void processIMC(final InterModProcessEvent event){
		LOGGER.info("Main processIMC(): {}",event.getIMCStream().map(m->m.getMessageSupplier().get()).collect(Collectors.toList()));
	}
	
	private void doClientStuff(final FMLClientSetupEvent event){
		LOGGER.info("Main doClientStuff({})",event);
	}
}