package net.rom.registry;


/**
 * The Interface IAddRecipe.
 */
public interface IAddRecipe {
	   
   	/**
   	 * Add recipes for the block/item.
   	 *
   	 * @param recipes the recipes
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
