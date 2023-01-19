package com.jonghyun.fishing.loaders;

import com.jonghyun.fishing.manager.FishingBagManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FishingBagLoader implements IDataLoader {

    @Override
    public void load() {
        File file = new File("plugins/CustomFishing/PlayerData");
        if(!file.exists())
            file.mkdir();
        for(File f : file.listFiles())
        {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            UUID uuid = UUID.fromString(f.getName().replaceAll(".yml", ""));

            List<ItemStack> fishes = new ArrayList<>();
            for(String key : yaml.getKeys(false))
            {
                if(yaml.getItemStack(key) != null)
                fishes.add(yaml.getItemStack(key));
            }
            FishingBagManager.getInstance().bagMap.put(uuid, fishes);
        }
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(!FishingBagManager.getInstance().bagMap.containsKey(p.getUniqueId()))
                FishingBagManager.getInstance().bagMap.put(p.getUniqueId(), new ArrayList<>());
        }
    }

    @Override
    public void save() {
        for(Map.Entry<UUID, List<ItemStack>> entry : FishingBagManager.getInstance().bagMap.entrySet())
        {
            File file = new File("plugins/CustomFishing/PlayerData/" + entry.getKey() + ".yml");
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            int i = 0;
            for(ItemStack stack : entry.getValue())
            {
                yaml.set(i + "", stack);
                i++;
            }
            try {
                yaml.save(file);
            } catch(Exception e) {}
        }
    }
}
