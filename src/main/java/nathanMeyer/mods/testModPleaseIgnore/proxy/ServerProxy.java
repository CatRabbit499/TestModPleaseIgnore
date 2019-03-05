package nathanMeyer.mods.testModPleaseIgnore.proxy;

import nathanMeyer.mods.testModPleaseIgnore.TestModPleaseIgnore;
import nathanMeyer.mods.testModPleaseIgnore.command.CommandTestModPleaseIgnore;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.SERVER)
public class ServerProxy extends CommonProxy{
    @Override
    public void preInit(FMLPreInitializationEvent event){
        super.preInit(event);
        TestModPleaseIgnore.logger.info("ServerProxy preInit()");
    }

    @Override
    public void init(FMLInitializationEvent event){
        super.init(event);
        TestModPleaseIgnore.logger.info("ServerProxy init()");
    }

    @Override
    public void postInit(FMLPostInitializationEvent event){
        super.postInit(event);
        TestModPleaseIgnore.logger.info("ServerProxy preInit()");
    }

    public void serverStarting(FMLServerStartingEvent event){
        super.serverStarting(event);
        TestModPleaseIgnore.logger.info("ServerProxy serverStarting()");
        event.registerServerCommand(new CommandTestModPleaseIgnore());
    }
}