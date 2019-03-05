package nathanMeyer.mods.testModPleaseIgnore.proxy;

import nathanMeyer.mods.testModPleaseIgnore.TestModPleaseIgnore;
import nathanMeyer.mods.testModPleaseIgnore.command.CommandGamma;
import nathanMeyer.mods.testModPleaseIgnore.command.CommandSound;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class ClientProxy  extends CommonProxy{
    @Override
    public void preInit(FMLPreInitializationEvent event){
        super.preInit(event);
        TestModPleaseIgnore.logger.info("ClientProxy preInit()");
    }

    @Override
    public void init(FMLInitializationEvent event){
        super.init(event);
        ClientCommandHandler.instance.registerCommand(CommandGamma.INSTANCE);
        ClientCommandHandler.instance.registerCommand(CommandSound.INSTANCE);
        TestModPleaseIgnore.logger.info("ClientProxy init()");
    }

    @Override
    public void postInit(FMLPostInitializationEvent event){
        super.postInit(event);
        TestModPleaseIgnore.logger.info("ClientProxy postInit()");
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event){

    }
}