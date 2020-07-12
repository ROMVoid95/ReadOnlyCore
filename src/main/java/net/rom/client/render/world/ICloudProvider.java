package net.rom.client.render.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.rom.client.render.Texture;
import net.rom.client.render.model.SCMathHelper;
import net.rom.utils.CoreUtil;

public interface ICloudProvider {
	public float getCloudMovementSpeed(World world);

	public default float getMaxCloudSpeedDuringStorm() {
		return 12F;
	}

	public default float getMaxNormalCloudSpeed() {
		return 2F;
	}

	@SideOnly(Side.CLIENT)
	public Texture getCloudTexture();

	public default double getCloudMovementX(World world, float cloudTicksPrev, float cloudTicks) {
		return SCMathHelper.interpolateRotation(cloudTicksPrev, cloudTicks, CoreUtil.getPartialTicks());
	}

	public default double getCloudMovementZ(World world, float cloudTicksPrev, float cloudTicks) {
		return 0;
	}

	public boolean areCloudsApplicableTo(WorldProvider provider);
}