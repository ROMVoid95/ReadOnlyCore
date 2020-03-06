package net.rom.libs.autoreg;

public interface IAddRecipe {
	   /**
     * Add recipes for the block/item
     *
     */
    default void addRecipes(RecipeBuilder recipes) {
    }

    /**
     * Add ore dictionary entries for the block/item.
     *
     */
    default void addOreDict() {
    }
}
