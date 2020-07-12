package net.rom.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;


/**
 * The Class BlockMetaSubtypes.
 */
public class BlockMetaSubtypes extends Block{
	
	/** The subtype count. */
	private final int subtypeCount;

	/**
	 * Instantiates a new block meta subtypes.
	 *
	 * @param materialIn the material in
	 * @param subtypeCount the subtype count
	 */
	public BlockMetaSubtypes(Material materialIn, int subtypeCount) {
		super(materialIn);
		this.subtypeCount = subtypeCount;
	}
	
    /**
     * Damage dropped.
     *
     * @param state the state
     * @return the int
     */
    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

	/**
	 * Gets the subtype count.
	 *
	 * @return the subtype count
	 */
	public int getSubtypeCount() {
		return subtypeCount;
	}

}
