package com.brownbear85.onyxarcanix.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

import java.util.ArrayList;

@AutoRegisterCapability
public class PlayerRunes {
    public static Capability<PlayerRunes> PLAYER_RUNES = CapabilityManager.get(new CapabilityToken<PlayerRunes>() { });

    private ArrayList<String> runes = new ArrayList<>();

    public String[] getRunes() {
        return runes.toArray(new String[] {});
    }

    public void addRune(String add) {
        if (!runes.contains(add)) {
            runes.add(add);
        }
    }

    public void copyFrom(PlayerRunes source) {
        this.runes = source.runes;
    }

    public void saveNBTData(CompoundTag nbt) {
        ListTag list = new ListTag();
        for (String str : runes) {
            list.add(StringTag.valueOf(str));
        }
        nbt.put("unlocked_runes", list);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.runes.clear();
        ListTag list = (ListTag) nbt.get("unlocked_runes");
        for (int i = 0; i < list.size(); i++) {
            this.runes.add(list.get(i).getAsString());
        }
    }
}
