package nathanMeyer.mods.tmpi.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import nathanMeyer.mods.tmpi.TestModPleaseIgnore;
import nathanMeyer.mods.tmpi.client.gui.GUIHandler;
import nathanMeyer.mods.tmpi.util.ChatUtils.ChatFormatting;
import nathanMeyer.mods.tmpi.util.ChatUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static net.minecraft.command.Commands.argument;
import static net.minecraft.command.Commands.literal;
import static net.minecraftforge.common.util.Constants.NBT.*;

public class CommandInvsee{
	private static Logger LOGGER = TestModPleaseIgnore.LOGGER;
	
	public static void register(CommandDispatcher<CommandSource> dispatcher){
		dispatcher.register(literal("invsee")
			.executes(ctx->invsee(ctx.getSource(),ctx.getSource().asPlayer().getName().getString()))
			.then(argument("target",StringArgumentType.string()).executes(
				ctx->invsee(ctx.getSource(),StringArgumentType.getString(ctx,"target")))));
	}
	
	private static int invsee(CommandSource source,String name) throws CommandSyntaxException{
		MinecraftServer server = source.getServer();
		if(server.isSinglePlayer()){
			ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.info("Run on singleplayer"));
			EntityPlayerMP player = source.asPlayer();
			EntityPlayerMP target = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByUsername(name);
			ChatUtils.respond(source,"Mark 1!");
			GUIHandler.openInvseeGui(player,target);
		}
		else{
			ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.info("Run on dedicated server"));
			UUID targetID;
			String targetUser;
			EntityPlayerMP player = source.asPlayer();
			if(name.matches("[0-9a-f]{8}-?[0-9a-f]{4}-?[0-5][0-9a-f]{3}-?[089ab][0-9a-f]{3}-?[0-9a-f]{12}")){
				// UUID
				source.sendFeedback(new TextComponentString(
					ChatFormatting.PREFIX + ChatFormatting.info("Args[0] (" + name + ") matches UUID format. Validating...")),false);
				targetID = UUID.fromString(name);
				switch(CommandInvsee.checkUUIDStatus(targetID,server)){
				case EXISTS_ONLINE:{
					ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.info("Exists Online"));
					break;
				}
				case EXISTS_OFFLINE:{
					ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.info("Exists Offline"));
					SaveHandler saveHandler = (SaveHandler)Objects
						.requireNonNull(DimensionManager.getWorld(server,DimensionType.OVERWORLD,false,false))
						.getSaveHandler();
					String[] dats = saveHandler.getPlayerNBTManager().getAvailablePlayerDat();
					ArrayList<String> datlist = new ArrayList<String>(){{
						addAll(Arrays.asList(dats));
					}};
					if(Arrays.asList(dats).contains(targetID.toString())){
						File playersDirectory = new File(saveHandler.getWorldDirectory(),"playerdata");
						File playerFile = new File(playersDirectory,targetID.toString() + ".dat");
						System.out.println(playerFile.exists() + " & " + playerFile.isFile());
						if(playerFile.exists()){
							try{
								NBTTagCompound inv = CompressedStreamTools.readCompressed(new FileInputStream(playerFile));
								WorldServer worldserver = server.getWorld(DimensionType.OVERWORLD);
								GameProfile gameprof = new GameProfile(UUID.randomUUID(),"[TMPI] - FakePlayer");
								FakePlayer dummyplayer = new FakePlayer(worldserver,gameprof);
								
								byte slot;
								ItemStack invItem;
								NBTTagList stackList = inv.getList("Inventory",Constants.NBT.TAG_COMPOUND);
								//ChatUtils.respond(source,"Inventory: ");
								for(int i = 0;i<stackList.size();i++){
									dummyplayer.inventory.write(stackList.getList(i));
									//ChatUtils.respond(source,"[" + slot + "] " + invItem.toString());
								}
								
								//ChatUtils.respond(source,"Mark 1");
								ChatUtils.respond(source,dummyplayer.getName() + ": ");
								ChatUtils.respond(source,dummyplayer.inventory.mainInventory.toString());
								//ChatUtils.respond(source,"Mark 2");
								GUIHandler.openInvseeGui(player,dummyplayer);
							}
							catch(Exception e){
								e.printStackTrace(System.err);
							}
						}
						else{
							ChatUtils.respond(source,ChatFormatting.PREFIX + "No file, (not good)");
						}
						break;
					}
				}
				case NON_EXISTANT:{
					ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.info("Non Existant"));
					break;
				}
				}
			}
			else{
				// Username or garbage string
				ChatUtils.respond(source,
					ChatFormatting.PREFIX + ChatFormatting.info("Checking args[0] (" + name + ") for user matches...")
				);
				targetUser = name;
				switch(checkUserStatus(targetUser,server)){
				case EXISTS_ONLINE:{
					ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.info("Exists Online"));
					break;
				}
				case EXISTS_OFFLINE:{
					ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.info("Exists Offline"));
					break;
				}
				case NON_EXISTANT:{
					ChatUtils.respond(source,ChatFormatting.PREFIX + ChatFormatting.info("Non Existant"));
					break;
				}
				}
			}
		}
		return 1;
	}
	
	private static QueryStatus checkUUIDStatus(UUID query,MinecraftServer server){
		if(server.getPlayerProfileCache().getProfileByUUID(query)!=null){
			GameProfile targetProfile = server.getPlayerProfileCache().getProfileByUUID(query);
			assert targetProfile!=null;
			if(Arrays.asList(server.getPlayerList().getOnlinePlayerNames()).contains(targetProfile.getName()))
				return QueryStatus.EXISTS_ONLINE;
			return QueryStatus.EXISTS_OFFLINE;
		}
		return QueryStatus.NON_EXISTANT;
	}
	
	private static QueryStatus checkUserStatus(String query,MinecraftServer server){
		if(Arrays.asList(server.getPlayerList().getOnlinePlayerNames()).contains(query))
			return QueryStatus.EXISTS_ONLINE;
		if(server.getPlayerProfileCache().getGameProfileForUsername(query)!=null)
			return QueryStatus.EXISTS_OFFLINE;
		return QueryStatus.NON_EXISTANT;
	}
	
	private String decode(int id){
		switch(id){
		case TAG_END:
			return "end";
		case TAG_BYTE:
			return "byte";
		case TAG_SHORT:
			return "short";
		case TAG_INT:
			return "int";
		case TAG_LONG:
			return "long";
		case TAG_FLOAT:
			return "float";
		case TAG_DOUBLE:
			return "double";
		case TAG_BYTE_ARRAY:
			return "byte array";
		case TAG_STRING:
			return "string";
		case TAG_LIST:
			return "list";
		case TAG_COMPOUND:
			return "compound";
		case TAG_INT_ARRAY:
			return "int array";
		case TAG_LONG_ARRAY:
			return "long array";
		case TAG_ANY_NUMERIC:
			return "any numeric";
		default:
			return "INVALID";
		}
	}
	
	private enum QueryStatus{EXISTS_ONLINE,EXISTS_OFFLINE,NON_EXISTANT}
}
