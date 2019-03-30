package nathanMeyer.mods.tmpi.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockBasic extends Block{
	public BlockBasic(Material material, ResourceLocation location){
		super(Properties.create(material));
		setRegistryName(location);
	}
}
