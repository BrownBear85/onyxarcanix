package com.brownbear85.onyxarcanix.init;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.blocks.AltarBlock;
import com.brownbear85.onyxarcanix.blocks.ChiselableBlock;
import com.brownbear85.onyxarcanix.blocks.PedestalBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OnyxArcanix.MODID);

    private static final BlockBehaviour.Properties ONYX_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(2.4F, 6.0F);

    /* deco blocks */

    public static final RegistryObject<Block> ONYX_BLOCK = register("onyx",
            () -> new Block(ONYX_PROPERTIES), ItemInit.properties());

    public static final RegistryObject<Block> ONYX_STAIRS = register("onyx_stairs",
            () -> new StairBlock(() -> ONYX_BLOCK.get().defaultBlockState(), ONYX_PROPERTIES), ItemInit.properties());

    public static final RegistryObject<Block> ONYX_SLAB = register("onyx_slab",
            () -> new SlabBlock(ONYX_PROPERTIES), ItemInit.properties());

    public static final RegistryObject<Block> ONYX_WALL = register("onyx_wall",
            () -> new WallBlock(ONYX_PROPERTIES), ItemInit.properties());



    public static final RegistryObject<Block> ONYX_BRICKS = register("onyx_bricks",
            () -> new Block(ONYX_PROPERTIES), ItemInit.properties());

    public static final RegistryObject<Block> ONYX_BRICK_STAIRS = register("onyx_brick_stairs",
            () -> new StairBlock(() -> ONYX_BRICKS.get().defaultBlockState(), ONYX_PROPERTIES), ItemInit.properties());

    public static final RegistryObject<Block> ONYX_BRICK_SLAB = register("onyx_brick_slab",
            () -> new SlabBlock(ONYX_PROPERTIES), ItemInit.properties());

    public static final RegistryObject<Block> ONYX_BRICK_WALL = register("onyx_brick_wall",
            () -> new WallBlock(ONYX_PROPERTIES), ItemInit.properties());



    public static final RegistryObject<Block> CHISELED_ONYX = register("chiseled_onyx_bricks",
            () -> new Block(ONYX_PROPERTIES), ItemInit.properties());

    /* special blocks */

    public static final RegistryObject<Block> RUNED_STONE_BRICKS = register("runed_stone_bricks",
            () -> new ChiselableBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), ItemInit.properties());

    public static final RegistryObject<Block> STONE_PEDESTAL = register("stone_pedestal",
            () -> new PedestalBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F).dynamicShape().noOcclusion(), 0.5F, 1.5F, 0.5F), ItemInit.properties());



    public static final RegistryObject<Block> ALTAR = register("altar",
            () -> new AltarBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F).dynamicShape().noOcclusion(), 0.5F, 1.0F, 0.5F),
            ItemInit.properties().stacksTo(1));

    public static final RegistryObject<Block> ONYX_ALTAR = register("onyx_altar",
            () -> new AltarBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F).dynamicShape().noOcclusion(), 0.5F, 1.0F, 0.5F),
            ItemInit.properties().stacksTo(1));


    /* registration methods */

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier, Item.Properties properties) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }
}
