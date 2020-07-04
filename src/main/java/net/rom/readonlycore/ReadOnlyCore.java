package net.rom.readonlycore;

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
import net.rom.readonlycore.base.IMod;
import net.rom.readonlycore.command.CommandData;
import net.rom.readonlycore.network.ReadOnlyNetHandler;
import net.rom.readonlycore.proxy.LibCommonProxy;
import net.rom.readonlycore.utils.TranslateUtil;

@Mod(modid = Ref.MODID, name = Ref.NAME, version = Ref.VERSION, acceptedMinecraftVersions = Ref.ACCEPTED_MC_VERSION, updateJSON = Ref.UPDATE_URL, dependencies = Ref.DEPENDENCIES)
public class ReadOnlyCore implements IMod{


	public static ReadOnlyNetHandler network;
	public static final Logger LOGGER = LogManager.getLogger(Ref.NAME);
	@Instance(Ref.MODID)
	public static ReadOnlyCore instance;
	public static TranslateUtil translateUtil = new TranslateUtil(Ref.MODID);
	
	@SidedProxy(serverSide = Ref.SERVER_PROXY_CLASS, clientSide = Ref.CLIENT_PROXY_CLASS)
	public static LibCommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network = new ReadOnlyNetHandler(Ref.MODID);
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		ConfigManager.sync(Ref.MODID, Type.INSTANCE);
		proxy.init(event);
	}
	
    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandData());
    }
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@Override
	public String getModId() {
		return Ref.MODID;
	}

	@Override
	public String getModName() {
		return Ref.NAME;
	}

	@Override
	public String getVersion() {
		return Ref.VERSION;
	}

	@Override
	public int getBuildNum() {
		return Ref.BUILD_NUM;
	}
}
