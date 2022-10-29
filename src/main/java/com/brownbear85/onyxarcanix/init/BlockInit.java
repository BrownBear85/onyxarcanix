package com.brownbear85.onyxarcanix.init;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.blocks.Chiselable;
import com.brownbear85.onyxarcanix.blocks.Pedestal;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OnyxArcanix.MODID);

    public static final RegistryObject<Block> ONYX_BLOCK = register("onyx",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(2.4F, 6.0F)), ItemInit.properties());

    public static final RegistryObject<Block> ONYX_BRICKS = register("onyx_bricks",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(2.4F, 6.0F)), ItemInit.properties());

    public static final RegistryObject<Block> RUNED_STONE_BRICKS = register("runed_stone_bricks",
            () -> new Chiselable(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6.0F)), ItemInit.properties());

    public static final RegistryObject<Block> STONE_PEDESTAL = register("stone_pedestal",
            () -> new Pedestal(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F, 6.0F).dynamicShape().noOcclusion(), 0.5F, 1.5F, 0.5F), ItemInit.properties());

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier, Item.Properties properties) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }
}
