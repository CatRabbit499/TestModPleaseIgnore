package nathanMeyer.mods.tmpi.util;

import nathanMeyer.mods.tmpi.TestModPleaseIgnore;
import net.minecraft.util.ResourceLocation;

public class LocationManager{
	public static ResourceLocation getLocation(String path){
		return new ResourceLocation(TestModPleaseIgnore.MODID,path);
	}
}
