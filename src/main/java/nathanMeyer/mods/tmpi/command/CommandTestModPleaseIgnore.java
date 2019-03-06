package nathanMeyer.mods.tmpi.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import nathanMeyer.mods.tmpi.constants.ChatFormatting;
import nathanMeyer.mods.tmpi.util.ChatUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import static net.minecraft.command.Commands.literal;

public class CommandTestModPleaseIgnore{
	public static int register(CommandDispatcher<CommandSource> dispatcher){
		dispatcher.register(literal("tmpi").then(literal("reload").executes(ctx->testModPleaseIgnore(ctx.getSource())))
		                                   .executes(context->testModPleaseIgnore(context.getSource())));
		return 1;
	}
	
	private static int testModPleaseIgnore(CommandSource source) throws CommandSyntaxException{
		EntityPlayerMP player = source.asPlayer();
		World world = player.getEntityWorld();
		if(world.isRemote){
			ChatUtils.respond(source,ChatFormatting.PREFIX + "§3Hello, §f" + player.getName() + "§3!");
		}
		else{
			ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.modinfo());
		}
		return 1;
	}
}