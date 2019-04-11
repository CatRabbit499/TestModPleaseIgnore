package nathanMeyer.mods.tmpi.init;

import nathanMeyer.mods.tmpi.blocks.BlockPlayerInventory;
import nathanMeyer.mods.tmpi.tiles.TileInvsee;
import nathanMeyer.mods.tmpi.util.LocationManager;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks{
	@ObjectHolder("tmpi:invblock") public static Block invBlock;
	
	
	public static TileEntityType<?> TYPE_INVSEE;
	
	public static void registerTiles(IForgeRegistry<TileEntityType<?>> registry){
		registry.register(TYPE_INVSEE = TileEntityType.Builder
			.create(TileInvsee::new)
			.build(null)
			.setRegistryName(LocationManager.getLocation("tile_invsee")));
	}
	
	public static void registerBlocks(IForgeRegistry<Block> registry){
		registry.register(new BlockPlayerInventory());
	}
}