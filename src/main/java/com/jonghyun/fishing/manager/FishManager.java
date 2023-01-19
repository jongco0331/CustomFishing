package com.jonghyun.fishing.manager;

import com.jonghyun.fishing.object.CustomFish;
import com.jonghyun.fishing.object.LengthFish;
import com.jonghyun.fishing.object.MiniGame;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public final class FishManager {

    private static FishManager fishManager = null;

    private FishManager() {}

    public static FishManager getInstance()
    {
        if(fishManager == null)
            fishManager = new FishManager();
        return fishManager;
    }
    @Getter @Setter
    private HashMap<String, CustomFish> customFishes = new HashMap<>();
    @Getter
    private HashMap<String, List<CustomFish>> sortedRankFishes = new HashMap<>();
    @Getter
    private HashMap<UUID, MiniGame> miniGameData = new HashMap<>();

    public CustomFish getRandomFish()
    {
        String rank = RankManager.getInstance().randomRank();
        if(rank == null)
            return null;
        int size = sortedRankFishes.get(rank).size();
        return sortedRankFishes.get(rank).get(new Random().nextInt(size));
    }

    public LengthFish getRandomLengthFish()
    {
        CustomFish customFish = getRandomFish();
        ItemStack fish = customFish.getFish().clone();
        ItemMeta meta = fish.getItemMeta();
        List<String> lore = meta.getLore();
        Random r = new SecureRandom();
        double length = customFish.getMin() + r.nextDouble() * (customFish.getMax()-customFish.getMin());
        length = (Math.round(length*100))/100.0;
        for(int i = 0; i < lore.size(); i++)
        {
            lore.set(i, lore.get(i).replaceAll("<cm>", length + ""));
        }
        meta.setLore(lore);
        fish.setItemMeta(meta);
        return new LengthFish(fish, length);
    }

    public IChatBaseComponent bukkitStackToChatComponent(ItemStack stack) {
        net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(stack);
        return nms.C();
    }

}
