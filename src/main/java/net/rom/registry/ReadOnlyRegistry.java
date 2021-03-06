package net.rom.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraft.world.storage.loot.properties.EntityProperty;
import net.minecraft.world.storage.loot.properties.EntityPropertyManager;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.rom.block.BlockMetaSubtypes;
import net.rom.block.inerf.IColorBlock;
import net.rom.block.inerf.ITEBlock;
import net.rom.client.ICustomMesh;
import net.rom.client.ICustomModel;
import net.rom.items.IColorItem;
import net.rom.items.ItemBlockMetaSubtypes;
import net.rom.utils.MCUtil;


/**
 * The Class StellarRegistry.
 */
public class ReadOnlyRegistry {
    
    /** The Constant PATTERN_REGISTRY_NAME. */
    private static final Pattern PATTERN_REGISTRY_NAME = Pattern.compile("[^a-z0-9_]+");

    /** The blocks. */
    private final List<Block> blocks = NonNullList.create();
    
    /** The items. */
    private final List<Item> items = NonNullList.create();

    /** The recipe adders. */
    private final List<IAddRecipe> recipeAdders = NonNullList.create();
    
    /** The colored blocks. */
    private final List<Block> coloredBlocks = NonNullList.create();
    
    /** The colored items. */
    private final List<Item> coloredItems = NonNullList.create();

    /** The phased initializers. */
    private final List<IPhaseInit> phasedInitializers = new ArrayList<>();
    
    /** The registration handlers. */
    private final Map<Class<? extends IForgeRegistryEntry<?>>, Consumer<ReadOnlyRegistry>> registrationHandlers = new HashMap<>();

    /** The mod. */
    private Object mod;
    
    /** The logger. */
    private final Logger logger;
    
    /** The mod id. */
    private final String modId;
    
    /** The resource prefix. */
    private final String resourcePrefix;

    /** The recipes. */
    @Nonnull
    private final RecipeBuilder recipes;

    /** The default creative tab. */
    @Nullable
    private CreativeTabs defaultCreativeTab = null;

    /**
     * Constructor which automatically acquires the mod container to populate required fields.
     *
     */
    public ReadOnlyRegistry() {
        ModContainer mod = Objects.requireNonNull(Loader.instance().activeModContainer());
        this.modId = mod.getModId();
        this.resourcePrefix = this.modId + ":";
        this.logger = LogManager.getLogger(mod.getName() + "/ModRegistry");
        this.recipes = new RecipeBuilder(modId);
        MinecraftForge.EVENT_BUS.register(new EventHandler(this));
    }

    /**
     * Gets the recipe maker.
     *
     * @return the recipe maker
     */
    public RecipeBuilder getRecipeMaker() {
        return recipes;
    }

    /**
     * Set the mod instance object.
     *
     * @param mod the new mod
     */
    public void setMod(Object mod) {
        this.mod = mod;
    }

    /**
     * Add a phased initializer, which has preInit, init, and postInit methods which ModRegistry will
     * call automatically.
     * <p>This method should be called during <em>pre-init</em> in the proper proxy,
     * <em>before</em> calling the ModRegistry's preInit method.</p>
     *
     * @param instance Your initializer (singleton design is recommended)
     */
    public void addPhasedInitializer(IPhaseInit instance) {
        this.phasedInitializers.add(instance);
    }

    /**
     * Adds a function that will be called when it is time to register objects for a certain class.
     * For example, adding a handler for class {@link Item} will call the function during {@link
     * net.minecraftforge.event.RegistryEvent.Register} for type {@link Item}.
     * <p>This method should be called during <em>pre-init</em> in the proper proxy.</p>
     *
     * @param registerFunction The function to call
     * @param registryClass    The registry object class
     * @throws RuntimeException if a handler for the class is already registered
     */
    public void addRegistrationHandler(Consumer<ReadOnlyRegistry> registerFunction, Class<? extends IForgeRegistryEntry<?>> registryClass) throws RuntimeException {
        if (this.registrationHandlers.containsKey(registryClass))
            throw new RuntimeException("Registration handler for class " + registryClass + " already registered!");
        this.registrationHandlers.put(registryClass, registerFunction);
    }

