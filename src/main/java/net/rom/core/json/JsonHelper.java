/*
 * 
 */
package net.rom.core.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class JsonHelper {
	
	private String modId = "";
	private static String TAB = "    ";// 4 space
	
	/**
	 * Instantiates a new json helper.
	 *
	 * @param modId the mod id
	 */
	public JsonHelper(String modId) {
		this.modId = modId;
	}

	/**
	 * Sets the mod id.
	 *
	 * @param modId the mod id
	 * @return the json helper
	 */
	public JsonHelper setModId(String modId) {
		this.modId = modId;
		return this;
	}
	
	public String getModId() {
		return this.modId;
	}
	
	private ResourceLocation getAssetDirectory(String modid) {
		return new Res
	}

	/**
	 * Adds the block json files.
	 *
	 * @param block the block
	 * @param addPath the add path
	 */
	public void addBlockJsonFiles(Block block, String addPath) {
		try {
			File blockStates = new File(path + "/blockstates/",
					block.getUnlocalizedName().toLowerCase().substring(5) + ".json");
			File modelBlock = new File(path + "/models/block/",
					block.getUnlocalizedName().toLowerCase().substring(5) + ".json");
			File modelItemBlock = new File(path + "/models/item/",
					block.getUnlocalizedName().toLowerCase().substring(5) + ".json");

			// TODO NEED ADD UPDATE JSON
			if (!blockStates.exists())
				if (blockStates.createNewFile()) {
					blockstateJson(block, blockStates);
				}

			if (!modelBlock.exists())
				if (modelBlock.createNewFile()) {
					modelBlockJson(block, modelBlock, addPath);
				}

			if (!modelItemBlock.exists())
				if (modelItemBlock.createNewFile()) {
					modelItemBlockJson(block, modelItemBlock, addPath);
				}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Blockstate json.
	 *
	 * @param block the block
	 * @param file the file
	 */
	private void blockstateJson(Block block, File file) {
		try {

			FileWriter writer = new FileWriter(file);

			writer.write("{" + "\n");
			writer.write(TAB + "\"variants\": {" + "\n");
			writer.write(TAB + TAB + "\"normal\": {" + "\n");
			writer.write(TAB + TAB + TAB + "\"model\": " + "\"" + GalaxySpace.MODID + ":"
					+ block.getUnlocalizedName().toLowerCase().substring(5) + "\"" + "\n");
			writer.write(TAB + TAB + "}" + "\n");
			writer.write(TAB + "}" + "\n");
			writer.write("}");

			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Model block json.
	 *
	 * @param block the block
	 * @param file the file
	 * @param addPath the add path
	 */
	private void modelBlockJson(Block block, File file, String addPath) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write("{" + "\n");
			writer.write(TAB + "\"parent\": \"block/cube_all\"," + "\n");
			writer.write(TAB + "\"textures\": {" + "\n");
			writer.write(TAB + TAB + TAB + "\"all\": " + "\"" + GalaxySpace.MODID + ":blocks/" + addPath
					+ block.getUnlocalizedName().toLowerCase().substring(5) + "\"" + "\n");
			writer.write(TAB + "}" + "\n");
			writer.write("}");

			writer.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}

	}

	/**
	 * Model item block json.
	 *
	 * @param block the block
	 * @param file the file
	 * @param addPath the add path
	 */
	private void modelItemBlockJson(Block block, File file, String addPath) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write("{" + "\n");
			writer.write(TAB + "\"parent\": \"" + GalaxySpace.MODID + ":block/"
					+ block.getUnlocalizedName().toLowerCase().substring(5) + "\"" + "\n");
			writer.write("}");
			writer.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}
}
