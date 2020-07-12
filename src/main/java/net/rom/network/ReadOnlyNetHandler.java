package net.rom.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


/**
 * The Class ReadOnlyNetHandler.
 */
public class ReadOnlyNetHandler {
    
    /** The wrapper. */
    public final SimpleNetworkWrapper wrapper;

    /** The last message id. */
    private int lastMessageId = -1;

    /**
     * Instantiates a new read only net handler.
     *
     * @param modId the mod id
     */
    public ReadOnlyNetHandler(String modId) {
        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(modId);
    }

    /**
     * Register.
     *
     * @param clazz the clazz
     * @param handlerSide the handler side
     */
    public void register(Class clazz, Side handlerSide) {
        wrapper.registerMessage(clazz, clazz, ++lastMessageId, handlerSide);
    }
}
