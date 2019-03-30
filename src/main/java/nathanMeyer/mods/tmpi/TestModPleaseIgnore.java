package nathanMeyer.mods.tmpi;

import config.TMPIConfig;
import nathanMeyer.mods.tmpi.client.gui.GUIHandler;
import nathanMeyer.mods.tmpi.command.CommandGamma;
import nathanMeyer.mods.tmpi.command.CommandInvsee;
import nathanMeyer.mods.tmpi.command.CommandSound;
import nathanMeyer.mods.tmpi.command.CommandTestModPleaseIgnore;
import nathanMeyer.mods.tmpi.init.ModBlocks;
import nathanMeyer.mods.tmpi.init.ModItems;
import nathanMeyer.mods.tmpi.proxy.ClientProxy;
import nathanMeyer.mods.tmpi.proxy.CommonProxy;
import nathanMeyer.mods.tmpi.proxy.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
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
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod(TestModPleaseIgnore.MODID)
public class TestModPleaseIgnore{
	public static final String MODID = "tmpi";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	private static final String CHANNEL = MODID;
	private static final String PROTOCOL_VERSION = "1.0";
	public static String NAME = "Test Mod Please Ignore";
	public static String VERSION = "${Version}";
	public static SimpleChannel channel = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(MODID,CHANNEL))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(()->PROTOCOL_VERSION)
			.simpleChannel();
	private static CommonProxy PROXY = DistExecutor.runForDist(()->ClientProxy::new,()->ServerProxy::new);
	
	public TestModPleaseIgnore(){
		FluidRegistry.enableUniversalBucket();
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,TMPIConfig.commonSpec,"tmpi.toml");
		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::enqueueIMC);
		modEventBus.addListener(this::processIMC);
		modEventBus.addListener(this::doClientStuff);
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY,()->GUIHandler.InvseeClient::getInvseeGui);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event){
		LOGGER.info("Main setup({})",event);
		PROXY.setup(event);
	}
	
	private void enqueueIMC(final InterModEnqueueEvent event){
		InterModComms.sendTo("forge","helloworld",()->{
			LOGGER.info("Main enqueueIMC({})",event);
			return "Hello world";
		});
		PROXY.enqueueIMC(event);
	}
	
	private void processIMC(final InterModProcessEvent event){
		LOGGER.info("Main processIMC({})",event);
		LOGGER.info("Got IMC: {}",event.getIMCStream().map(m->m.getMessageSupplier().get()).collect(Collectors.toList()));
		PROXY.processIMC(event);
	}
	
	private void doClientStuff(final FMLClientSetupEvent event){
		LOGGER.info("Main doClientStuff({})",event);
		PROXY.doClientStuff(event);
	}
	
	@EventBusSubscriber(bus = Bus.FORGE)
	public static class ServerEvents{
		@SubscribeEvent
		public static void onServerStarting(FMLServerStartingEvent event){
			LOGGER.info("onServerStarting({})",event);
			CommandTestModPleaseIgnore.register(event.getCommandDispatcher());
			CommandInvsee.register(event.getCommandDispatcher());
			CommandGamma.register(event.getCommandDispatcher());
			CommandSound.register(event.getCommandDispatcher());
		}
	}
	
	@EventBusSubscriber(bus = Bus.MOD)
	public static class RegistryEvents{
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