package net.rom.core.recipe;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IRSerializer {
    JsonObject serialize(ItemStack result, Object... components);

}
