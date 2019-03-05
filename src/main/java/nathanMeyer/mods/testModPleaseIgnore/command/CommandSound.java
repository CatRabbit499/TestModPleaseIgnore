package nathanMeyer.mods.testModPleaseIgnore.command;

import nathanMeyer.mods.testModPleaseIgnore.TestModPleaseIgnore;
import nathanMeyer.mods.testModPleaseIgnore.constants.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CommandSound extends CommandBase{
    public static final CommandSound INSTANCE = new CommandSound();

    @Override
    public String getName(){
        return "sound";
    }

    @Override
    public String getUsage(ICommandSender sender){
        return "/sound reload";
    }

    @Override
    public List<String> getAliases(){
        return new ArrayList<String>(){{}};
    }

    private static final boolean devEnv = (boolean)Launch.blackboard.get("fml.deobfuscatedEnvironment");

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
        if(args.length>0){
            if(args[0].equalsIgnoreCase("reload")){
                Field reflectTargetA;
                Field reflectTargetB;
                SoundHandler handler;
                try{
                    // Iniiate Reload
                    sender.sendMessage(new TextComponentString(ChatFormatting.PREFIX + ChatFormatting.valid("Reloading Soundsystem...")));
                    handler = Minecraft.getMinecraft().getSoundHandler();
                    reflectTargetA = SoundHandler.class.getDeclaredField(devEnv ? "sndManager" : "field_147694_f");
                    reflectTargetA.setAccessible(true);
                    SoundManager manager = (SoundManager) reflectTargetA.get(handler);
                    manager.reloadSoundSystem();
                    // User Feedback
                    reflectTargetB = manager.getClass().getDeclaredField(devEnv ?"loaded" : "field_148617_f");
                    reflectTargetB.setAccessible(true);

                    Thread reloadFeedback = new Thread("reloadFeedback"){
                        public void run(){
                            System.out.println("run by: " + getName());
                            try{
                                while (!reflectTargetB.getBoolean(manager));
                            }catch(Exception e){
                                sender.sendMessage(new TextComponentString(ChatFormatting.PREFIX + ChatFormatting.error("Error In Feedback Thread: ",e.getClass().getName())));
                            }
                            sender.sendMessage(new TextComponentString(ChatFormatting.PREFIX + ChatFormatting.valid("Reload Complete")));
                        }
                    };
                    reloadFeedback.start();
                }catch(Exception e){
                    sender.sendMessage(new TextComponentString(ChatFormatting.PREFIX + ChatFormatting.error("Error Reloading SoundSystem: ",e.getClass().getName())));
                    TestModPleaseIgnore.logger.info(e);
                    TestModPleaseIgnore.logger.info(e.fillInStackTrace());
                }
            }
            else{
                sender.sendMessage(new TextComponentString(ChatFormatting.PREFIX + ChatFormatting.error("Invalid Input: ") + args[0]));
            }
        }
        else{
            sender.sendMessage(new TextComponentString(ChatFormatting.PREFIX + ChatFormatting.usage(getUsage(sender))));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender){
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
        return new ArrayList<String>(){{
            if(args.length < 2){
                add("reload");
            }
        }};
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
