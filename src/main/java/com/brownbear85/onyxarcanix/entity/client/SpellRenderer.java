package com.brownbear85.onyxarcanix.entity.client;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SpellRenderer extends EntityRenderer {
    public SpellRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(Entity pEntity) {
        return new ResourceLocation(OnyxArcanix.MODID, "textures/entity/spell.png");
    }
}
