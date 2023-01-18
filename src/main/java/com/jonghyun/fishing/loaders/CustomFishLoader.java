package com.jonghyun.fishing.loaders;

import com.jonghyun.fishing.Glow;
import com.jonghyun.fishing.manager.FishManager;
import com.jonghyun.fishing.manager.RankManager;
import com.jonghyun.fishing.object.CustomFish;
import com.jonghyun.fishing.object.ExchangeItem;
import com.jonghyun.fishing.object.SellPrice;
import com.jonghyun.fishing.utils.FileUtil;
import com.jonghyun.fishing.utils.StringUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Fish;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomFishLoader implements IDataLoader {

    Glow glow = new Glow(255);

    @Override
    public void load() {
        FishManager.getInstance().getCustomFishes().clear();
        FishManager.getInstance().getSortedRankFishes().clear();

        for(String rank : RankManager.getInstance().getRanks())
        {
            FishManager.getInstance().getSortedRankFishes().put(rank, new ArrayList<>());
        }

        File dir = FileUtil.getFolder("fishes");
        if(!dir.exists())
        {
            dir.mkdir();
        }
        for(File f : dir.listFiles())
        {
            if(!f.getName().contains(".yml"))
                continue;
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
            for(String id : yaml.getKeys(false))
            {
                ConfigurationSection section = yaml.getConfigurationSection(id);
                String rank = section.getString("rank");
                if(!RankManager.getInstance().getRanks().contains(rank)) {
                    System.out.println("[ERROR] 존재하지 않는 랭크 데이터 " + rank);
                    continue;
                }

                ItemStack fish = new ItemStack(Material.getMaterial(section.getString("material")));
                ItemMeta fishMeta = fish.getItemMeta();
                if(section.get("display-name") != null)
                    fishMeta.setDisplayName(StringUtil.colorize(section.getString("display-name")));
                if(section.get("lore") != null)
                    fishMeta.setLore(StringUtil.colorize(section.getStringList("lore")));
                if(section.get("glowing") != null)
                {
                    if(section.getBoolean("glowing"))
                    {
                        fishMeta.addEnchant(glow, 1, true);
                    }
                }
                fish.setItemMeta(fishMeta);

                double min = section.getDouble("cm.min");
                double max = section.getDouble("cm.max");

                List<SellPrice> prices = new ArrayList<>();
                for(String price : section.getConfigurationSection("price").getKeys(false))
                {
                    prices.add(new SellPrice(price, section.getLong("price." + price)));
                }

                List<ExchangeItem> exchangeItems = new ArrayList<>();
                for(String price : section.getConfigurationSection("item").getKeys(false))
                {
                    ConfigurationSection exchangeSection = section.getConfigurationSection("item." + price);
                    ItemStack exchangeItem = new ItemStack(Material.getMaterial(exchangeSection.getString("material")));
                    exchangeItem.setAmount(exchangeSection.getInt("amount"));
                    ItemMeta exchangeMeta = exchangeItem.getItemMeta();
                    if(exchangeSection.get("display-name") != null)
                        exchangeMeta.setDisplayName(StringUtil.colorize(exchangeSection.getString("display-name")));
                    if(exchangeSection.get("lore") != null)
                        exchangeMeta.setDisplayName(StringUtil.colorize(exchangeSection.getString("lore")));
                    if(exchangeSection.get("glowing") != null)
                    {
                        if(exchangeSection.getBoolean("glowing"))
                        {
                            exchangeMeta.addEnchant(glow, 1, true);
                        }
                    }
                    exchangeItem.setItemMeta(exchangeMeta);
                    exchangeItems.add(new ExchangeItem(price, exchangeItem));
                }
                FishManager.getInstance().getCustomFishes().put(id, new CustomFish(id, rank, fish, min, max, prices, exchangeItems));
                FishManager.getInstance().getSortedRankFishes().get(rank).add(new CustomFish(id, rank, fish, min, max, prices, exchangeItems));
            }
        }
    }

    @Override
    public void save() {

    }
}
