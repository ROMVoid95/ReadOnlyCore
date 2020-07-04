package net.rom.readonlycore.client.render.world;

public interface IClimateProvider
{
	public ICloudProvider getCloudProvider();

	public IStormProvider getStormProvider();
}