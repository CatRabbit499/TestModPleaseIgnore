package nathanMeyer.mods.tmpi;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import config.TMPIConfig;
import nathanMeyer.mods.tmpi.client.gui.GUIHandler;
import nathanMeyer.mods.tmpi.command.CommandGamma;
import nathanMeyer.mods.tmpi.command.CommandInvsee;
import nathanMeyer.mods.tmpi.command.CommandSound;
import nathanMeyer.mods.tmpi.command.CommandTestModPleaseIgnore;
import nathanMeyer.mods.tmpi.init.ModBlocks;
import nathanMeyer.mods.tmpi.proxy.ClientProxy;
import nathanMeyer.mods.tmpi.proxy.CommonProxy;
import nathanMeyer.mods.tmpi.proxy.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.stream.Collectors;

@Mod(TestModPleaseIgnore.MODID)
public class TestModPleaseIgnore{
	public static final String MODID = "tmpi";
	public static final String INVENTORY_ID_KEY = "InventoryId";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	private static final String CHANNEL = MODID;
	private static final String PROTOCOL_VERSION = "1.0";
	public static String NAME = "Test Mod Please Ignore";
	public static String VERSION = "${Version}";
	public static GUIHandler guiHandler = new GUIHandler();
	public static SimpleChannel channel = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(MODID,CHANNEL))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(()->PROTOCOL_VERSION)
			.simpleChannel();
	private static TestModPleaseIgnore INSTANCE;
	private static CommonProxy PROXY = DistExecutor.runForDist(()->ClientProxy::new,()->ServerProxy::new);
	
	public TestModPleaseIgnore(){
		INSTANCE = this;
		FluidRegistry.enableUniversalBucket();
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT,TMPIConfig.clientSpec);
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER,TMPIConfig.serverSpec);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,TMPIConfig.commonSpec);
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::enqueueIMC);
		modEventBus.addListener(this::processIMC);
		modEventBus.addListener(this::doClientStuff);
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY,()->GUIHandler.InvseeClient::getInvseeGui);
		//modEventBus.register(ForgeConfig.class);
		loadConfig(TMPIConfig.clientSpec,FMLPaths.CONFIGDIR.get().resolve("tmpi-client.toml"));
		loadConfig(TMPIConfig.serverSpec,FMLPaths.CONFIGDIR.get().resolve("tmpi-server.toml"));
		loadConfig(TMPIConfig.commonSpec,FMLPaths.CONFIGDIR.get().resolve("tmpi.toml"));
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public static void loadConfig(ForgeConfigSpec spec,Path path){
		TestModPleaseIgnore.LOGGER.debug("Loading config file {}",path);
		final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
		TestModPleaseIgnore.LOGGER.debug("Built TOML config for {}",path.toString());
		configData.load();
		TestModPleaseIgnore.LOGGER.debug("Loaded TOML config file {}",path.toString());
		spec.setConfig(configData);
	}
	
	private void setup(final FMLCommonSetupEvent event){
		LOGGER.info("Main setup()");
		System.out.println(TMPIConfig.SERVER.modVersion.get());
		//CONFIG = new TMPIConfig();
		PROXY.setup(event);
	}
	
	private void enqueueIMC(final InterModEnqueueEvent event){
		LOGGER.info("Main enqueueIMC()");
		InterModComms.sendTo("forge","helloworld",()->{
			LOGGER.info("Main enqueueIMC()");
			return "Hello world";
		});
		PROXY.enqueueIMC(event);
	}
	
	private void processIMC(final InterModProcessEvent event){
		LOGGER.info("Main processIMC()");
		LOGGER.info("Got IMC",event.getIMCStream().map(m->m.getMessageSupplier().get()).collect(Collectors.toList()));
		PROXY.processIMC(event);
	}
	
	private void doClientStuff(final FMLClientSetupEvent event){
		LOGGER.info("Main doClientStuff()");
		PROXY.doClientStuff(event);
	}
	
	public static TestModPleaseIgnore getInstance(){
		return INSTANCE;
	}
	
	@SubscribeEvent
	public void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent){
		LOGGER.info("HELLO from Register Block");
		ModBlocks.registerBlocks(blockRegistryEvent);
	}
	
	@Mod.EventBusSubscriber
	public static class RegistryEvents{
		@SubscribeEvent
		public static void onServerStarting(FMLServerStartingEvent event){
			LOGGER.info("serverStarting()");
			CommandTestModPleaseIgnore.register(event.getCommandDispatcher());
			CommandInvsee.register(event.getCommandDispatcher());
			CommandGamma.register(event.getCommandDispatcher());
			CommandSound.register(event.getCommandDispatcher());
		}
	}
}
