package net.rom.json;

import java.io.FileWriter;

import com.google.gson.stream.JsonWriter;


/**
 * The Class Generator.
 */
public class Generator {

	/**
	 * Generate item.
	 *
	 * @param parent the parent
	 */
	public static void generateItem(String parent) {
		for (String name : JsonGenerator.toGenerate) {
			try {
				JsonWriter writer = new JsonWriter(
						new FileWriter(String.valueOf(JsonGenerator.PATH) + "/output/item/" + name + ".json"));
				writer.beginObject().setIndent("   ");
				writer.name("parent").value(parent);
				writer.name("textures");
				writer.beginObject().setIndent("   ");
				writer.name("layer0").value(String.valueOf(JsonGenerator.MODID) + ":items/" + name);
				writer.endObject();
				writer.endObject();
				writer.close();
				JsonGenerator.items++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		JsonGenerator.toGenerate.clear();
	}

	/**
	 * Generate item block.
	 */
	public static void generateItemBlock() {
		for (String name : JsonGenerator.toGenerate) {
			try {
				JsonWriter writer = new JsonWriter(
						new FileWriter(String.valueOf(JsonGenerator.PATH) + "/output/item/" + name + ".json"));
				writer.beginObject().setIndent("   ");
				writer.name("parent").value(String.valueOf(JsonGenerator.MODID) + ":block/" + name);
				writer.name("display");
				writer.beginObject().setIndent("   ");
				writer.name("thirdperson");
				writer.beginObject().setIndent("   ");
				writer.name("rotation");
				writer.beginArray().setIndent("");
				writer.value(10L);
				writer.value(-45L);
				writer.value(170L);
				writer.endArray().setIndent("   ");
				writer.name("translation");
				writer.beginArray().setIndent("");
				writer.value(0L);
				writer.value(1.5D);
				writer.value(-2.75D);
				writer.endArray().setIndent("   ");
				writer.name("scale");
				writer.beginArray().setIndent("");
				writer.value(0.375D);
				writer.value(0.375D);
				writer.value(0.375D);
				writer.endArray().setIndent("   ");
				writer.endObject();
				writer.endObject();
				writer.endObject();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Generate item block.
	 *
	 * @param name the name
	 */
	public static void generateItemBlock(String name) {
		try {
			JsonWriter writer = new JsonWriter(
					new FileWriter(String.valueOf(JsonGenerator.PATH) + "/output/item/" + name + ".json"));
			writer.beginObject().setIndent("   ");
			writer.name("parent").value(String.valueOf(JsonGenerator.MODID) + ":block/" + name);
			writer.name("display");
			writer.beginObject().setIndent("   ");
			writer.name("thirdperson");
			writer.beginObject().setIndent("   ");
			writer.name("rotation");
			writer.beginArray().setIndent("");
			writer.value(10L);
			writer.value(-45L);
			writer.value(170L);
			writer.endArray().setIndent("   ");
			writer.name("translation");
			writer.beginArray().setIndent("");
			writer.value(0L);
			writer.value(1.5D);
			writer.value(-2.75D);
			writer.endArray().setIndent("   ");
			writer.name("scale");
			writer.beginArray().setIndent("");
			writer.value(0.375D);
			writer.value(0.375D);
			writer.value(0.375D);
			writer.endArray().setIndent("   ");
			writer.endObject();
			writer.endObject();
			writer.endObject();
			writer.close();
			JsonGenerator.items++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generate block.
	 */
	public static void generateBlock() {
		for (String name : JsonGenerator.toGenerate) {
			try {
				JsonWriter writer = new JsonWriter(
						new FileWriter(String.valueOf(JsonGenerator.PATH) + "/output/block/" + name + ".json"));
				writer.beginObject().setIndent("   ");
				writer.name("parent").value("block/cube_all");
				writer.name("textures");
				writer.beginObject().setIndent("   ");
				writer.name("all").value(String.valueOf(JsonGenerator.MODID) + ":blocks/" + name);
				writer.endObject();
				writer.endObject();
				writer.close();
				JsonGenerator.blocks++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		generateItemBlock();
		generateBlockstate();
		JsonGenerator.toGenerate.clear();
	}

	/**
	 * Generate forge blockstate.
	 */
	public static void generateForgeBlockstate() {
		for (String name : JsonGenerator.toGenerate) {
			try {
				JsonWriter writer = new JsonWriter(
						new FileWriter(String.valueOf(JsonGenerator.PATH) + "/output/blockstates/" + name + ".json"));
				writer.beginObject().setIndent("   ");
				writer.name("variants");
				writer.beginObject().setIndent("   ");
				writer.name("normal");
				writer.beginObject().setIndent("");
				writer.name("model").value(String.valueOf(JsonGenerator.MODID) + ":" + name);
				writer.endObject().setIndent("   ");
				writer.endObject();
				writer.endObject();
				writer.close();
				JsonGenerator.blockstates++;
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}
	
	/**
	 * Generate blockstate.
	 */
	public static void generateBlockstate() {
		for (String name : JsonGenerator.toGenerate) {
			try {
				JsonWriter writer = new JsonWriter(
						new FileWriter(String.valueOf(JsonGenerator.PATH) + "/output/blockstates/" + name + ".json"));
				writer.beginObject().setIndent("   ");
				writer.name("variants");
				writer.beginObject().setIndent("   ");
				writer.name("normal");
				writer.beginObject().setIndent("");
				writer.name("model").value(String.valueOf(JsonGenerator.MODID) + ":" + name);
				writer.endObject().setIndent("   ");
				writer.endObject();
				writer.endObject();
				writer.close();
				JsonGenerator.blockstates++;
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

}
