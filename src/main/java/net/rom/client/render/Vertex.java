package net.rom.client.render;

import net.minecraft.client.renderer.Tessellator;

// TODO: Auto-generated Javadoc
/**
 * The Class Vertex.
 */
//TODO: MOVE TO CORE MOD
public class Vertex
{
	
	/** The unit X. */
	public static Vertex unitX = new Vertex(1, 0, 0);
	
	/** The unit Y. */
	public static Vertex unitY = new Vertex(0, 1, 0);
	
	/** The unit Z. */
	public static Vertex unitZ = new Vertex(0, 0, 1);
	
	/** The unit NX. */
	public static Vertex unitNX = new Vertex(-1, 0, 0);
	
	/** The unit NY. */
	public static Vertex unitNY = new Vertex(0, -1, 0);
	
	/** The unit NZ. */
	public static Vertex unitNZ = new Vertex(0, 0, -1);
	
	/** The unit PYNZ. */
	public static Vertex unitPYNZ = new Vertex(0, 0.707, -0.707);
	
	/** The unit PXPY. */
	public static Vertex unitPXPY = new Vertex(0.707, 0.707, 0);
	
	/** The unit PYPZ. */
	public static Vertex unitPYPZ = new Vertex(0, 0.707, 0.707);
	
	/** The unit NXPY. */
	public static Vertex unitNXPY = new Vertex(-0.707, 0.707, 0);

	/** The z. */
	public float x, y, z;

	/**
	 * Instantiates a new vertex.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public Vertex(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Instantiates a new vertex.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public Vertex(double x, double y, double z)
	{
		this((float) x, (float) y, (float) z);
	}

	/**
	 * Instantiates a new vertex.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public Vertex(int x, int y, int z)
	{
		this((float) x, (float) y, (float) z);
	}

	/**
	 * Normalize.
	 *
	 * @return the vertex
	 */
	public Vertex normalize()
	{
		float sq = (float) Math.sqrt(x * x + y * y + z * z);
		x = x / sq;
		y = y / sq;
		z = z / sq;
		return this;
	}

	/**
	 * Tessellate.
	 *
	 * @param tessellator the tessellator
	 * @return the vertex
	 */
	public Vertex tessellate(Tessellator tessellator)
	{
		return this.tessellateWithUV(tessellator, null);
	}

	/**
	 * Tessellate with UV.
	 *
	 * @param tessellator the tessellator
	 * @param uv the uv
	 * @return the vertex
	 */
	public Vertex tessellateWithUV(Tessellator tessellator, UV uv)
	{
		if (uv == null)
		{
			tessellator.getBuffer().pos(x, y, z);
		} else
		{
			tessellator.getBuffer().pos(x, y, z);
			tessellator.getBuffer().tex(uv.u, uv.v);
		}
		return this;
	}

	/**
	 * Adds the.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return the vertex
	 */
	public Vertex add(double x, double y, double z)
	{
		return new Vertex(this.x + x, this.y + y, this.z + z);
	}

	/**
	 * Adds the.
	 *
	 * @param v the v
	 * @return the vertex
	 */
	public Vertex add(Vertex v)
	{
		return add(v.x, v.y, v.z);
	}

	/**
	 * Mul.
	 *
	 * @param c the c
	 * @return the vertex
	 */
	public Vertex mul(double c)
	{
		return new Vertex(c * x, c * y, c * z);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString()
	{
		return String.format("Vertex(%s, %s, %s)", this.x, this.y, this.z);
	}
}