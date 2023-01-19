package com.jonghyun.fishing.manager;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class FishingBagManager {

    private static FishingBagManager fishingBagManager = null;

    private FishingBagManager() {}

    public static FishingBagManager getInstance()
    {
        if(fishingBagManager == null)
            fishingBagManager = new FishingBagManager();
        return fishingBagManager;
    }

    public HashMap<UUID, List<ItemStack>> bagMap = new HashMap<>();

}
