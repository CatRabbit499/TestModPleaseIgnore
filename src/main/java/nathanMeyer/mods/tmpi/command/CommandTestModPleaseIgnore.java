package nathanMeyer.mods.tmpi.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import nathanMeyer.mods.tmpi.util.Template;
import nathanMeyer.mods.tmpi.util.ChatUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import static net.minecraft.command.Commands.literal;

public class CommandTestModPleaseIgnore{
	public static void register(CommandDispatcher<CommandSource> dispatcher){
		dispatcher.register(literal("tmpi")
			.then(literal("reload").executes(ctx->tmpiReload(ctx.getSource())))
			.then(literal("info").executes(ctx->tmpiInfo(ctx.getSource())))
			.executes(ctx->tmpiMain(ctx.getSource())));
	}
	
	private static int tmpiMain(CommandSource source) throws CommandSyntaxException{
		EntityPlayerMP player = source.asPlayer();
		World world = player.getEntityWorld();
		ChatUtils.respond(source,true,(world.isRemote ? "§3Hello, §f" + player.getName() + "§3!" : "") + Template.modinfo()
		);
		return 1;
	}
	
	private static int tmpiInfo(CommandSource source){
		return 1;
	}
	
	private static int tmpiReload(CommandSource source){
		ChatUtils.respond(source,true,Template.info("Reload is not implemented yet :("));
		return 1;
	}
}