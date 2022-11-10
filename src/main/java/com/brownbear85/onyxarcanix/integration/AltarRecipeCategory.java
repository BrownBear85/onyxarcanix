package com.brownbear85.onyxarcanix.integration;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.init.BlockInit;
import com.brownbear85.onyxarcanix.recipe.AltarRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class AltarRecipeCategory implements IRecipeCategory<AltarRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(OnyxArcanix.MODID, "altar_crafting");
    public static final ResourceLocation TEXTURE = new ResourceLocation(OnyxArcanix.MODID, "textures/gui/altar_crafting_gui.png");

    private final IDrawable background;
    private final IDrawable altar;
    private final IDrawable pedestal;

    private final IGuiHelper helper;

    public AltarRecipeCategory(IGuiHelper helper) {
        this.helper = helper;
        this.background = helper.createDrawable(TEXTURE, 0, 0, 100, 100);
        this.altar = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockInit.ALTAR.get()));
        this.pedestal = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockInit.STONE_PEDESTAL.get()));
    }

    @Override
    public RecipeType<AltarRecipe> getRecipeType() {
        return JEIOnyxArcanixPlugin.ALTAR_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Altar");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.altar;
    }

    @Override
    public void draw(AltarRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        altar.draw(stack, 42, 44);
        pedestal.draw(stack, 42, 14);
        pedestal.draw(stack, 42, 74);
        pedestal.draw(stack, 12, 44);
        pedestal.draw(stack, 72, 44);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AltarRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 42, 34).addIngredients(recipe.getIngredients().get(0)).setOverlay(helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, recipe.getIngredients().get(0).getItems()[0]), 0, 0);
        builder.addSlot(RecipeIngredientRole.INPUT, 42, 4).addIngredients(recipe.getIngredients().get(1)).setOverlay(helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, recipe.getIngredients().get(1).getItems()[0]), 0, 0);;
        builder.addSlot(RecipeIngredientRole.INPUT, 42, 64).addIngredients(recipe.getIngredients().get(2)).setOverlay(helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, recipe.getIngredients().get(2).getItems()[0]), 0, 0);;
        builder.addSlot(RecipeIngredientRole.INPUT, 12, 34).addIngredients(recipe.getIngredients().get(3)).setOverlay(helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, recipe.getIngredients().get(3).getItems()[0]), 0, 0);;
        builder.addSlot(RecipeIngredientRole.INPUT, 72, 34).addIngredients(recipe.getIngredients().get(4)).setOverlay(helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, recipe.getIngredients().get(4).getItems()[0]), 0, 0);;
        builder.addSlot(RecipeIngredientRole.OUTPUT, 77, 77).addItemStack(recipe.getResultItem());
    }
}
