package com.jonghyun.fishing.loaders;

import com.jonghyun.fishing.manager.FishingManager;
import com.jonghyun.fishing.manager.RankManager;
import com.jonghyun.fishing.objects.fish.CustomFish;
import com.jonghyun.fishing.objects.fish.ExchangeItem;
import com.jonghyun.fishing.objects.fish.SellPrice;
import com.jonghyun.fishing.utils.FileUtil;
import com.jonghyun.fishing.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomFishLoader implements IDataLoader {


    @Override
    public void load() {
        FishingManager.getInstance().getCustomFishes().clear();
        FishingManager.getInstance().getSortedRankFishes().clear();

        for(String rank : RankManager.getInstance().getRanks())
        {
            FishingManager.getInstance().getSortedRankFishes().put(rank, new ArrayList<>());
        }

        File dir = FileUtil.getFolder("fishes");
        if(!dir.exists())
        {
            dir.mkdir();
            FileUtil.createDir("fishes/Common");
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

                ItemStack fish = ItemUtil.getYamlItem(section);

                double min = section.getDouble("cm.min");
                double max = section.getDouble("cm.max");

                List<SellPrice> prices = new ArrayList<>();
                for(String price : section.getConfigurationSection("price").getKeys(false))
                {
                    prices.add(new SellPrice(price.replaceAll("\\|", "."), section.getLong("price." + price)));
                }

                List<ExchangeItem> exchangeItems = new ArrayList<>();
                for(String price : section.getConfigurationSection("item").getKeys(false))
                {
                    ConfigurationSection exchangeSection = section.getConfigurationSection("item." + price);
                    ItemStack exchangeItem = ItemUtil.getYamlItem(exchangeSection);
                    exchangeItems.add(new ExchangeItem(price.replaceAll("\\|", "."), exchangeItem));
                }
                FishingManager.getInstance().getCustomFishes().put(id, new CustomFish(id, rank, fish, min, max, prices, exchangeItems));
                FishingManager.getInstance().getSortedRankFishes().get(rank).add(new CustomFish(id, rank, fish, min, max, prices, exchangeItems));
            }
        }
    }

    @Override
    public void save() {

    }
}
