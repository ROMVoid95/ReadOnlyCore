package net.rom.libs.autoreg;

import net.minecraft.item.ItemBlock;

public interface ILibItemBlock {
	
	/**
	 * @return The custom {@link ItemBlock} to get registered
	 */
	ItemBlock getCustomItemBlock();

}
