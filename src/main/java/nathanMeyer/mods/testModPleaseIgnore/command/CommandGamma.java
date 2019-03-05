package nathanMeyer.mods.testModPleaseIgnore.command;

import nathanMeyer.mods.testModPleaseIgnore.constants.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandGamma extends CommandBase{
    public static final CommandGamma INSTANCE = new CommandGamma();

    @Override
    public String getName(){
        return "gamma";
    }

    @Override
    public String getUsage(ICommandSender sender){
        return "/gamma #";
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<String>(){{
            add("gamma");
        }};
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
        if(args.length>0){
            try{
                Minecraft.getMinecraft().gameSettings.gammaSetting = Float.parseFloat(args[0]);
                sender.sendMessage(new TextComponentString(ChatFormatting.PREFIX + ChatFormatting.valid("Gamma Updated: ") + Minecraft.getMinecraft().gameSettings.gammaSetting));
            }catch(NumberFormatException e){
                sender.sendMessage(new TextComponentString(ChatFormatting.PREFIX + ChatFormatting.error("Invalid Input: ") + args[0]));
            }
        }
        else{
            sender.sendMessage(new TextComponentString(ChatFormatting.PREFIX + ChatFormatting.valid("Gamma: ") + Minecraft.getMinecraft().gameSettings.gammaSetting));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender){
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return new ArrayList<String>(){{

        }};
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index){
        return false;
    }

    @Override
    public int compareTo(ICommand o){
        return 0;
    }
}