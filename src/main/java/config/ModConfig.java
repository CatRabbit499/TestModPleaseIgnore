package config;

import nathanMeyer.mods.testModPleaseIgnore.TestModPleaseIgnore;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = TestModPleaseIgnore.MODID)
@LangKey("tmpi.config.title")
public class ModConfig {
    @Comment("Comment 1")
    public static boolean testBool1 = false;
    @Comment("Comment 2")
    public static boolean testBool2 = true;
    @Comment("Comment 3")
    public static boolean testBool3 = true;
    @Comment("Comment 4")
    public static boolean testBool4 = true;

    public static final Client client = new Client();
    public static class Client{
        @Comment("This is an example int property.")
        public int baz = -100;

        public final HUDPos chunkEnergyHUDPos = new HUDPos(0, 0);

        public static class HUDPos {
            public HUDPos(final int x, final int y) {
                this.x = x;
                this.y = y;
            }

            @Comment("The x coordinate")
            public int x;

            @Comment("The y coordinate")
            public int y;
        }
    }

    @EventBusSubscriber(modid = TestModPleaseIgnore.MODID)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(TestModPleaseIgnore.MODID)) {
                ConfigManager.sync(TestModPleaseIgnore.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
