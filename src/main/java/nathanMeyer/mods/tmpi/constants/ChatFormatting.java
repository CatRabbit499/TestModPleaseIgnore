package nathanMeyer.mods.tmpi.constants;

import nathanMeyer.mods.tmpi.TestModPleaseIgnore;

public class ChatFormatting{
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