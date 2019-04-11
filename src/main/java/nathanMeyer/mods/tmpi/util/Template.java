package nathanMeyer.mods.tmpi.util;

import nathanMeyer.mods.tmpi.TestModPleaseIgnore;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import static net.minecraft.util.text.TextFormatting.*;

public class Template{
	private static Template INSTANCE = new Template();
	private ITextComponent component;
	
	static class Builder{
		Builder(){
			INSTANCE.component = new TextComponentString("");
		}
		
		Builder(ITextComponent curent){
			INSTANCE.component = curent;
		}
		
		Builder color(String text,TextFormatting... formatting){
			ITextComponent temp = new TextComponentString(text);
			for(TextFormatting tf : formatting)
				temp.applyTextStyle(tf);
			return new Builder(INSTANCE.component.appendSibling(temp));
		}
		
		ITextComponent build(){
			return INSTANCE.component;
		}
	}
	
	public static final ITextComponent PREFIX = new Builder()
		.color("[",DARK_GRAY)
		.color("TMPI",DARK_GREEN)
		.color("]",DARK_GRAY)
		.color(" ",RESET)
		.build();
	public static final ITextComponent TAB = new TextComponentString("        ");
	
	public TextComponentString expandify(String s){
		return new TextComponentString("none");
	}
	
	//private static String expandify(String str){
	//	return str.replace("§","\u00a7");
	//}
	
	public static ITextComponent normal(String message){
		return new Builder().color(message,RESET).build();
	}
	
	public static ITextComponent info(String message){
		return new Builder().color(message,AQUA).color("",RESET).build();
	}
	
	public static ITextComponent valid(String message){
		return new Builder().color(message,GREEN).color("",RESET).build();
	}
	
	public static ITextComponent error(String message){
		return new Builder().color(message,GOLD).color("",RED).build();
	}
	
	public static ITextComponent error(String message,String errorClass){
		return new Builder().color(message,GOLD).color(errorClass,RED).build();
	}
	
	public static ITextComponent usage(String message){
		return new Builder().color(message,GOLD).color("",RESET).build();
	}
	
	public static ITextComponent modinfo(){
		return new Builder().color(TestModPleaseIgnore.MODID,DARK_GREEN).color("v" + TestModPleaseIgnore.VERSION,GOLD).build();
	}
}
