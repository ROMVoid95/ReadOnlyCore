package net.rom.readonlycore.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.rom.readonlycore.items.ILeftClickItem;

public final class MessageLeftClick extends LibMessage{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum Type {
        EMPTY, BLOCK
    }

    public int type;
    public boolean mainHand;

    public MessageLeftClick() {
        this.type = 0;
        this.mainHand = true;
    }

    public MessageLeftClick(Type type, EnumHand hand) {
        this.type = type.ordinal();
        this.mainHand = hand == EnumHand.MAIN_HAND;
    }

    @Override
    public IMessage handleMessage(MessageContext context) {
        if (context.side != Side.SERVER)
            return null;

        EnumHand hand = mainHand ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
        EntityPlayer player = context.getServerHandler().player;
        ItemStack heldItem = player.getHeldItem(hand);

        if (!heldItem.isEmpty() && heldItem.getItem() instanceof ILeftClickItem) {
            ILeftClickItem item = (ILeftClickItem) heldItem.getItem();
            if (type == Type.EMPTY.ordinal()) {
                item.onItemLeftClickSL(player.world, player, hand);
            } else {
                item.onItemLeftClickBlockSL(player.world, player, hand);
            }
        }

        return null;
    }
}
