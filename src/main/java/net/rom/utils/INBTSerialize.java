package net.rom.utils;

import net.minecraft.nbt.NBTTagCompound;


/**
 * The Interface INBTSerialize.
 *
 * @param <T> the generic type
 */
public interface INBTSerialize<T> {
	
    /**
     * Read.
     *
     * @param tags the tags
     * @return the t
     */
    T read(NBTTagCompound tags);
    
    /**
     * Write.
     *
     * @param tags the tags
     * @param obj the obj
     */
    void write(NBTTagCompound tags, T obj);
    
}
