package nathanMeyer.mods.tmpi.util;

import nathanMeyer.mods.tmpi.TestModPleaseIgnore;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;

import java.util.Arrays;

public class ChatUtils{
	public static ITextComponent PREFIX = new TextComponentString(ChatFormatting.PREFIX);
	
	public static void respond(CommandSource source,String message){
		source.sendFeedback(new TextComponentString(message),false);
	}
	
	public static void debug(CommandSource source,String message,Throwable error){
		ITextComponent test = new TextComponentString(message).appendSibling(new TextComponentString(error.getMessage())
			.applyTextStyles(TextFormatting.UNDERLINE,TextFormatting.BLUE)
			.applyTextStyle(x->x.setClickEvent(new ClickEvent(Action.CHANGE_PAGE,"fixme..."))));
		source.sendFeedback(test,false);
	}
	
	public static void respond(CommandSource source,String[] message){
		TextComponentString[] test = Arrays
			.stream(message)
			.map(x->new TextComponentString(ChatFormatting.TAB + x))
			.toArray(TextComponentString[]::new);
		test[0] = new TextComponentString(test[0].getText().trim());
		for(ITextComponent component : test){
			source.sendFeedback(component,false);
		}
	}
	
	public static class ChatFormatting{
		public static final String PREFIX = expandify("§8[§2TMPI§8] §r");
		public static final String TAB = "        ";
		
		public static String info(String message){
			return expandify("§b" + message + "§r");
		}
		
		private static String expandify(String str){
			return str.replace("§","\u00a7");
		}
		
		public static String valid(String message){
			return expandify("§a" + message + "§r");
		}
		
		public static String error(String message){
			return expandify("§6" + message + "§c");
		}
		
		public static String error(String message,String errorClass){
			return expandify("§6" + message + "§c" + errorClass);
		}
		
		public static String usage(String message){
			return expandify("§6" + message + "§r");
		}
		
		public static String modinfo(){
			return expandify("§2" + TestModPleaseIgnore.NAME + " §6v" + TestModPleaseIgnore.VERSION);
		}
	}
}
