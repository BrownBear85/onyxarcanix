package com.brownbear85.onyxarcanix.integration;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.init.BlockInit;
import com.brownbear85.onyxarcanix.recipe.AltarRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
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
    private final IDrawable icon;

    public AltarRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 100, 100);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockInit.ALTAR.get()));
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
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AltarRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 0, 0).addIngredients(recipe.getIngredients().get(0));
    }
}
