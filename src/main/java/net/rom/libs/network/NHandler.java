package net.rom.libs.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NHandler {
    public final SimpleNetworkWrapper wrapper;

    private int lastMessageId = -1;

    public NHandler(String modId) {
        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(modId);
    }

    public void register(Class clazz, Side handlerSide) {
        wrapper.registerMessage(clazz, clazz, ++lastMessageId, handlerSide);
    }
}