    /**
     * Make creative tab.
     *
     * @param label the label
     * @param icon the icon
     * @return the creative tabs
     */
    public CreativeTabs makeCreativeTab(String label, Supplier<ItemStack> icon) {
        CreativeTabs tab = new CreativeTabs(label) {

			@Override
			public ItemStack getTabIconItem() {
				return icon.get();
			}

        };
        if (defaultCreativeTab == null) defaultCreativeTab = tab;
        return tab;
    }

    //region Standard register methods (usually called within a registration handler)

    // Block

    /**
     * Register a Block. Its name (registry key/name) must be provided. Uses a new ItemBlockSL.
     *
     * @param <T> the generic type
     * @param block the block
     * @param key the key
     * @return the t
     */
    public <T extends Block> T registerBlock(T block, String key) {
        return registerBlock(block, key, defaultItemBlock(block));
    }

    /**
     * Default item block.
     *
     * @param <T> the generic type
     * @param block the block
     * @return the item block
     */
    @Nonnull
    private <T extends Block> ItemBlock defaultItemBlock(T block) {
        if (block instanceof BlockMetaSubtypes)
            return new ItemBlockMetaSubtypes((BlockMetaSubtypes) block);
        else
            return new ItemBlock(block);
    }

    /**
     * Register a Block. Its name registry name and ItemBlock must be provided.
     *
     * @param <T> the generic type
     * @param block the block
     * @param key the key
     * @param itemBlock the item block
     * @return the t
     */
    public <T extends Block> T registerBlock(T block, String key, ItemBlock itemBlock) {
        blocks.add(block);
        block.setUnlocalizedName(modId + "." + key);

        validateRegistryName(key);
        ResourceLocation name = new ResourceLocation(modId, key);
        safeSetRegistryName(block, name);
        ForgeRegistries.BLOCKS.register(block);

        // Register ItemBlock; TODO: Should this be done in Item register event?
        safeSetRegistryName(itemBlock, name);
        ForgeRegistries.ITEMS.register(itemBlock);

        // Register TileEntity
        if (block instanceof ITEBlock) {
            Class<? extends TileEntity> clazz = ((ITEBlock) block).getTileEntityClass();
            registerTileEntity(clazz, key);
        }

        if (block instanceof IAddRecipe) {
            this.recipeAdders.add((IAddRecipe) block);
        }

        if (MCUtil.isClient() && block instanceof IColorBlock) {
            this.coloredBlocks.add(block);
        }

        if (defaultCreativeTab != null) {
            block.setCreativeTab(defaultCreativeTab);
        }

        return block;
    }

    // Item

    /**
     * Register an Item. Its name (registry key/name) must be provided.
     *
     * @param <T> the generic type
     * @param item the item
     * @param key the key
     * @return the t
     */
    public <T extends Item> T registerItem(T item, String key) {
        items.add(item);
        item.setUnlocalizedName(modId + "." + key.toLowerCase());
        validateRegistryName(key);
        ResourceLocation name = new ResourceLocation(modId, key);
        safeSetRegistryName(item, name);
        ForgeRegistries.ITEMS.register(item);

        if (item instanceof IAddRecipe) {
            this.recipeAdders.add((IAddRecipe) item);
        }

        if (MCUtil.isClient() && item instanceof IColorItem) {
            this.coloredItems.add(item);
        }

        if (defaultCreativeTab != null) {
            item.setCreativeTab(defaultCreativeTab);
        }

        return item;
    }

    // Enchantment

    /**
     * Register enchantment.
     *
     * @param enchantment the enchantment
     * @param key the key
     */
    public void registerEnchantment(Enchantment enchantment, String key) {
        validateRegistryName(key);
        ResourceLocation name = new ResourceLocation(modId, key);
        safeSetRegistryName(enchantment, name);
        enchantment.setName(name.getResourceDomain() + "." + name.getResourcePath());
        ForgeRegistries.ENCHANTMENTS.register(enchantment);
    }

    // Entity

    /**
     * Automatically incrementing ID number for registering entities.
     */
    private int lastEntityId = -1;

    /**
     * Register entity.
     *
     * @param entityClass the entity class
     * @param key the key
     */
    public void registerEntity(Class<? extends Entity> entityClass, String key) {
        registerEntity(entityClass, key, ++lastEntityId, mod, 64, 20, true);
    }

