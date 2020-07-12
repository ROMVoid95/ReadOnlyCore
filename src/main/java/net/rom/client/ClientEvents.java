package net.rom.client;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.rom.ReadOnlyCore;
import net.rom.items.ILeftClickItem;
import net.rom.network.MessageLeftClick;


/**
 * The Class ClientEvents.
 */
public final class ClientEvents {
	  
  	/**
  	 * On left click empty.
  	 *
  	 * @param event the event
  	 */
  	@SubscribeEvent
	  public void onLeftClickEmpty(LeftClickEmpty event) {

	    ItemStack stack = event.getItemStack();
	    if (!stack.isEmpty() && stack.getItem() instanceof ILeftClickItem) {
	      // Client-side call
	      ActionResult<ItemStack> result = ((ILeftClickItem) stack.getItem())
	          .onItemLeftClickSL(event.getWorld(), event.getEntityPlayer(), event.getHand());
	      // Server-side call
	      if (result.getType() == EnumActionResult.SUCCESS) {
	    	  ReadOnlyCore.network.wrapper
	            .sendToServer(new MessageLeftClick(MessageLeftClick.Type.EMPTY, event.getHand()));
	      }
	    }
	  }

	  /**
  	 * On left click block.
  	 *
  	 * @param event the event
  	 */
  	@SubscribeEvent
	  public void onLeftClickBlock(LeftClickBlock event) {

	    ItemStack stack = event.getItemStack();
	    if (!stack.isEmpty() && stack.getItem() instanceof ILeftClickItem) {
	      // Client-side call
	      ActionResult<ItemStack> result = ((ILeftClickItem) stack.getItem())
	          .onItemLeftClickBlockSL(event.getWorld(), event.getEntityPlayer(), event.getHand());
	      // Server-side call
	      if (result.getType() == EnumActionResult.SUCCESS) {
	    	  ReadOnlyCore.network.wrapper
	            .sendToServer(new MessageLeftClick(MessageLeftClick.Type.BLOCK, event.getHand()));
	      }
	    }
	  }
}
