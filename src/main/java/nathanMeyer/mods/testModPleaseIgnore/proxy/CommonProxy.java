package nathanMeyer.mods.testModPleaseIgnore.proxy;

import nathanMeyer.mods.testModPleaseIgnore.init.ModBlocks;
import nathanMeyer.mods.testModPleaseIgnore.init.ModItems;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@EventBusSubscriber
public class CommonProxy{
    public static Configuration config;

    public void preInit(FMLPreInitializationEvent event){
        ModBlocks.init();
        ModItems.init();
    }

    public void init(FMLInitializationEvent event){

    }

    public void postInit(FMLPostInitializationEvent event){

    }

    public void serverStarting(FMLServerStartingEvent event){

    }
}
