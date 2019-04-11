package nathanMeyer.mods.tmpi.tiles;

import mcp.MethodsReturnNonnullByDefault;
import nathanMeyer.mods.tmpi.client.gui.inventory.ContainerInvsee;
import nathanMeyer.mods.tmpi.client.gui.inventory.GUIInvsee;
import nathanMeyer.mods.tmpi.init.ModBlocks;
import nathanMeyer.mods.tmpi.util.IGuiTile;
import nathanMeyer.mods.tmpi.util.IRestorableTileEntity;
import nathanMeyer.mods.tmpi.util.LocationManager;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IInteractionObject;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static nathanMeyer.mods.tmpi.TestModPleaseIgnore.LOGGER;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TileInvsee extends TileEntity implements ITickable, IRestorableTileEntity, IGuiTile, IInteractionObject{
	private static final UUID TILE_ID = UUID.randomUUID();
	/** Player One Inventory **/
	private EntityPlayer readyPlayerOne;
	private InventoryPlayer invPlayerOne;
	private List<NonNullList<ItemStack>> storagePlayerOne;
	/** Player Two Inventory **/
	private EntityPlayer readyPlayerTwo;
	private InventoryPlayer invPlayerTwo;
	private List<NonNullList<ItemStack>> storagePlayerTwo;
	
	private static UUID getTileId(){
		return TILE_ID;
	}
	
	public TileInvsee(){
		super(ModBlocks.TYPE_INVSEE);
	}
	
	@SuppressWarnings("unused")
	public TileInvsee(TileEntityType<?> typeIn,Block block,EntityPlayer playerOne,EntityPlayer playerTwo){
		super(typeIn);
		LOGGER.info("TileEntity ID: {}",TileInvsee::getTileId);
		this.readyPlayerOne = playerOne;
		this.invPlayerOne = playerOne.inventory;
		this.storagePlayerOne = new ArrayList<NonNullList<ItemStack>>(){{
			add(invPlayerOne.mainInventory);
			add(invPlayerOne.armorInventory);
			add(invPlayerOne.offHandInventory);
		}};
		this.readyPlayerTwo = playerTwo;
		this.invPlayerTwo = playerTwo.inventory;
		this.storagePlayerTwo = new ArrayList<NonNullList<ItemStack>>(){{
			add(invPlayerTwo.mainInventory);
			add(invPlayerTwo.armorInventory);
			add(invPlayerTwo.offHandInventory);
		}};
	}
	
	/** {@link IGuiTile} **/
	@Override
	public GuiContainer createGui(EntityPlayer player){
		return new GUIInvsee(invPlayerOne,invPlayerTwo,player);
	}
	
	/** {@link IRestorableTileEntity} **/
	@Override
	public void readRestorableFromNBT(NBTTagCompound compound){
	
	}
	
	@Override
	public void writeRestorableToNBT(NBTTagCompound compound){
	
	}
	
	/** {@link ITickable} **/
	@Override
	public void tick(){
	
	}
	
	/** {@link IInteractionObject} **/
	@Override
	public Container createContainer(InventoryPlayer invPlayer,EntityPlayer player){
		return new ContainerInvsee(invPlayer,invPlayerTwo,player);
	}
	
	@Override
	public String getGuiID(){
		return LocationManager.getLocation("tile_invsee").toString();
	}
	
	/** {@link net.minecraft.util.INameable} **/
	@Override
	public ITextComponent getName(){
		return new TextComponentString("TileInvsee");
	}
	
	@Override
	public boolean hasCustomName(){
		return false;
	}
	
	@Override
	@Nullable
	public ITextComponent getCustomName(){
		return null;
	}
}