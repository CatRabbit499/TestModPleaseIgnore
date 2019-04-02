package nathanMeyer.mods.tmpi.config;

import com.google.common.collect.Lists;
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
	public static final ForgeConfigSpec commonSpec;
	private static final TMPIConfig.Common COMMON;
	
	static{
		final Pair<TMPIConfig.Common,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}
	
	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent){
		TestModPleaseIgnore.LOGGER.debug("Loaded {} nathanMeyer.mods.tmpi.config file {}",TestModPleaseIgnore.MODID,
			configEvent.getConfig().getFileName()
		);
		
	}
	
	@SubscribeEvent
	public static void onFileChange(final ModConfig.ConfigReloading configEvent){
		TestModPleaseIgnore.LOGGER.fatal(CORE,"{} nathanMeyer.mods.tmpi.config just got changed on the file system!",
			TestModPleaseIgnore.MODID
		);
	}
	
	public static class Server{
		final ForgeConfigSpec.DoubleValue modVersion;
		
		Server(ForgeConfigSpec.Builder builder){
			builder.comment("Server only settings, mostly logging options").push("server");
			modVersion = builder
				.comment("Mod version running on the server...")
				.translation("tmpi.nathanMeyer.mods.tmpi.config.server.modVersion")
				.defineInRange("modVersion",5.0,0,5);
			builder.pop();
		}
	}
	
	public static class Client{
		final BooleanValue testClientSettingOne;
		
		Client(ForgeConfigSpec.Builder builder){
			builder.comment("Client only settings").push("client");
			this.testClientSettingOne = builder
				.comment("Example boolean client setting")
				.translation("tmpi.nathanMeyer.mods.tmpi.config.client.testClientSettingOne")
				.define("testClientSettingOne",true);
			builder.pop();
		}
	}
	
	public static class Common{
		final BooleanValue testBooleanOne;
		final IntValue testIntegerOne;
		final LongValue testLongOne;
		final DoubleValue testDoubleOne;
		final ConfigValue<?> testListOne;
		
		Common(Builder builder){
			new Client(builder);
			new Server(builder);
			builder.comment("Common settings to both server and client").push("common");
			testBooleanOne = builder
				.comment("Example boolean nathanMeyer.mods.tmpi.config value")
				.translation("tmpi.nathanMeyer.mods.tmpi.config.testBooleanOne")
				.define("testBooleanOne",true);
			testIntegerOne = builder
				.comment("Example integer nathanMeyer.mods.tmpi.config value")
				.translation("tmpi.nathanMeyer.mods.tmpi.config.testIntegerOne")
				.defineInRange("testIntegerOne",5,0,10);
			testLongOne = builder
				.comment("Example long nathanMeyer.mods.tmpi.config value")
				.translation("tmpi.nathanMeyer.mods.tmpi.config.testLongOne")
				.defineInRange("testLongOne",5L,0L,10L);
			testDoubleOne = builder
				.comment("Example double nathanMeyer.mods.tmpi.config value")
				.translation("tmpi.nathanMeyer.mods.tmpi.config.testDoubleOne")
				.defineInRange("testDoubleOne",1.0F,0.0F,10.0F);
			testListOne = builder
				.comment("Example String list nathanMeyer.mods.tmpi.config value")
				.translation("tmpi.nathanMeyer.mods.tmpi.config.testListOne")
				.defineList("testList",Lists.newArrayList("one","two","three"),String.class::isInstance);
			builder.pop();
		}
	}
}