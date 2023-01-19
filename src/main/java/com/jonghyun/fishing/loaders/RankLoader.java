package com.jonghyun.fishing.loaders;

import com.jonghyun.fishing.manager.RankManager;
import com.jonghyun.fishing.utils.FileUtil;
import com.jonghyun.fishing.utils.StringUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class RankLoader implements IDataLoader {

    @Override
    public void load() {
        FileUtil.createDir("rank");
        YamlConfiguration rankYaml = FileUtil.getYaml("rank");

        RankManager rankManager = RankManager.getInstance();
        rankManager.getRankChance().clear();
        rankManager.getRanks().clear();

        rankManager.getRanks().addAll(StringUtil.colorize(rankYaml.getStringList("rank")));
        ConfigurationSection section = rankYaml.getConfigurationSection("fish-chance");
        int chance = 0;
        for(String rank : section.getKeys(false))
        {
            rankManager.getRankChance().put(rank, section.getInt(rank));
            chance += section.getInt(rank);
        }
        rankManager.setChance(chance);
    }

    @Override
    public void save() {

    }
}
