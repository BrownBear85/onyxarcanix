package com.brownbear85.onyxarcanix.integration;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.recipe.AltarRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIOnyxArcanixPlugin implements IModPlugin {
    public static RecipeType<AltarRecipe> ALTAR_TYPE = new RecipeType<>(AltarRecipeCategory.UID, AltarRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(OnyxArcanix.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<AltarRecipe> altarRecipes = manager.getAllRecipesFor(AltarRecipe.Type.INSTANCE);
        registration.addRecipes(ALTAR_TYPE, altarRecipes);
    }
}
