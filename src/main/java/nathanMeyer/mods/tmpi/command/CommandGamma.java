package nathanMeyer.mods.tmpi.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import nathanMeyer.mods.tmpi.constants.ChatFormatting;
import nathanMeyer.mods.tmpi.util.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandSource;

import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;

public class CommandGamma{
	public static final CommandGamma INSTANCE = new CommandGamma();
	
	public static void register(CommandDispatcher<CommandSource> dispatcher){
		dispatcher.register(literal("gamma").then(argument("newGamma",FloatArgumentType.floatArg(0,1000))
				.executes(ctx->setGamma(ctx.getSource(),FloatArgumentType.getFloat(ctx,"newGamma"))))
		                                    .executes(ctx->getGamma(ctx.getSource())));
	}
	
	private static int setGamma(CommandSource source,float amount){
		Minecraft.getInstance().gameSettings.gammaSetting = amount;
		ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.valid("Gamma Updated: ") + Minecraft
				.getInstance().gameSettings.gammaSetting);
		return 1;
	}
	
	private static int getGamma(CommandSource source){
		ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.valid("Gamma: ") + Minecraft
				.getInstance().gameSettings.gammaSetting);
		return 1;
	}
}