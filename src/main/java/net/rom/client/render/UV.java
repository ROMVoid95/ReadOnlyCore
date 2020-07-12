package net.rom.client.render;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

// TODO: Auto-generated Javadoc
/**
 * The Class UV.
 */
//TODO: MOVE TO CORE MOD
public class UV
{
	
	/** The u. */
	public float u;
	
	/** The v. */
	public float v;

	/**
	 * Instantiates a new uv.
	 *
	 * @param u the u
	 * @param v the v
	 */
	public UV(float u, float v)
	{
		this.u = u;
		this.v = v;
	}

	/**
	 * Instantiates a new uv.
	 *
	 * @param facing the facing
	 * @param vec3d the vec 3 d
	 */
	public UV(EnumFacing facing, Vec3d vec3d)
	{
		switch (facing.getAxis())
		{
		case X:
			this.u = Math.round(vec3d.z * 16);
			this.v = Math.round(vec3d.y * 16);
			break;
		case Y:
			this.u = Math.round(vec3d.x * 16);
			this.v = Math.round(vec3d.z * 16);
			break;
		case Z:
			this.u = Math.round(vec3d.x * 16);
			this.v = Math.round(vec3d.y * 16);
			break;
		}
	}

	/**
	 * Gets the u.
	 *
	 * @return the u
	 */
	public float getU()
	{
		return u;
	}

	/**
	 * Gets the v.
	 *
	 * @return the v
	 */
	public float getV()
	{
		return v;
	}
}