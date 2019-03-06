package nathanMeyer.mods.tmpi.util;

import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextComponentString;

public class ChatUtils{
	public static void respond(CommandSource source,String message){
		source.sendFeedback(new TextComponentString(message),false);
	}
}
