package com.brownbear85.onyxarcanix.init;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.blocks.entities.ItemHolderBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, OnyxArcanix.MODID);

    public static final RegistryObject<BlockEntityType<ItemHolderBlockEntity>> ITEM_HOLDER_BLOCK_ENTITY = BLOCK_ENTITIES.register("pedestal",
            () -> BlockEntityType.Builder.of(ItemHolderBlockEntity::new,
    /* blocks */ BlockInit.STONE_PEDESTAL.get()
            ).build(null));
}
