package config;

import nathanMeyer.mods.tmpi.TestModPleaseIgnore;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import static net.minecraftforge.fml.Logging.CORE;

@Mod.EventBusSubscriber
public class TMPIConfig{
	
	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent){
		TestModPleaseIgnore.LOGGER.debug("Loaded {} config file {}",TestModPleaseIgnore.MODID,configEvent.getConfig().getFileName());
		
	}
	
	@SubscribeEvent
	public static void onFileChange(final ModConfig.ConfigReloading configEvent){
		TestModPleaseIgnore.LOGGER.fatal(CORE,"{} config just got changed on the file system!",TestModPleaseIgnore.MODID);
	}
	
	
	public static final ForgeConfigSpec clientSpec;
	public static final TMPIConfig.Client CLIENT;
	public static final ForgeConfigSpec serverSpec;
	public static final TMPIConfig.Server SERVER;
	public static final ForgeConfigSpec commonSpec;
	public static final TMPIConfig.Common COMMON;
	
	static{
		final Pair<Client,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
		clientSpec = specPair.getRight();
		CLIENT = specPair.getLeft();
	}
	
	static{
		final Pair<TMPIConfig.Server,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
		serverSpec = specPair.getRight();
		SERVER = specPair.getLeft();
	}
	
	static{
		final Pair<TMPIConfig.Common,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
	
	public static class Server{
		public final ForgeConfigSpec.DoubleValue modVersion;
		
		Server(ForgeConfigSpec.Builder builder){
			builder.comment("Server only settings, mostly logging options").push("server");
			modVersion = builder.comment("Mod version running on the server...").translation("lol").defineInRange("test",5.0,0,5);
			builder.pop();
		}
	}
	
	public static class Client{
		public final BooleanValue titleScreenMCVersion;
		public final BooleanValue titleScreenMCP;
		public final BooleanValue titleScreenForge;
		public final BooleanValue titleScreenMods;
		
		Client(ForgeConfigSpec.Builder builder){
			builder.comment("Client only settings").push("client");
			this.titleScreenMCVersion = builder
					.comment("Show which version of Minecraft the client is currently running.")
					.translation("tmpi.config_client.mcversion")
					.define("showMcVersion",true);
			this.titleScreenMCP = builder
					.comment("Show which version of Minecraft Coder Pack (MCP) the client is running.")
					.translation("bettertitlescreen.configgui.mcpVersion")
					.define("showMcpVersion",false);
			this.titleScreenForge = builder
					.comment("Show which version of Minecraft Forge the client is running.")
					.translation("bettertitlescreen.configgui.forgeVersion")
					.define("showForgeVersion",false);
			this.titleScreenMods = builder
					.comment("Show how many mods are loaded.")
					.translation("bettertitlescreen.configgui.modsLoaded")
					.define("showModsLoaded",false);
			builder.pop();
		}
	}
	
	public static class Common{
		public final BooleanValue testBooleanOne;
		public final IntValue testIntegerOne;
		public final LongValue testLongOne;
		public final DoubleValue testDoubleOne;
		
		public final BooleanValue testBooleanTwo;
		
		Common(Builder builder){
			builder.comment("Common settings to both server and client").push("common");
			testBooleanOne = builder
					.comment("Example boolean config value")
					.translation("tmpi.config.testBooleanOne")
					.define("testBooleanOne",true);
			testIntegerOne = builder
					.comment("Example integer config value")
					.translation("tmpi.config.testIntegerOne")
					.defineInRange("testIntegerOne",5,0,10);
			testLongOne = builder
					.comment("Example long config value")
					.translation("tmpi.config.testLongOne")
					.defineInRange("showForgeVersion",5L,0L,10L);
			testDoubleOne = builder
					.comment("Example long config value")
					.translation("tmpi.config.testDoubleOne")
					.defineInRange("testDoubleOne",1.0F,0.0F,10.0F);
			builder.pop();
			
			builder.comment("test").push("test");
			testBooleanTwo = builder.comment("comment").translation("langkey").define("testBooleanTwo",true);
			builder.pop();
		}
	}
}