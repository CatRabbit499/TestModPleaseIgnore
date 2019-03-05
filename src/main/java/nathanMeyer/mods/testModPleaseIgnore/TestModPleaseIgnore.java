package nathanMeyer.mods.testModPleaseIgnore;

import nathanMeyer.mods.testModPleaseIgnore.client.gui.GUIHandler;
import nathanMeyer.mods.testModPleaseIgnore.command.CommandInvsee;
import nathanMeyer.mods.testModPleaseIgnore.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

@Mod(name = TestModPleaseIgnore.NAME,
     modid = TestModPleaseIgnore.MODID,
     version = TestModPleaseIgnore.VERSION,
     acceptableRemoteVersions = "*")
public class TestModPleaseIgnore{
	public static final String NAME = "Test Mod Please Ignore";
	public static final String MODID = "tmpi";
	public static final String VERSION = "1.0";
	
	public enum GUI_ENUM{PLAYERINV,BAUBLES}
	
	public static Logger logger;
	
	@Instance(MODID)
	public static TestModPleaseIgnore instance;
	
	@SidedProxy(clientSide = "nathanMeyer.mods.testModPleaseIgnore.proxy.ClientProxy",
	            serverSide = "nathanMeyer.mods.testModPleaseIgnore.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		logger = event.getModLog();
		logger.info("Main preInit()");
		proxy.preInit(event);
	}
	
	public static GUIHandler guiHandler;
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		logger.info("Main init()");
		NetworkRegistry.INSTANCE.registerGuiHandler(TestModPleaseIgnore.instance,guiHandler = new GUIHandler());
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		logger.info("Main postInit()");
		proxy.postInit(event);
	}
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event){
		logger.info("serverStarting()");
		event.registerServerCommand(new CommandInvsee());
		proxy.serverStarting(event);
	}
}
