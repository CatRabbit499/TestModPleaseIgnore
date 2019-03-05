package nathanMeyer.mods.testModPleaseIgnore.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerInvsee extends Container{
    public ContainerInvsee(InventoryPlayer playerSenderInventory, InventoryPlayer playerTargetInventory, World world, BlockPos table){
        // Add TargetPlayer's Inventory to Container
        for (int k = 0; k < 3; ++k){ // Add rows to column (main inventory)
            for (int i1 = 0; i1 < 9; ++i1){ // Add slots to row (main inventory)
                this.addSlotToContainer(new Slot(playerTargetInventory, i1 + k * 9 + 9, 8 + i1 * 18,k * 18));
            }
        }

        for (int l = 0; l < 9; ++l){ // Add slots to row (hotbar)
            this.addSlotToContainer(new Slot(playerTargetInventory, l, 8 + l * 18, 58));
        }

        /* ---------------- Intended Inventory Divider ---------------- */
        // Add CommandSender's Inventory to Container
        for (int k = 0; k < 3; ++k){ // Add rows to column (main inventory)
            for (int i1 = 0; i1 < 9; ++i1){ // Add slots to row (main inventory)
                this.addSlotToContainer(new Slot(playerSenderInventory, i1 + k * 9 + 9, 8 + i1 * 18, k * 18 + 94));
            }
        }

        for (int l = 0; l < 9; ++l){ // Add slots to row (hotbar)
            this.addSlotToContainer(new Slot(playerSenderInventory, l, 8 + l * 18, 58 + 94));
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn){
        return true;
    }
}
