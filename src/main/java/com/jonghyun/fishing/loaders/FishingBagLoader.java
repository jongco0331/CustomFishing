package com.jonghyun.fishing.loaders;

import com.jonghyun.fishing.manager.FishingBagManager;
import com.jonghyun.fishing.utils.FileUtil;
import net.minecraft.server.v1_12_R1.MojangsonParser;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
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
        FishingBagManager.getInstance().bagMap.clear();
        File file = new File("plugins/CustomFishing/PlayerData");
        if(!file.exists()) {
            file.mkdir();
        }
        for(File f : file.listFiles())
        {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
            UUID uuid = UUID.fromString(f.getName().replaceAll(".yml", ""));

            List<ItemStack> fishes = new ArrayList<>();
            for(String key : yaml.getKeys(false))
            {
                try {
                    NBTTagCompound comp = MojangsonParser.parse(yaml.getString(key));
                    net.minecraft.server.v1_12_R1.ItemStack stack = new net.minecraft.server.v1_12_R1.ItemStack(comp);
                    fishes.add(CraftItemStack.asBukkitCopy(stack));
                } catch(Exception e ) {e.printStackTrace();}
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
            if(entry.getKey() != null)
            {
                File file = new File("plugins/CustomFishing/PlayerData/" + entry.getKey() + ".yml");
                YamlConfiguration yaml = new YamlConfiguration();
                int i = 0;
                if(entry.getValue() != null)
                {
                    for(ItemStack stack : entry.getValue())
                    {
                        if(stack != null)
                        {
                            net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(stack);
                            NBTTagCompound tag = new NBTTagCompound();
                            nms.save(tag);
                            yaml.set(i + "", tag.toString());
                            i++;
                        }
                    }
                }
                try {
                    yaml.save(file);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
