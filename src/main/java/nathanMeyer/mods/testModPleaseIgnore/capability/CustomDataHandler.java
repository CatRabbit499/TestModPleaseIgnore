package nathanMeyer.mods.testModPleaseIgnore.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CustomDataHandler{
    // The Capability field. Used for checks and references.
    // Initialized when forge registers the capability.
    @CapabilityInject(IManaHandler.class)
    public static final Capability<IManaHandler> CAPABILITY_MANA = null;

    // Handles all of the required registration for the capability.
    public static void register(){
        CapabilityManager.INSTANCE.register(IManaHandler.class, new Storage(), DefaultManaHandler::new);
        MinecraftForge.EVENT_BUS.register(new CustomDataHandler());
    }

    // Allows the provider to be attatched to a target entity.
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof EntityPlayer)
            event.addCapability(new ResourceLocation("tmpi", "manacap"), new Provider());
    }

    // Allows for the capability to persist after death.
    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event){
        final IManaHandler original = getHandler(event.getOriginal());
        final IManaHandler clone = getHandler(event.getEntity());
        clone.setMana(original.getMana());
    }

    // Simple wrapper to get the handler from an entity.
    public static IManaHandler getHandler(Entity entity){
        if(entity.hasCapability(CAPABILITY_MANA, EnumFacing.DOWN))
            return entity.getCapability(CAPABILITY_MANA, EnumFacing.DOWN);
        return null;
    }

    // The basic properties for the new capability.
    public interface IManaHandler{
        int getMana();
        void setMana(int mana);
        void addMana(int mana);
        void removeMana(int mana);
    }

    // The default implementation of the capability. Holds all the logic.
    public static class DefaultManaHandler implements IManaHandler{
        private int mana;
        @Override
        public int getMana(){
            return this.mana;
        }

        @Override
        public void setMana(int mana){
            this.mana = mana;
        }

        @Override
        public void addMana(int mana){
            this.mana += mana;
        }

        @Override
        public void removeMana(int mana){
            this.mana -= mana;
            if(this.mana < 0) this.mana = 0;
        }
    }

    // Handles the actual read/write of the nbt.
    public static class Storage implements Capability.IStorage<IManaHandler>{
        @Override
        public NBTBase writeNBT(Capability<IManaHandler> capability, IManaHandler instance, EnumFacing side){
            final NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("mana", instance.getMana());
            return tag;
        }

        @Override
        public void readNBT(Capability<IManaHandler> capability, IManaHandler instance, EnumFacing side, NBTBase nbt){
            final NBTTagCompound tag = (NBTTagCompound) nbt;
            instance.setMana(tag.getInteger("mana"));
        }
    }

    // Delegates all of the system calls to the capability.
    public static class Provider implements ICapabilitySerializable<NBTTagCompound>{
        IManaHandler instance = CAPABILITY_MANA.getDefaultInstance();
        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing){
            return capability == CAPABILITY_MANA;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing){
            return hasCapability(capability, facing) ? CAPABILITY_MANA.<T>cast(instance) : null;
        }

        @Override
        public NBTTagCompound serializeNBT(){
            return (NBTTagCompound) CAPABILITY_MANA.getStorage().writeNBT(CAPABILITY_MANA, instance, null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt){
            CAPABILITY_MANA.getStorage().readNBT(CAPABILITY_MANA, instance, null, nbt);
        }
    }
}