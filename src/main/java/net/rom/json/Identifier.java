package net.rom.json;


/**
 * The Class Identifier.
 */
public class Identifier {

	/** The key. */
	private final String key;
	
	/** The item. */
	private final String item;
	
	/** The meta. */
	private final int meta;

	/**
	 * Instantiates a new identifier.
	 *
	 * @param key the key
	 * @param item the item
	 * @param meta the meta
	 */
	public Identifier(String key, String item, int meta) {
		this.key = key;
		this.item = item;
		this.meta = meta;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * Gets the item.
	 *
	 * @return the item
	 */
	public String getItem() {
		return this.item;
	}

	/**
	 * Gets the meta.
	 *
	 * @return the meta
	 */
	public int getMeta() {
		return this.meta;
	}

}
