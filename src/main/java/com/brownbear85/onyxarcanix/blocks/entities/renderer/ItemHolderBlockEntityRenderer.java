package com.brownbear85.onyxarcanix.blocks.entities.renderer;

import com.brownbear85.onyxarcanix.blocks.BaseItemHolder;
import com.brownbear85.onyxarcanix.blocks.entities.ItemHolderBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class ItemHolderBlockEntityRenderer implements BlockEntityRenderer<ItemHolderBlockEntity> {
    public ItemHolderBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    private float rotation() {
        return System.currentTimeMillis() % 360000 * 0.001F;
    }

    private double bobHeight() {
        return Math.sin(System.currentTimeMillis() % Math.round(Math.PI * 4000) * 0.002F) * 0.1;
    }

    @Override
    public void render(ItemHolderBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        if (blockEntity.getBlockState().getBlock() instanceof BaseItemHolder itemHolderBlock) {
            ItemStack stack = blockEntity.getRenderStack();
            poseStack.pushPose();
            poseStack.translate(itemHolderBlock.itemX, itemHolderBlock.itemY + bobHeight(), itemHolderBlock.itemZ);
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(Vector3f.YP.rotation(rotation()));

            itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, getLightLevel(blockEntity.getLevel(), blockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, poseStack, bufferSource, 1);
            poseStack.popPose();
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