    /**
     * Register entity.
     *
     * @param entityClass the entity class
     * @param key the key
     * @param trackingRange the tracking range
     * @param updateFrequency the update frequency
     * @param sendsVelocityUpdates the sends velocity updates
     */
    public void registerEntity(Class<? extends Entity> entityClass, String key, int trackingRange, int updateFrequency,
                               boolean sendsVelocityUpdates) {
        registerEntity(entityClass, key, ++lastEntityId, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
    }

    /**
     * Register entity.
     *
     * @param entityClass the entity class
     * @param key the key
     * @param id the id
     * @param mod the mod
     * @param trackingRange the tracking range
     * @param updateFrequency the update frequency
     * @param sendsVelocityUpdates the sends velocity updates
     */
    public void registerEntity(Class<? extends Entity> entityClass, String key, int id, Object mod, int trackingRange,
                               int updateFrequency, boolean sendsVelocityUpdates) {
        ResourceLocation resource = new ResourceLocation(modId, key);
        EntityRegistry.registerModEntity(resource, entityClass, key, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
    }

    /**
     * Register entity.
     *
     * @param entityClass the entity class
     * @param key the key
     * @param trackingRange the tracking range
     * @param updateFrequency the update frequency
     * @param sendsVelocityUpdates the sends velocity updates
     * @param eggPrimary the egg primary
     * @param eggSecondary the egg secondary
     */
    public void registerEntity(Class<? extends Entity> entityClass, String key, int trackingRange, int updateFrequency,
                               boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary) {
        registerEntity(entityClass, key, ++lastEntityId, mod, trackingRange, updateFrequency, sendsVelocityUpdates, eggPrimary, eggSecondary);
    }

    /**
     * Register entity.
     *
     * @param entityClass the entity class
     * @param key the key
     * @param id the id
     * @param mod the mod
     * @param trackingRange the tracking range
     * @param updateFrequency the update frequency
     * @param sendsVelocityUpdates the sends velocity updates
     * @param eggPrimary the egg primary
     * @param eggSecondary the egg secondary
     */
    public void registerEntity(Class<? extends Entity> entityClass, String key, int id, Object mod, int trackingRange,
                               int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary) {
        ResourceLocation resource = new ResourceLocation(modId, key);
        EntityRegistry.registerModEntity(resource, entityClass, key, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates, eggPrimary, eggSecondary);
    }

    /**
     * Register entity renderer.
     *
     * @param <T> the generic type
     * @param entityClass the entity class
     * @param renderFactory the render factory
     */
    @SideOnly(Side.CLIENT)
    public <T extends Entity> void registerEntityRenderer(Class<T> entityClass, IRenderFactory<T> renderFactory) {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
    }

    // Potion

    /**
     * Register potion.
     *
     * @param potion the potion
     * @param key the key
     */
    public void registerPotion(Potion potion, String key) {
        if (potion.getName().isEmpty())
            potion.setPotionName("effect." + modId + "." + key);

        validateRegistryName(key);
        ResourceLocation name = new ResourceLocation(this.modId, key);
        safeSetRegistryName(potion, name);
        ForgeRegistries.POTIONS.register(potion);
    }

    // Sound Events

    /**
     * Register sound event.
     *
     * @param sound the sound
     * @param key the key
     */
    public void registerSoundEvent(SoundEvent sound, String key) {
        validateRegistryName(key);
        ResourceLocation name = new ResourceLocation(modId, key);
        safeSetRegistryName(sound, name);
        ForgeRegistries.SOUND_EVENTS.register(sound);
    }

    // Loot

    /**
     * Register loot condition.
     *
     * @param serializer the serializer
     */
    public void registerLootCondition(LootCondition.Serializer<? extends LootCondition> serializer) {
        LootConditionManager.registerCondition(serializer);
    }

    /**
     * Register loot entity property.
     *
     * @param serializer the serializer
     */
    public void registerLootEntityProperty(EntityProperty.Serializer<? extends EntityProperty> serializer) {
        EntityPropertyManager.registerProperty(serializer);
    }

    /**
     * Register loot function.
     *
     * @param serializer the serializer
     */
    public void registerLootFunction(LootFunction.Serializer<? extends LootFunction> serializer) {
        LootFunctionManager.registerFunction(serializer);
    }

    /**
     * Register loot table.
     *
     * @param name the name
     */
    public void registerLootTable(String name) {
        LootTableList.register(new ResourceLocation(this.modId, name));
    }

    /**
     * Set the object's registry name, if it has not already been set. Logs a warning if it has.
     *
     * @param entry the entry
     * @param name the name
     */
    private void safeSetRegistryName(IForgeRegistryEntry<?> entry, ResourceLocation name) {
        if (entry.getRegistryName() == null)
            entry.setRegistryName(name);
        else
            logger.warn("Registry name for {} has already been set. Was trying to set it to {}.", entry.getRegistryName(), name);
    }

    /**
     * Ensure the given name does not contain upper case letters. This is not a problem until 1.13,
     * so just log it as a warning.
     *
     * @param name the name
     */
    private void validateRegistryName(String name) {
        if (PATTERN_REGISTRY_NAME.matcher(name).matches())
            logger.warn("Invalid name for object: {}", name);
    }

    /**
     * Register advancement trigger.
     *
     * @param <T> the generic type
     * @param trigger the trigger
     * @return the i criterion trigger
     */
    // Advancements
    public <T extends ICriterionInstance> ICriterionTrigger<T> registerAdvancementTrigger(ICriterionTrigger<T> trigger) {
        CriteriaTriggers.register(trigger);
        return trigger;
    }

    /**
     * Register a TileEntity. "tile." + resourcePrefix is automatically prepended to the key.
     *
     * @param tileClass the tile class
     * @param key the key
     */
    public void registerTileEntity(Class<? extends TileEntity> tileClass, String key) {
        GameRegistry.registerTileEntity(tileClass, new ResourceLocation(modId, key));
    }

    /**
     * Registers a renderer for a TileEntity.
     *
     * @param <T> the generic type
     * @param tileClass the tile class
     * @param renderer the renderer
     */
    @SideOnly(Side.CLIENT)
    public <T extends TileEntity> void registerTileEntitySpecialRenderer(Class<T> tileClass, TileEntitySpecialRenderer<T> renderer) {
        ClientRegistry.bindTileEntitySpecialRenderer(tileClass, renderer);
    }

    // Model registration wrappers

    /**
     * Sets the model.
     *
     * @param block the block
     * @param meta the meta
     * @param modelPath the model path
     */
    @SideOnly(Side.CLIENT)
    public void setModel(Block block, int meta, String modelPath) {
        setModel(Item.getItemFromBlock(block), meta, modelPath, "inventory");
    }

    /**
     * Sets the model.
     *
     * @param block the block
     * @param meta the meta
     * @param modelPath the model path
     * @param variant the variant
     */
    @SideOnly(Side.CLIENT)
    public void setModel(Block block, int meta, String modelPath, String variant) {
        setModel(Item.getItemFromBlock(block), meta, modelPath, variant);
    }

    /**
     * Sets the model.
     *
     * @param item the item
     * @param meta the meta
     * @param modelPath the model path
     */
    @SideOnly(Side.CLIENT)
    public void setModel(Item item, int meta, String modelPath) {
        setModel(item, meta, modelPath, "inventory");
    }

    /**
     * Sets the model.
     *
     * @param item the item
     * @param meta the meta
     * @param modelPath the model path
     * @param variant the variant
     */
    @SideOnly(Side.CLIENT)
    public void setModel(Item item, int meta, String modelPath, String variant) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(this.resourcePrefix + modelPath, variant));
    }

