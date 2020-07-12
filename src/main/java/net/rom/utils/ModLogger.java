package net.rom.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The Class ModLogger.
 */
public class ModLogger {
	
	/** The Constant MOD_LOG. */
	private static final Map<String, ModLogger> MOD_LOG = new HashMap<>();

	/** The logger. */
	private final Logger logger;
	
	/** The build. */
	private final int build;
	
	/** The latest debug out. */
	private String latestDebugOut = "";
	
	/**
	 * Instantiates a new mod logger.
	 *
	 * @param modName the mod name
	 * @param build the build
	 */
	public ModLogger(String modName, int build) {
		this.logger = LogManager.getLogger(modName);
		this.build = build;
		addLogger(modName);
	}
	
	/**
	 * Instantiates a new mod logger.
	 *
	 * @param providedLogger the provided logger
	 * @param build the build
	 */
	public ModLogger(Logger providedLogger, int build) {
		this.logger = providedLogger;
		this.build = build;
		addLogger(providedLogger.getName());
	}
	
    /**
     * Gets the LogHelper for the mod name (<em>not mod ID</em>), if it exists. Holding a reference
     * to the object should be preferred; this method should not be used in most cases.
     *
     * @param modName The mod name (not ID)
     * @return Optional of LogHelper if one has been registered, empty Optional otherwise
     */
    public static Optional<ModLogger> getRegisteredLogger(String modName) {
        if (!MOD_LOG.containsKey(modName)) return Optional.empty();
        return Optional.of(MOD_LOG.get(modName));
    }

	/**
	 * Adds the logger.
	 *
	 * @param modName the mod name
	 */
	private void addLogger(String modName) {
		MOD_LOG.put(modName, this);
		this.debug("Add Logger for \"{}\"", modName);
	}
	
	/**
	 * Catching.
	 *
	 * @param t the t
	 */
	public void catching(Throwable t) {
		this.logger.catching(t);
	}
	
    /**
     * Debug.
     *
     * @param msg the msg
     * @param params the params
     */
    public void debug(String msg, Object... params) {
        this.logger.debug(msg, params);

        if (this.build == 0) {
            String newOutput = this.logger.getMessageFactory().newMessage(msg, params).getFormattedMessage();
            if (!newOutput.equals(latestDebugOut)) {
                info("[DEBUG] " + newOutput);
                this.latestDebugOut = newOutput;
            }
        }
    }

    /**
     * Error.
     *
     * @param msg the msg
     * @param params the params
     */
    public void error(String msg, Object... params) {
        this.logger.error(msg, params);
    }

    /**
     * Fatal.
     *
     * @param msg the msg
     * @param params the params
     */
    public void fatal(String msg, Object... params) {
        this.logger.fatal(msg, params);
    }

    /**
     * Info.
     *
     * @param msg the msg
     * @param params the params
     */
    public void info(String msg, Object... params) {
        this.logger.info(msg, params);
    }

    /**
     * Log.
     *
     * @param level the level
     * @param msg the msg
     * @param params the params
     */
    public void log(Level level, String msg, Object... params) {
        this.logger.log(level, msg, params);
    }

    /**
     * Trace.
     *
     * @param msg the msg
     * @param params the params
     */
    public void trace(String msg, Object... params) {
        this.logger.trace(msg, params);
    }

    /**
     * Warn.
     *
     * @param msg the msg
     * @param params the params
     */
    public void warn(String msg, Object... params) {
        this.logger.warn(msg, params);
    }

    /**
     * Warn.
     *
     * @param t the t
     * @param msg the msg
     * @param params the params
     */
    public void warn(Throwable t, String msg, Object... params) {
        this.logger.warn(msg, params);
        this.logger.catching(t);
    }

}
