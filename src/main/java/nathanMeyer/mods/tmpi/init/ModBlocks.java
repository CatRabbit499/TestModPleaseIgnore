package nathanMeyer.mods.tmpi.init;

import nathanMeyer.mods.tmpi.blocks.BlockPlayerInventory;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks{
	@ObjectHolder("tmpi:invblock")
	public static Block invBlock;
	
	public static void registerTiles(IForgeRegistry<TileEntityType<?>> registry){
	
	}
	
	public static void registerBlocks(IForgeRegistry<Block> registry){
		registry.register(new BlockPlayerInventory());
	}
}