    //endregion

    //region Initialization phases

    /*
     * Initialization phases. Calling in either your common or client proxy is recommended. "client" methods in your
     * client proxy, the rest in your common AND client proxy.
     */

    /** The pre init done. */
    private boolean preInitDone = false;
    
    /** The init done. */
    private boolean initDone = false;
    
    /** The post init done. */
    private boolean postInitDone = false;

    /**
     * Call in the "preInit" phase in your common proxy.
     *
     * @param event the event
     */
    public void preInit(FMLPreInitializationEvent event) {
        if (this.preInitDone) {
            logger.warn("preInit called more than once!");
            return;
        }

        verifyOrFindModObject();
        this.phasedInitializers.forEach(i -> i.preInit(this, event));
        this.preInitDone = true;
    }

    /**
     * Verify or find mod object.
     */
    private void verifyOrFindModObject() {
        if (mod == null) {
            logger.warn("Mod {} did not manually set its mod object! This is bad and may cause crashes.", modId);
            ModContainer container = Loader.instance().getIndexedModList().get(modId);
            if (container != null) {
                this.mod = container.getMod();
                logger.warn("Automatically acquired mod object for {}", modId);
            } else {
                logger.warn("Could not find mod object. The mod ID is likely incorrect.");
            }
        }
    }

