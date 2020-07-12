package net.rom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.rom.base.IMod;
import net.rom.command.CommandData;
import net.rom.network.ReadOnlyNetHandler;
import net.rom.proxy.LibCommonProxy;
import net.rom.utils.TranslateUtil;


/**
 * The Class ReadOnlyCore.
 */
@Mod(modid = Ref.MODID, name = Ref.NAME, version = Ref.VERSION, acceptedMinecraftVersions = Ref.ACCEPTED_MC_VERSION, updateJSON = Ref.UPDATE_URL, dependencies = Ref.DEPENDENCIES)
public class ReadOnlyCore implements IMod{


	/** The network. */
	public static ReadOnlyNetHandler network;
	
	/** The Constant LOGGER. */
	public static final Logger LOGGER = LogManager.getLogger(Ref.NAME);
	
	/** The instance. */
	@Instance(Ref.MODID)
	public static ReadOnlyCore instance;
	
	/** The translate util. */
	public static TranslateUtil translateUtil = new TranslateUtil(Ref.MODID);
	
	/** The proxy. */
	@SidedProxy(serverSide = Ref.SERVER_PROXY_CLASS, clientSide = Ref.CLIENT_PROXY_CLASS)
	public static LibCommonProxy proxy;

	/**
	 * Pre init.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network = new ReadOnlyNetHandler(Ref.MODID);
		proxy.preInit(event);
	}

	/**
	 * Inits the.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		ConfigManager.sync(Ref.MODID, Type.INSTANCE);
		proxy.init(event);
	}
	
    /**
     * On server starting.
     *
     * @param event the event
     */
    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandData());
    }
	
	/**
	 * Post init.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	/**
	 * Gets the mod id.
	 *
	 * @return the mod id
	 */
	@Override
	public String getModId() {
		return Ref.MODID;
	}

	/**
	 * Gets the mod name.
	 *
	 * @return the mod name
	 */
	@Override
	public String getModName() {
		return Ref.NAME;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	@Override
	public String getVersion() {
		return Ref.VERSION;
	}

	/**
	 * Gets the builds the num.
	 *
	 * @return the builds the num
	 */
	@Override
	public int getBuildNum() {
		return Ref.BUILD_NUM;
	}
}
