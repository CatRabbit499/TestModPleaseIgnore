package nathanMeyer.mods.tmpi.command;

import com.mojang.brigadier.CommandDispatcher;
import nathanMeyer.mods.tmpi.TestModPleaseIgnore;
import nathanMeyer.mods.tmpi.constants.ChatFormatting;
import nathanMeyer.mods.tmpi.util.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.command.CommandSource;

import java.util.concurrent.atomic.AtomicBoolean;

import static net.minecraft.command.Commands.literal;

public class CommandSound{
	public static final CommandSound INSTANCE = new CommandSound();
	private static final boolean devEnv = !Boolean.valueOf(System.getProperty("fml.deobfuscatedEnvironment"));
	
	public static int register(CommandDispatcher<CommandSource> dispatcher){
		dispatcher.register(literal("sound").then(literal("reload").executes(ctx->reloadSound(ctx.getSource()))));
		return 1;
	}
	
	private static int reloadSound(CommandSource source){
		try{
			// Iniiate Reload
			ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.info("Reloading Soundsystem..."));
			final SoundManager manager = Minecraft.getInstance().getSoundHandler().sndManager;
			manager.reload();
			
			// Feedback Thread
			Thread reloadFeedback = new Thread("reloadFeedback"){
				public void run(){
					try{
						AtomicBoolean test = new AtomicBoolean(manager.loaded);
						while(!test.getAndSet(manager.loaded)){
							sleep(200); // Check every tick (20/sec)
						}
						ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.valid("Reload Complete"));
					}
					catch(Exception e){
						ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting
								.error("Error In Feedback Thread: ",e.getClass().getName()));
						TestModPleaseIgnore.LOGGER.error(e);
						TestModPleaseIgnore.LOGGER.error(e.fillInStackTrace());
					}
				}
			};
			reloadFeedback.start();
		}
		catch(Exception e){
			ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.error("Error Reloading SoundSystem: ",e.getClass().getName()));
			TestModPleaseIgnore.LOGGER.error(e);
			TestModPleaseIgnore.LOGGER.error(e.fillInStackTrace());
		}
		return 1;
	}
}
