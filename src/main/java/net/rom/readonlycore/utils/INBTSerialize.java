package net.rom.readonlycore.utils;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSerialize<T> {
	
    T read(NBTTagCompound tags);
    
    void write(NBTTagCompound tags, T obj);
    
}
