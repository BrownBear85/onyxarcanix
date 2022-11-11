package com.brownbear85.onyxarcanix.recipe;

import com.brownbear85.onyxarcanix.OnyxArcanix;
import com.brownbear85.onyxarcanix.blocks.entities.AltarBlockEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AltarRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final AltarBlockEntity.Type altar;

    public AltarRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, AltarBlockEntity.Type altar) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.altar = altar;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        boolean rot0 = recipeItems.get(0).test(pContainer.getItem(0)) &&
                recipeItems.get(1).test(pContainer.getItem(1)) &&
                recipeItems.get(2).test(pContainer.getItem(2)) &&
                recipeItems.get(3).test(pContainer.getItem(3)) &&
                recipeItems.get(4).test(pContainer.getItem(4));
        boolean rot90 = recipeItems.get(0).test(pContainer.getItem(0)) &&
                recipeItems.get(1).test(pContainer.getItem(4)) &&
                recipeItems.get(2).test(pContainer.getItem(3)) &&
                recipeItems.get(3).test(pContainer.getItem(1)) &&
                recipeItems.get(4).test(pContainer.getItem(2));
        boolean rot180 = recipeItems.get(0).test(pContainer.getItem(0)) &&
                recipeItems.get(1).test(pContainer.getItem(2)) &&
                recipeItems.get(2).test(pContainer.getItem(1)) &&
                recipeItems.get(3).test(pContainer.getItem(4)) &&
                recipeItems.get(4).test(pContainer.getItem(3));
        boolean rot270 = recipeItems.get(0).test(pContainer.getItem(0)) &&
                recipeItems.get(1).test(pContainer.getItem(3)) &&
                recipeItems.get(2).test(pContainer.getItem(4)) &&
                recipeItems.get(3).test(pContainer.getItem(2)) &&
                recipeItems.get(4).test(pContainer.getItem(1));
        return rot0 || rot90 || rot180 || rot270;
    }

    public AltarBlockEntity.Type getAltar() {
        return altar;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AltarRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "altar_crafting";
    }

    public static class Serializer implements  RecipeSerializer<AltarRecipe>  {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(OnyxArcanix.MODID, "altar_crafting");

        @Override
        public AltarRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            AltarBlockEntity.Type altar = AltarBlockEntity.Type.valueOf(pSerializedRecipe.get("altar").getAsString());

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(5, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new AltarRecipe(pRecipeId, output, inputs, altar);
        }

        @Override
        public @Nullable AltarRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            AltarBlockEntity.Type altar = pBuffer.readEnum(AltarBlockEntity.Type.class);
            return new AltarRecipe(pRecipeId, output, inputs, altar);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, AltarRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(), false);
            pBuffer.writeEnum(pRecipe.altar);
        }
    }
}
