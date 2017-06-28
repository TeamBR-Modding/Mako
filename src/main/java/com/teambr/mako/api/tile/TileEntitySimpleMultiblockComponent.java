package com.teambr.mako.api.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class TileEntitySimpleMultiblockComponent extends TileEntityBase implements IHasGui {

    private static final String NBT_CONTROLLER = "Controller";

    private BlockPos controller;

    public BlockPos getController() {
        return controller;
    }

    public void setController(BlockPos controller) {
        this.controller = controller;
        this.markDirty();
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound tagCompound = super.writeToNBT(compound);
        if (controller != null) tagCompound.setTag(NBT_CONTROLLER, NBTUtil.createPosTag(controller));
        return tagCompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        controller = null;
        if (compound.hasKey(NBT_CONTROLLER))
            controller = NBTUtil.getPosFromTag(compound.getCompoundTag(NBT_CONTROLLER));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (controller != null && this.world.getTileEntity(controller) instanceof IHasExternalCapability)
            return ((IHasExternalCapability) this.world.getTileEntity(controller)).hasExternalCapability(this.pos, capability, facing);
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (controller != null && this.world.getTileEntity(controller) instanceof IHasExternalCapability)
            return ((IHasExternalCapability) this.world.getTileEntity(controller)).getExternalCapability(pos, capability, facing);
        return super.getCapability(capability, facing);
    }

    public void notifyContollerOnBreak() {
        if (controller != null && this.world.getTileEntity(controller) instanceof TileEntityMultiblock) {
            ((TileEntityMultiblock) this.world.getTileEntity(controller)).destroyMultiblock();
        }
    }

    @Override
    public Container getClientGUI(int id, EntityPlayer player, World world, BlockPos pos) {
        if (controller != null && this.world.getTileEntity(controller) instanceof TileEntityMultiblock) {
            return ((TileEntityMultiblock) this.world.getTileEntity(controller)).getClientGUI(id, player, world, pos);
        }
        return null;
    }
}
