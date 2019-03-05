package nathanMeyer.mods.testModPleaseIgnore.util;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class ChatUtils{
    public static void sendFeedback(MinecraftServer server, ICommandSender sender, String message){
        sender.sendMessage(new TextComponentString(message));
    }
}
