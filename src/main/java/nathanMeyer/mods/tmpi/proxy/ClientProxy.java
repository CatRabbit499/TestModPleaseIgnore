package nathanMeyer.mods.tmpi.proxy;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ClientProxy implements IProxy{
	@Override
	public void setup(FMLCommonSetupEvent event){
		// TODO: Implement ClientCommandHandler or replacement
	}
}