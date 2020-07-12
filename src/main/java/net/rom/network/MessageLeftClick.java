package net.rom.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.rom.items.ILeftClickItem;


/**
 * The Class MessageLeftClick.
 */
public final class MessageLeftClick extends LibMessage{
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The Enum Type.
	 */
	public enum Type {
        
        /** The empty. */
        EMPTY, 
 /** The block. */
 BLOCK
    }

    /** The type. */
    public int type;
    
    /** The main hand. */
    public boolean mainHand;

    /**
     * Instantiates a new message left click.
     */
    public MessageLeftClick() {
        this.type = 0;
        this.mainHand = true;
    }

    /**
     * Instantiates a new message left click.
     *
     * @param type the type
     * @param hand the hand
     */
    public MessageLeftClick(Type type, EnumHand hand) {
        this.type = type.ordinal();
        this.mainHand = hand == EnumHand.MAIN_HAND;
    }

    /**
     * Handle message.
     *
     * @param context the context
     * @return the i message
     */
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
