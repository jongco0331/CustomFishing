package com.jonghyun.fishing.utils;

import com.jonghyun.fishing.Fishing;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileUtil {

    protected static Fishing plugin = Fishing.getPlugin();

    public static void createDir(String dir)
    {
        if(!new File("plugins/CustomFishing/" + dir + ".yml").exists())
            plugin.saveResource(dir + ".yml", false);
    }

    public static YamlConfiguration getYaml(String dir)
    {
        File file = new File("plugins/CustomFishing/" + dir + ".yml");
        if(!file.exists())
            return null;
        return YamlConfiguration.loadConfiguration(file);
    }

    public static File getFolder(String dir)
    {
        return new File("plugins/CustomFishing/" + dir);
    }

}
