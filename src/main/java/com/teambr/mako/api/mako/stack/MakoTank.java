package com.teambr.mako.api.mako.stack;

import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public class MakoTank implements IMakoTank, IMakoHandler {

    @Nullable
    protected MakoStack mako;
    protected int capacity;

    public MakoTank(int capacity) {
        this(null, capacity);
    }

    public MakoTank(@Nullable MakoStack makoStack, int capacity) {
        this.mako = makoStack;
        this.capacity = capacity;
    }


    @Override
    public MakoStack getMakoStack() {
        return mako;
    }

    @Override
    public int getMakoAmount() {
        return mako == null ? 0 : mako.getAmount();
    }

    @Override
    public int getMaxMakoAmount() {
        return capacity;
    }

    public void setMaxMakoAmount(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public MakoTankInfo getTankInfo() {
        return new MakoTankInfo(this);
    }

    @Override
    public void fill(MakoStack stack) {
        if (mako == null) mako = stack;
        else if (mako.isMakoEqual(stack)) {
            mako.fill(stack.getAmount());
        }
    }

    @Nullable
    @Override
    public void drain(int drainAmount) {
        mako.drain(drainAmount);
        if (mako.getAmount() <= 0) mako = null;
    }


    public MakoTank readFromNBT(NBTTagCompound nbt) {
        if (!nbt.hasKey("Empty")) {
            mako = MakoStack.loadFromNBT(nbt);
        } else {
            mako = null;
        }
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if (mako == null) {
            nbt.setString("Empty", "");
        } else {
            mako.writeToNBT(nbt);
        }
        return nbt;
    }

    @Nullable
    @Override
    public void drain(MakoStack res) {
        if (mako.isMakoEqual(res)) {
            mako.drain(res.getAmount());
        }
    }
}
