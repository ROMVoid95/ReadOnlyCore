package net.rom.client;

import java.util.ArrayDeque;
import java.util.Queue;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.rom.ReadOnlyCore;
import net.rom.Ref;
import net.rom.utils.MCUtil;


/**
 * The Class ClientTicks.
 */
@Mod.EventBusSubscriber(modid = Ref.MODID, value = Side.CLIENT)
public class ClientTicks {
    
    /** The Constant INSTANCE. */
    @Deprecated
    public static final ClientTicks INSTANCE = new ClientTicks();

    /** The Constant QUEUE_OVERFLOW_LIMIT. */
    private static final int QUEUE_OVERFLOW_LIMIT = 200;

    /** The scheduled actions. */
    private static volatile Queue<Runnable> scheduledActions = new ArrayDeque<>();

    /** The ticks in game. */
    public static int ticksInGame = 0;
    
    /** The partial ticks. */
    public static float partialTicks = 0f;
    
    /** The delta ticks. */
    public static float deltaTicks = 0f;
    
    /** The total ticks. */
    public static float totalTicks = 0f;

    /**
     * Instantiates a new client ticks.
     */
    private ClientTicks() {}

    /**
     * Schedule action.
     *
     * @param action the action
     */
    public static void scheduleAction(Runnable action) {
        if (MCUtil.isClient())
            scheduledActions.add(action);
        else
        	ReadOnlyCore.LOGGER.error("Tried to add client tick action on server side? {}", action);

        if (scheduledActions.size() >= QUEUE_OVERFLOW_LIMIT) {
            // Queue overflow?
        	ReadOnlyCore.LOGGER.warn("Too many client tick actions queued! Currently at {} items. Would have added '{}'.",
                    scheduledActions.size(), action);
        	ReadOnlyCore.LOGGER.catching(new IllegalStateException("ClientTicks queue overflow"));
            scheduledActions.clear();
        }
    }

    /**
     * Client tick end.
     *
     * @param event the event
     */
    @SubscribeEvent
    public static void clientTickEnd(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        runScheduledActions();
        updateTickCounters();
    }

    /**
     * Render tick.
     *
     * @param event the event
     */
    @SubscribeEvent
    public static void renderTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            partialTicks = event.renderTickTime;
    }

    /**
     * Run scheduled actions.
     */
    private static void runScheduledActions() {
        Runnable action = scheduledActions.poll();
        while (action != null) {
            action.run();
            action = scheduledActions.poll();
        }
    }

    /**
     * Update tick counters.
     */
    private static void updateTickCounters() {
        GuiScreen gui = Minecraft.getMinecraft().currentScreen;
        if (gui == null || !gui.doesGuiPauseGame()) {
            ++ticksInGame;
            partialTicks = 0;
        }

        float oldTotal = totalTicks;
        totalTicks = ticksInGame + partialTicks;
        deltaTicks = totalTicks - oldTotal;
    }
}
