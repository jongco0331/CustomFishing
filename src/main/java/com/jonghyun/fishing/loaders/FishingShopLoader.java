package com.jonghyun.fishing.loaders;

import com.jonghyun.fishing.manager.FishingShopManager;
import com.jonghyun.fishing.objects.shop.EventClickType;
import com.jonghyun.fishing.objects.shop.EventObject;
import com.jonghyun.fishing.objects.shop.FishShop;
import com.jonghyun.fishing.utils.FileUtil;
import com.jonghyun.fishing.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;

public class FishingShopLoader implements IDataLoader {

    @Override
    public void load() {
        FishingShopManager.getInstance().getFishingShops().clear();
        File file = new File("plugins/CustomFishing/shops");
        if(!file.exists())
        {
            file.mkdir();
            FileUtil.createDir("shops/TestShop");
        }

        for(File f : file.listFiles())
        {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
            String[] contents = yaml.getString("shapes").split(" ");
            HashMap<String, EventObject> componentsMap = new HashMap<>();
            ConfigurationSection section = yaml.getConfigurationSection("components");
            for(String key : section.getKeys(false))
            {
                ConfigurationSection configurationSection = section.getConfigurationSection(key);
                ItemStack stack = ItemUtil.getYamlItem(configurationSection);
                HashMap<EventClickType, String> eventMap = new HashMap<>();
                if(stack != null)
                {
                    for(String event : configurationSection.getConfigurationSection("event").getKeys(false))
                    {
                        eventMap.put(EventClickType.value(event), configurationSection.getConfigurationSection("event").getString(event));
                    }
                }
                componentsMap.put(key, new EventObject(stack, eventMap));
            }
            String npc = yaml.getString("npc");
            FishingShopManager.getInstance().getFishingShops().put(f.getName().replaceAll(".yml", ""), new FishShop(contents, componentsMap, npc));
        }
    }

    @Override
    public void save() {

    }


}
