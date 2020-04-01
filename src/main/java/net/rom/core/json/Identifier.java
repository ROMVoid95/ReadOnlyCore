package net.rom.core.json;

public class Identifier {

	private final String key;
	private final String item;
	private final int meta;

	public Identifier(String key, String item, int meta) {
		this.key = key;
		this.item = item;
		this.meta = meta;
	}

	public String getKey() {
		return this.key;
	}

	public String getItem() {
		return this.item;
	}

	public int getMeta() {
		return this.meta;
	}

}
