package net.rom.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;


/**
 * The Class WorldPosition.
 */
public class WorldPosition {

	/** The Constant ZERO. */
	public static final WorldPosition ZERO = new WorldPosition(0, 0, 0, 0);

	/** The dim. */
	public final int x, y, z, dim;

	/**
	 * Instantiates a new world position.
	 *
	 * @param pos the pos
	 * @param dim the dim
	 */
	public WorldPosition(BlockPos pos, int dim) {
		this(pos.getX(), pos.getY(), pos.getZ(), dim);

	}

	/**
	 * Instantiates a new world position.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @param dim the dim
	 */
	public WorldPosition(int x, int y, int z, int dim) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dim = dim;
	}

	/**
	 * Read from NBT.
	 *
	 * @param tags the tags
	 * @return the world position
	 */
	public static WorldPosition readFromNBT(NBTTagCompound tags) {
		return new WorldPosition(tags.getInteger("posX"), tags.getInteger("posY"), tags.getInteger("posZ"),
				tags.getInteger("dim"));
	}

	/**
	 * Write to NBT.
	 *
	 * @param tags the tags
	 */
	public void writeToNBT(NBTTagCompound tags) {
		tags.setInteger("posX", x);
		tags.setInteger("posY", y);
		tags.setInteger("posZ", z);
		tags.setInteger("dim", dim);
	}

	/**
	 * To block pos.
	 *
	 * @return the block pos
	 */
	public BlockPos toBlockPos() {
		return new BlockPos(x, y, z);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return String.format("(%d, %d, %d) in dimension %d", x, y, z, dim);
	}

	/**
	 * Equals.
	 *
	 * @param other the other
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof WorldPosition)) {
			return false;
		}
		WorldPosition pos = (WorldPosition) other;
		return x == pos.x && y == pos.y && z == pos.z && dim == pos.dim;
	}
}
