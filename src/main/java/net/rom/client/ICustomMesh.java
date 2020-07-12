package net.rom.client;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**
 * The Interface ICustomMesh.
 */
public interface ICustomMesh {

	/**
	 * Gets the variants.
	 *
	 * @return the variants
	 */
	@SideOnly(Side.CLIENT)
	default ResourceLocation[] getVariants() {
		return new ModelResourceLocation[0];
	}

	/**
	 * Gets the custom mesh.
	 *
	 * @return the custom mesh
	 */
	@SideOnly(Side.CLIENT)
	ItemMeshDefinition getCustomMesh();
}
