package nathanMeyer.mods.testModPleaseIgnore.client.gui;

import nathanMeyer.mods.testModPleaseIgnore.client.gui.inventory.ContainerInvsee;
import nathanMeyer.mods.testModPleaseIgnore.client.gui.inventory.GUIInvsee;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GUIHandler implements IGuiHandler{
    public static EntityPlayerMP other;
    public GUIHandler(){

    }

    public static void setTarget(EntityPlayerMP target){
        other = target;
    }

    private static InventoryPlayer getTarget(){
        return other.inventory;
    }



    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
        System.out.println("GUI ID: " + ID);
        if(ID == 1){ /*TestModPleaseIgnore.GUI_ENUM.PLAYERINV.ordinal()*/
            System.out.println("GuiHandler: getServerGuiElement ID == " + ID + " called");
            return new ContainerInvsee(player.inventory,player.inventory,world,new BlockPos(x,y,z));
        }
        if(ID == 2){
            System.out.println("GuiHandler: getServerGuiElement ID == " + ID + " called");
            return new ContainerInvsee(player.inventory,getTarget(),world,new BlockPos(x,y,z));
        }
        else{
            System.out.println("No GUI ID Match :(");
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
        System.out.println("GUI ID: " + ID);
        if(ID == 1){ /*TestModPleaseIgnore.GUI_ENUM.PLAYERINV.ordinal()*/
            System.out.println("GuiHandler: getClientGuiElement ID == " + ID + " called");
            return new GUIInvsee(player.inventory,player.inventory,world,new BlockPos(x,y,z));
        }
        if(ID == 2){
            System.out.println("GuiHandler: getClientGuiElement ID == " + ID + " called");
            return new GUIInvsee(player.inventory,getTarget(),world,new BlockPos(x,y,z));
        }
        return null;
    }
}
