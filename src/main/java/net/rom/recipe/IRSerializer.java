package net.rom.recipe;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;


/**
 * The Interface IRSerializer.
 */
@FunctionalInterface
public interface IRSerializer {
    
    /**
     * Serialize.
     *
     * @param result the result
     * @param components the components
     * @return the json object
     */
    JsonObject serialize(ItemStack result, Object... components);

}
