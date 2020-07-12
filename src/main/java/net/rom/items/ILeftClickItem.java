package net.rom.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;


/**
 * The Interface ILeftClickItem.
 */
public interface ILeftClickItem {
	  
  	/**
  	 * Networked left-click handler. Called in EventHandlers on both the client- and
  	 * server-side (via packet) when a player left-clicks on nothing (in the air).
  	 *
  	 * @param world the world
  	 * @param player the player
  	 * @param hand the hand
  	 * @return If this returns SUCCESS on the client-side, a packet will be sent to the server.
  	 */
    default ActionResult<ItemStack> onItemLeftClickSL(World world, EntityPlayer player, EnumHand hand) {
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    /**
     * Called when the player left-clicks on a block. Defaults to the same behavior as an empty
     * click (onItemLeftClick).
     *
     * @param world the world
     * @param player the player
     * @param hand the hand
     * @return If this returns SUCCESS on the client-side, a packet will be sent to the server.
     */
    default ActionResult<ItemStack> onItemLeftClickBlockSL(World world, EntityPlayer player, EnumHand hand) {
        return onItemLeftClickSL(world, player, hand);
    }
}
