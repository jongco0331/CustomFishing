package com.jonghyun.fishing.manager;

import com.jonghyun.fishing.objects.fish.CustomFish;
import com.jonghyun.fishing.objects.fish.LengthFish;
import com.jonghyun.fishing.objects.MiniGame;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public final class FishingManager {

    private static FishingManager fishManager = null;

    private FishingManager() {}

    public static FishingManager getInstance()
    {
        if(fishManager == null)
            fishManager = new FishingManager();
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
        String lengthStr = length + "";
        String newLength = "";
        char[] le = lengthStr.toCharArray();
        for(char a : le)
        {
            newLength += "§" + a;
        }
        lore.set(0, lore.get(0) + " §L§E" + newLength);
        meta.setLore(lore);
        fish.setItemMeta(meta);
        return new LengthFish(fish, length);
    }

    public double getLengthOfFish(ItemStack fish)
    {
        ItemMeta meta = fish.getItemMeta();
        return Double.parseDouble(meta.getLore().get(0).split("§L§E")[1].replaceAll("§", ""));
    }

    public boolean isBetween(double current, double min, double max)
    {
        if(current >= min && current <= max)
            return true;
        return false;
    }
}