    /**
     * Call in the "init" phase in your common proxy.
     *
     * @param event the event
     */
    public void init(FMLInitializationEvent event) {
        if (this.initDone) {
            logger.warn("init called more than once!");
            return;
        }
        this.phasedInitializers.forEach(i -> i.init(this, event));
        this.initDone = true;
    }

    /**
     * Call in the "postInit" phase in your common proxy.
     *
     * @param event the event
     */
    public void postInit(FMLPostInitializationEvent event) {
        if (this.postInitDone) {
            logger.warn("postInit called more than once!");
            return;
        }

        int oldRecipeRegisterCount = recipes.getOldRecipeRegisterCount();
        if (oldRecipeRegisterCount > 0) {
            long totalRecipes = ForgeRegistries.RECIPES.getKeys().stream()
                    .map(ResourceLocation::getResourceDomain)
                    .filter(s -> s.equals(modId))
                    .count();
            logger.warn("Mod '{}' is still registering recipes with RecipeMaker ({} recipes, out of {} total)",
                    modId, oldRecipeRegisterCount, totalRecipes);
        }

        this.phasedInitializers.forEach(i -> i.postInit(this, event));
        this.postInitDone = true;
    }

    /**
     * Call in the "preInit" phase in your client proxy.
     *
     * @param event the event
     */
    @SideOnly(Side.CLIENT)
    public void clientPreInit(FMLPreInitializationEvent event) {
    }

    /**
     * Call in the "init" phase in your client proxy.
     *
     * @param event the event
     */
    @SideOnly(Side.CLIENT)
    public void clientInit(FMLInitializationEvent event) {
        for (Block block : this.blocks) {
            if (block instanceof ITEBlock) {
            	ITEBlock tileBlock = (ITEBlock) block;
                final TileEntitySpecialRenderer tesr = tileBlock.getTileRenderer();
                if (tesr != null) {
                    ClientRegistry.bindTileEntitySpecialRenderer(tileBlock.getTileEntityClass(), tesr);
                }
            }
        }
    }

    /**
     * Call in the "postInit" phase in your client proxy.
     *
     * @param event the event
     */
    @SideOnly(Side.CLIENT)
    public void clientPostInit(FMLPostInitializationEvent event) {
    }

    //endregion

    /**
     * Adds the recipes.
     */
    private void addRecipes() {
        this.recipeAdders.forEach(obj -> obj.addRecipes(this.recipes));
    }

    /**
     * Adds the ore dict entries.
     */
    private void addOreDictEntries() {
        this.recipeAdders.forEach(IAddRecipe::addOreDict);
    }

    /**
     * Register models.
     */
    @SideOnly(Side.CLIENT)
    private void registerModels() {
        for (Block block : blocks) {
            if (block instanceof ICustomModel) {
                ((ICustomModel) block).registerModels();
            } else {
                ResourceLocation registryName = Objects.requireNonNull(block.getRegistryName());
                ModelResourceLocation model = new ModelResourceLocation(registryName, "inventory");
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
            }
        }
        for (Item item : items) {
            if (item instanceof ICustomMesh) {
            	ICustomMesh customMesh = (ICustomMesh) item;
                ModelBakery.registerItemVariants(item, customMesh.getVariants());
                ModelLoader.setCustomMeshDefinition(item, customMesh.getCustomMesh());
            } else if (item instanceof ICustomModel) {
                ((ICustomModel) item).registerModels();
            } else {
                ResourceLocation registryName = Objects.requireNonNull(item.getRegistryName());
                ModelResourceLocation model = new ModelResourceLocation(registryName, "inventory");
                ModelLoader.setCustomModelResourceLocation(item, 0, model);
            }
        }
    }

    /**
     * Gets the default creative tab.
     *
     * @return the default creative tab
     */
    public CreativeTabs getDefaultCreativeTab() {
		return defaultCreativeTab;
	}

	/**
	 * Sets the default creative tab.
	 *
	 * @param defaultCreativeTab the new default creative tab
	 */
	public void setDefaultCreativeTab(CreativeTabs defaultCreativeTab) {
		this.defaultCreativeTab = defaultCreativeTab;
	}

