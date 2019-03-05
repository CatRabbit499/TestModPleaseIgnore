package nathanMeyer.mods.testModPleaseIgnore.init;

import nathanMeyer.mods.testModPleaseIgnore.TestModPleaseIgnore;
import nathanMeyer.mods.testModPleaseIgnore.items.ItemBasic;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = TestModPleaseIgnore.MODID)
public class ModItems{
    public static final CreativeTabs tabTMPI = new CreativeTabs("tabTMPI"){
        @Override
        public ItemStack getTabIconItem(){
            return new ItemStack(testItem);
        }

        @Override
        public boolean hasSearchBar(){
            return true;
        }
    }.setBackgroundImageName("item_search.png");

    static Item testItem;

    public static void init(){
        testItem = new ItemBasic("testItem");
        testItem.setCreativeTab(ModItems.tabTMPI);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){
        event.getRegistry().registerAll(testItem);
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event){
        registerRender(testItem);
    }

    private static void registerRender(Item item){
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
    }
}