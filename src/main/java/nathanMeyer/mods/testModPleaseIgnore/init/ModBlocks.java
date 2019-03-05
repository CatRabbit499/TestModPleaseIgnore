package nathanMeyer.mods.testModPleaseIgnore.init;

import nathanMeyer.mods.testModPleaseIgnore.TestModPleaseIgnore;
import nathanMeyer.mods.testModPleaseIgnore.blocks.BlockBasic;
import nathanMeyer.mods.testModPleaseIgnore.blocks.BlockPlayerInventory;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = TestModPleaseIgnore.MODID)
public class ModBlocks{
    public static Block testBlock;
    public static Block invBlock;

    public static void init(){
        testBlock = new BlockBasic("testBlock", Material.ROCK);
        testBlock.setHarvestLevel("pickaxe", 2);
        testBlock.setCreativeTab(ModItems.tabTMPI);
        testBlock.setLightLevel(1.0f);
        testBlock.setHardness(1.5f);

        invBlock = new BlockPlayerInventory();
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){
        event.getRegistry().registerAll(testBlock);
        event.getRegistry().registerAll(invBlock);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(new ItemBlock(testBlock).setRegistryName(testBlock.getRegistryName()));
        event.getRegistry().registerAll(new ItemBlock(invBlock).setRegistryName(invBlock.getRegistryName()));
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event){
        registerRender(Item.getItemFromBlock(testBlock));
        registerRender(Item.getItemFromBlock(invBlock));
    }

    public static void registerRender(Item item){
        ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation( item.getRegistryName(),"inventory"));
    }
}