	/**
     * Handles the new Forge RegistryEvents. An instance will automatically be registered when an
     * StellarRegistry is constructed.

     */
    public static class EventHandler {
        
        /** The Mod registry. */
        private final ReadOnlyRegistry ModRegistry;

        /**
         * Instantiates a new event handler.
         *
         * @param ModRegistry the mod registry
         */
        public EventHandler(ReadOnlyRegistry ModRegistry) {
            this.ModRegistry = ModRegistry;
        }

        /**
         * Run registration handler if present.
         *
         * @param registryClass the registry class
         */
        private void runRegistrationHandlerIfPresent(Class<? extends IForgeRegistryEntry<?>> registryClass) {
            if (ModRegistry.registrationHandlers.containsKey(registryClass))
                ModRegistry.registrationHandlers.get(registryClass).accept(ModRegistry);
        }

        /**
         * Register blocks.
         *
         * @param event the event
         */
        @SubscribeEvent
        public void registerBlocks(RegistryEvent.Register<Block> event) {
            runRegistrationHandlerIfPresent(Block.class);
        }

        /**
         * Register items.
         *
         * @param event the event
         */
        @SubscribeEvent
        public void registerItems(RegistryEvent.Register<Item> event) {
            runRegistrationHandlerIfPresent(Item.class);
            ModRegistry.addOreDictEntries();
        }

        /**
         * Register potions.
         *
         * @param event the event
         */
        @SubscribeEvent
        public void registerPotions(RegistryEvent.Register<Potion> event) {
            runRegistrationHandlerIfPresent(Potion.class);
        }

        /**
         * Register biomes.
         *
         * @param event the event
         */
        @SubscribeEvent
        public void registerBiomes(RegistryEvent.Register<Biome> event) {
            runRegistrationHandlerIfPresent(Biome.class);
        }

        /**
         * Register sound events.
         *
         * @param event the event
         */
        @SubscribeEvent
        public void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
            runRegistrationHandlerIfPresent(SoundEvent.class);
        }

        /**
         * Register potion types.
         *
         * @param event the event
         */
        @SubscribeEvent
        public void registerPotionTypes(RegistryEvent.Register<PotionType> event) {
            runRegistrationHandlerIfPresent(PotionType.class);
        }

        /**
         * Register enchantments.
         *
         * @param event the event
         */
        @SubscribeEvent
        public void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
            runRegistrationHandlerIfPresent(Enchantment.class);
        }

        /**
         * Register villager professions.
         *
         * @param event the event
         */
        @SubscribeEvent
        public void registerVillagerProfessions(RegistryEvent.Register<VillagerProfession> event) {
            runRegistrationHandlerIfPresent(VillagerProfession.class);
        }

        /**
         * Register entities.
         *
         * @param event the event
         */
        @SubscribeEvent
        public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
            runRegistrationHandlerIfPresent(EntityEntry.class);
        }

        /**
         * Register recipes.
         *
         * @param event the event
         */
        @SubscribeEvent
        public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
            runRegistrationHandlerIfPresent(IRecipe.class);
            ModRegistry.addRecipes();
        }

        /**
         * Register models.
         *
         * @param event the event
         */
        @SubscribeEvent
        public void registerModels(ModelRegistryEvent event) {
            ModRegistry.registerModels();
        }

        /**
         * Register block colors.
         *
         * @param event the event
         */
        @SideOnly(Side.CLIENT)
        @SubscribeEvent
        public void registerBlockColors(ColorHandlerEvent.Block event) {
            BlockColors blockColors = event.getBlockColors();
            for (Block block : ModRegistry.coloredBlocks)
                blockColors.registerBlockColorHandler(((IColorBlock) block).getColorHandler(), block);
        }

        /**
         * Register item colors.
         *
         * @param event the event
         */
        @SideOnly(Side.CLIENT)
        @SubscribeEvent
        public void registerItemColors(ColorHandlerEvent.Item event) {
            ItemColors itemColors = event.getItemColors();
            for (Block block : ModRegistry.coloredBlocks)
                itemColors.registerItemColorHandler(((IColorBlock) block).getItemColorHandler(), Item.getItemFromBlock(block));
            for (Item item : ModRegistry.coloredItems)
                itemColors.registerItemColorHandler(((IColorItem) item).getColorHandler(), item);
        }
    }
}
