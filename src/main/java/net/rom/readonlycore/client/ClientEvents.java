package net.rom.readonlycore.client;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.rom.readonlycore.ReadOnlyCore;
import net.rom.readonlycore.items.ILeftClickItem;
import net.rom.readonlycore.network.MessageLeftClick;

public final class ClientEvents {
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
