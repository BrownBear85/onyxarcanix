package com.brownbear85.onyxarcanix.runes;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.brownbear85.onyxarcanix.runes.PlayerRunes.PLAYER_RUNES;

public class PlayerRunesProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {


    private PlayerRunes runes = null;
    private final LazyOptional<PlayerRunes> optional = LazyOptional.of(this::createPlayerRunes);

    String[] DEFAULT_UNLOCKED_RUNES = {"b", "h", "n", "u"};
    private PlayerRunes createPlayerRunes() {
        if (this.runes == null) {
            this.runes = new PlayerRunes();
            for (String str : DEFAULT_UNLOCKED_RUNES) {
                this.runes.addRune(str);
            }
        }

        return this.runes;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_RUNES) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerRunes().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerRunes().loadNBTData(nbt);
    }
}
