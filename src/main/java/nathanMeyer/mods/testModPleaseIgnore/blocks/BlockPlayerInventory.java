package nathanMeyer.mods.testModPleaseIgnore.blocks;

import nathanMeyer.mods.testModPleaseIgnore.TestModPleaseIgnore;
import nathanMeyer.mods.testModPleaseIgnore.init.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPlayerInventory extends BlockBasic{
    public BlockPlayerInventory(){
        super("invBlock",Material.ROCK);
        setUnlocalizedName("deconstructor");
        setCreativeTab(ModItems.tabTMPI);
    }

    @Override
    public boolean onBlockActivated(
        World world,
        BlockPos blockPos,
        IBlockState state,
        EntityPlayer player,
        EnumHand hand,
        EnumFacing facing,
        float hitX,
        float hitY,
        float hitZ
    ){
        if(!world.isRemote){
            TestModPleaseIgnore.logger.info("BlockPlayerInventory onBlockActivated");
            player.openGui(
                    TestModPleaseIgnore.instance,
                    /*TestModPleaseIgnore.GUI_ENUM.PLAYERINV.ordinal()*/1,
                    world,
                    blockPos.getX(),
                    blockPos.getY(),
                    blockPos.getZ()
            );
        }
        return true;
    }
}
