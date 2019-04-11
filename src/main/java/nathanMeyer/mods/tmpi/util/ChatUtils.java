package nathanMeyer.mods.tmpi.util;

import net.minecraft.command.CommandSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;

import static net.minecraft.util.text.TextFormatting.BLUE;
import static net.minecraft.util.text.TextFormatting.UNDERLINE;

public class ChatUtils{
	public static void respond(CommandSource source,boolean prefix,Object... items){
		ITextComponent msg;
		if(items.length==0) return;
		msg = new TextComponentString("");
		if(prefix) msg.appendSibling(Template.PREFIX);
		for(Object item : items)
			msg.appendSibling((item instanceof ITextComponent) ? (ITextComponent)item : new TextComponentString(item.toString()));
		source.sendFeedback(msg,false);
	}
	
	public static void respondf(boolean prefix,CommandSource source,String message,Object... args){
		ITextComponent msg;
		String temp = message;
		while(temp.indexOf("{}")>0) for(Object o : args)
			temp = temp.replaceFirst("\\Q{}",o.toString());
		msg = new TextComponentString(temp);
		if(prefix) msg.appendSibling(Template.PREFIX);
		source.sendFeedback(msg.appendSibling(new TextComponentString(message)),false);
	}
	
	public static void debug(CommandSource source,String message,Throwable error){
		ITextComponent test = new TextComponentString(message).appendSibling(new TextComponentString(error.getMessage())
			.applyTextStyles(UNDERLINE,BLUE)
			.applyTextStyle(x->x.setClickEvent(new ClickEvent(Action.CHANGE_PAGE,"fixme..."))));
		source.sendFeedback(test,false);
	}
}
