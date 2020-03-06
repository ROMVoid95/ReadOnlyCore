package net.rom.libs.space.world;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

@SuppressWarnings({ "unused", "deprecation" })
public class StellarBiomeDecorator extends BiomeDecorator {

	public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
		if (this.decorating)
			throw new RuntimeException("Already decorating!!");
		this.chunkProviderSettings = ChunkGeneratorSettings.Factory
				.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
		this.chunkPos = pos;
		genDecorations(biome, worldIn, random);
	}

	protected void genDecorations(Biome biomeIn, World worldIn, Random random) {
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(worldIn, random, this.chunkPos));
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(worldIn, random, this.chunkPos));
	}

	private boolean getGen(World world, Random rand, BlockPos pos, DecorateBiomeEvent.Decorate.EventType event) {
		return TerrainGen.decorate(world, rand, pos, event);
	}
}
