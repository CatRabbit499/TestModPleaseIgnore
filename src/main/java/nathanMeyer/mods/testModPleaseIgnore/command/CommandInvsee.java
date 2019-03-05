package nathanMeyer.mods.testModPleaseIgnore.command;

import com.mojang.authlib.GameProfile;
import nathanMeyer.mods.testModPleaseIgnore.TestModPleaseIgnore;
import nathanMeyer.mods.testModPleaseIgnore.client.gui.inventory.GUIInvsee;
import nathanMeyer.mods.testModPleaseIgnore.constants.ChatFormatting;
import nathanMeyer.mods.testModPleaseIgnore.util.ChatUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.minecraftforge.common.util.Constants.NBT.*;

public class CommandInvsee implements ICommand {
    private static Logger logger = TestModPleaseIgnore.logger;

    @Override
    public String getName() {
        return "invsee";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/invsee <Username | UUID>";
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<String>(){{
            add("invsee");
            add("openinv");
        }};
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
        if(server.isSinglePlayer()){
            EntityPlayerMP player = (EntityPlayerMP)sender.getCommandSenderEntity();
            World world = player.getEntityWorld();
            int x = (int)Math.round(player.posX);
            int y = (int)Math.round(player.posY);
            int z = (int)Math.round(player.posZ);

            System.out.println("CommandMain.java: call GUI");
            player.openGui(TestModPleaseIgnore.instance, 100, world, x, y, z);
            System.out.println("CommandMain.java: done");

            GUIInvsee.remotePlayerName = player.getName();
            player.openGui(TestModPleaseIgnore.instance, 1, player.world, player.chunkCoordX, player.chunkCoordY, player.chunkCoordZ);
        }
        else{
            if(sender.getCommandSenderEntity() instanceof EntityPlayerMP){
                EntityPlayerMP player = (EntityPlayerMP)sender.getCommandSenderEntity();
                if(args.length < 1){
                    ChatUtils.sendFeedback(server,sender,ChatFormatting.PREFIX + ChatFormatting.error("Must provide IGN/UUID on servers"));
                }
                else{
                    UUID targetID;
                    String targetUser;
                    if(args[0].matches("[0-9a-f]{8}-?[0-9a-f]{4}-?[0-5][0-9a-f]{3}-?[089ab][0-9a-f]{3}-?[0-9a-f]{12}")){
                        // UUID
                        ChatUtils.sendFeedback(server,sender,ChatFormatting.PREFIX + ChatFormatting.info("Args[0] (" + args[0] + ") matches UUID format. Validating..."));
                        targetID = UUID.fromString(args[0]);
                        switch(checkUUIDStatus(targetID,server)){
                            case EXISTS_ONLINE:{
                                ChatUtils.sendFeedback(server,sender,ChatFormatting.PREFIX + ChatFormatting.info("Exists Online"));
                                break;
                            }
                            case EXISTS_OFFLINE:{
                                ChatUtils.sendFeedback(server,sender,ChatFormatting.PREFIX + ChatFormatting.info("Exists Offline"));
                                SaveHandler saveHandler = (SaveHandler)DimensionManager.getWorld(0).getSaveHandler();
                                String[] dats = saveHandler.getPlayerNBTManager().getAvailablePlayerDat();
                                ArrayList<String> datlist = new ArrayList<String>(){{
                                    addAll(Arrays.asList(dats));
                                }};
                                if(Arrays.asList(dats).contains(targetID.toString())) {
                                    File playersDirectory = new File(saveHandler.getWorldDirectory(), "playerdata");
                                    File playerFile = new File(playersDirectory, targetID.toString() + ".dat");
                                    System.out.println(playerFile.exists() + " & " + playerFile.isFile());
                                    if(playerFile.exists()){
                                        try{
                                            NBTTagCompound inv = CompressedStreamTools.readCompressed(new FileInputStream(playerFile));

                                            WorldServer world = server.getWorld(0);
                                            GameProfile gameprof = new GameProfile(UUID.randomUUID(), "[TMPI] - FakePlayer");
                                            FakePlayer dummyplayer = new FakePlayer(world, gameprof);

                                            byte slot;
                                            ItemStack invItem;
                                            NBTTagList stackList = inv.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);
                                            //ChatUtils.sendFeedback(server,sender,"Inventory: ");
                                            for(int i=0; i<stackList.tagCount(); i++){
                                                invItem = new ItemStack(stackList.getCompoundTagAt(i));
                                                slot = stackList.getCompoundTagAt(i).getByte("Slot");
                                                dummyplayer.inventory.mainInventory.set(slot,invItem);
                                                //ChatUtils.sendFeedback(server,sender,"[" + slot + "] " + invItem.toString());
                                            }
                                            //ChatUtils.sendFeedback(server,sender,"Mark 1");

                                            ChatUtils.sendFeedback(server,sender,dummyplayer.getName() + ": ");
                                            ChatUtils.sendFeedback(server,sender,dummyplayer.inventory.mainInventory.toString());
                                            //ChatUtils.sendFeedback(server,sender,"Mark 2");



                                            TestModPleaseIgnore.guiHandler.setTarget(dummyplayer);
                                            player.openGui(TestModPleaseIgnore.instance,2,player.world,player.chunkCoordX,player.chunkCoordY,player.chunkCoordZ);
                                        }
                                        catch(Exception e){
                                            e.printStackTrace(System.err);
                                        }
                                    }
                                    else{
                                        ChatUtils.sendFeedback(server,sender,ChatFormatting.PREFIX + "No file, (not good)");
                                    }
                                    break;
                                }
                            }
                            case NON_EXISTANT:{
                                ChatUtils.sendFeedback(server, sender, ChatFormatting.PREFIX + ChatFormatting.info("Non Existant"));
                                break;
                            }
                        }
                    }
                    else{
                        // Username or garbage string
                        ChatUtils.sendFeedback(server, sender, ChatFormatting.PREFIX + ChatFormatting.info("Checking args[0] (" + args[0] + ") for user matches..."));
                        targetUser = args[0];
                        switch(checkUserStatus(targetUser,server)){
                            case EXISTS_ONLINE:{
                                ChatUtils.sendFeedback(server, sender, ChatFormatting.PREFIX + ChatFormatting.info("Exists Online"));
                                break;
                            }
                            case EXISTS_OFFLINE:{
                                ChatUtils.sendFeedback(server, sender, ChatFormatting.PREFIX + ChatFormatting.info("Exists Offline"));
                                break;
                            }
                            case NON_EXISTANT:{
                                ChatUtils.sendFeedback(server, sender, ChatFormatting.PREFIX + ChatFormatting.info("Non Existant"));
                                break;
                            }
                        }
                    }
                }
            }
            else{
                ChatUtils.sendFeedback(server, sender, ChatFormatting.PREFIX + ChatFormatting.error("Console y u do dis lol"));
            }
        }
    }

    private String decode(int id){
        switch(id){
            case TAG_END: return "end";
            case TAG_BYTE: return "byte";
            case TAG_SHORT: return "short";
            case TAG_INT: return "int";
            case TAG_LONG: return "long";
            case TAG_FLOAT: return "float";
            case TAG_DOUBLE: return "double";
            case TAG_BYTE_ARRAY: return "byte array";
            case TAG_STRING: return "string";
            case TAG_LIST: return "list";
            case TAG_COMPOUND: return "compound";
            case TAG_INT_ARRAY: return "int array";
            case TAG_LONG_ARRAY: return "long array";
            case TAG_ANY_NUMERIC: return "any numeric";
            default: return "INVALID";
        }
    }

    private enum QueryStatus{EXISTS_ONLINE,EXISTS_OFFLINE,NON_EXISTANT}

    private QueryStatus checkUserStatus(String query, MinecraftServer server){
        for(GameProfile g : server.getOnlinePlayerProfiles()){
            if(g.getName().equals(query)){
                return QueryStatus.EXISTS_ONLINE;
            }
        }
        if(Arrays.asList(server.getPlayerProfileCache().getUsernames()).contains(query)){
            return QueryStatus.EXISTS_OFFLINE;
        }
        else{
            return QueryStatus.NON_EXISTANT;
        }
    }

    private QueryStatus checkUUIDStatus(UUID query, MinecraftServer server){
        GameProfile targetProfile;
        if(server.getPlayerProfileCache().getProfileByUUID(query) != null){
            targetProfile = server.getPlayerProfileCache().getProfileByUUID(query);
            if(Arrays.asList(server.getOnlinePlayerProfiles()).contains(targetProfile)){
                // Online Player Target
                return QueryStatus.EXISTS_ONLINE;
            }
            else{
                // Offline Player Target
                return QueryStatus.EXISTS_OFFLINE;
            }
        }
        else{
            return QueryStatus.NON_EXISTANT;
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender){
        /*EntityPlayerMP player;
        if(sender instanceof EntityPlayerMP) {
            player = (EntityPlayerMP) sender;
        }
        PermissionAPI.hasPermission((EntityPlayerMP)sender,"");
        sender.sendMessage(new TextComponentString(ChatFormatting.PREFIX + ChatFormatting.info("Perm Level: ") + sender.getServer()));
        if(sender.canUseCommand(2,getName())){
            return true;
        }
        return false;*/
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
        String[] datlist = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0).getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat();
        ArrayList<String> result = new ArrayList<String>();
        if(Arrays.stream(datlist).anyMatch(t -> t.startsWith(args[0]))){
            result.addAll(Arrays.stream(datlist).filter(x -> x.startsWith(args[0])).collect(Collectors.toList()));
        }
        else{
            result.addAll(Arrays.asList(datlist));
        }
        return result;
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
