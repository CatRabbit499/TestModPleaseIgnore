package nathanMeyer.mods.testModPleaseIgnore.command;

import mcp.MethodsReturnNonnullByDefault;
import nathanMeyer.mods.testModPleaseIgnore.constants.ChatFormatting;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
public class CommandTestModPleaseIgnore extends CommandBase{

    @Override
    public String getName() {
        return "testmodpleaseignore";
    }

    @Override
    public String getUsage(ICommandSender sender){
        return "/testmodpleaseignore <subcommand> [args[2] args[3] ... args[n-1] args[n]]";
    }

    @Override
    public List<String> getAliases(){
        return new ArrayList<String>(){{
            add("testmodpleaseignore");
            add("tmpi");
        }};
    }

    @Override
    public void execute(final MinecraftServer server,final ICommandSender sender,final String[] args){
        World world = sender.getEntityWorld();
        if(world.isRemote){
            sender.sendMessage(new TextComponentString(ChatFormatting.PREFIX + "§3Hello, §f" + sender.getName() + "§3!"));
        }
        else{
            if(args.length>0){
                switch(args[0]){
                    default:{
                        sender.sendMessage(new TextComponentString(ChatFormatting.PREFIX + ChatFormatting.error("Invalid Subcommand: ") + args[0]));
                        break;
                    }
                }
            }
            else{
                sender.sendMessage(new TextComponentString(ChatFormatting.PREFIX + ChatFormatting.modinfo()));
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        World world = sender.getEntityWorld();
        if(world.isRemote){
            return true;
        }
        // Player Sender
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return new ArrayList<String>(){{
            add("Test1");
            add("Test2");
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
