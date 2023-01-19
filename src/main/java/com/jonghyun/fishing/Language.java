package com.jonghyun.fishing;

import com.jonghyun.fishing.utils.StringUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Language {

    public static String FISH_SUCCESS_TITLE;
    public static String FISH_SUCCESS_SUBTITLE;
    public static String FISH_FAIL_TITLE;
    public static String FISH_FAIL_SUBTITLE;
    public static String GET_FISH;
    public static String FISHING_GUIDELINE;
    public static String FISHING_FISH_HEALTH;

    public static void update(String lang)
    {
        File file = new File("plugins/CustomFishing/lang/" + lang + ".yml");
        if (!file.exists()) {
            System.out.println("[ERROR] lang 파일이 존재하지 않습니다 " + file.getName());
            return;
        }
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        FISH_SUCCESS_TITLE = StringUtil.colorize(yaml.getString("FISH_SUCCESS_TITLE"));
        FISH_SUCCESS_SUBTITLE = StringUtil.colorize(yaml.getString("FISH_SUCCESS_SUBTITLE"));
        FISH_FAIL_TITLE = StringUtil.colorize(yaml.getString("FISH_FAIL_TITLE"));
        FISH_FAIL_SUBTITLE = StringUtil.colorize(yaml.getString("FISH_FAIL_SUBTITLE"));
        GET_FISH = StringUtil.colorize(yaml.getString("GET_FISH"));
        FISHING_GUIDELINE = StringUtil.colorize(yaml.getString("FISHING_GUIDELINE"));
        FISHING_FISH_HEALTH = StringUtil.colorize(yaml.getString("FISHING_FISH_HEALTH"));
    }

}
