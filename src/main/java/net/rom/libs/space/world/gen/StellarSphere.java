package net.rom.libs.space.world.gen;

import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class StellarSphere extends WorldGenerator {
	private String modID;
	private IBlockState state;
	private int size;
	private int yOffset;
	private boolean showDebugInfo;

	public StellarSphere(boolean showDebugInfo, String modID, IBlockState state, int size, int yOffset) {
		super();
		this.modID = modID;
		this.state = state;
		this.size = size;
		this.yOffset = yOffset;
		this.showDebugInfo = showDebugInfo;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position) {
		if (GenUtility.checkValidSpawn(world, position, this.size) == false)
			return false;
		else {
			if (this.showDebugInfo)
				// MessageUtilities.debugMessageToLog(modID, "Spawning Sphere at (x, y, z)" +
				// position.toString());
				generateStructure(world, rand, position.down(this.yOffset));
		}
		return true;
	}

	public boolean generateStructure(World world, Random rand, BlockPos position) {
		for (Entry<BlockPos, IBlockState> temp : GenUtility.generateSphere(this.state, this.size, position)
				.entrySet()) {
			world.setBlockState(temp.getKey(), temp.getValue(), 3);
		}
		return true;

	}
}
