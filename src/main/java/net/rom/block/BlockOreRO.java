package net.rom.block;

import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;


/**
 * The Class BlockOreRO.
 */
public class BlockOreRO extends BlockOre {
    
    /** The dropped item. */
    private final Item droppedItem;
    
    /** The quantity max. */
    private final int quantityMin, quantityMax;
    
    /** The xp max. */
    private final int xpMin, xpMax;

    /**
     * Instantiates a new block ore RO.
     *
     * @param droppedItem the dropped item
     * @param harvestLevel the harvest level
     * @param quantityMin the quantity min
     * @param quantityMax the quantity max
     * @param xpMin the xp min
     * @param xpMax the xp max
     */
    public BlockOreRO(Item droppedItem, int harvestLevel, int quantityMin, int quantityMax, int xpMin, int xpMax) {
        this.droppedItem = droppedItem;
        this.quantityMin = quantityMin;
        this.quantityMax = quantityMax;
        this.xpMin = xpMin;
        this.xpMax = xpMax;

        setHardness(3f);
        setResistance(15f);
        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", harvestLevel);
    }

    /**
     * Gets the item dropped.
     *
     * @param state the state
     * @param rand the rand
     * @param fortune the fortune
     * @return the item dropped
     */
    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return droppedItem;
    }

    /**
     * Quantity dropped.
     *
     * @param random the random
     * @return the int
     */
    @Override
    public int quantityDropped(Random random) {
        return quantityMin + random.nextInt(quantityMax - quantityMin + 1);
    }

    /**
     * Quantity dropped with bonus.
     *
     * @param fortune the fortune
     * @param random the random
     * @return the int
     */
    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        int quantity = quantityDropped(random);
        if (fortune > 0 && getItemDropped(getDefaultState(), random, fortune) != Item.getItemFromBlock(this)) {
            float bonus = bonusAmount(fortune, random);
            if (bonus < 0) bonus = 0;
            quantity = Math.round(quantity * (bonus + 1));
        }
        return MathHelper.clamp(quantity, 0, 64);
    }

    /**
     * Bonus amount.
     *
     * @param fortune the fortune
     * @param random the random
     * @return the float
     */
    public float bonusAmount(int fortune, Random random) {
        return random.nextInt(fortune + 2) - 1;
    }

    /**
     * Gets the exp drop.
     *
     * @param state the state
     * @param world the world
     * @param pos the pos
     * @param fortune the fortune
     * @return the exp drop
     */
    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        Item item = getItemDropped(world.getBlockState(pos), RANDOM, fortune);
        return item != Item.getItemFromBlock(this) ? xpMin + RANDOM.nextInt(xpMax - xpMin + 1) : 0;
    }
}
