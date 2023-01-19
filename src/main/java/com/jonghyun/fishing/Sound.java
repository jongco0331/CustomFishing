package com.jonghyun.fishing;

import com.jonghyun.fishing.objects.PacketSound;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Sound {
    public static PacketSound OPEN_BAG_GUI;
    public static PacketSound OPEN_SHOP_GUI;
    public static PacketSound SELL_ITEM;
    public static void update()
    {
        File file = new File("plugins/CustomFishing/sound.yml");
        if(!file.exists())
        {
            System.out.println("[Error] sound 파일이 존재하지 않습니다");
            return;
        }
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        OPEN_BAG_GUI = new PacketSound(yaml.getString("OPEN_BAG_GUI.SOUND"), (float)yaml.getDouble("OPEN_BAG_GUI.VOLUME"), (float)yaml.getDouble("OPEN_BAG_GUI.PITCH"));
        OPEN_SHOP_GUI = new PacketSound(yaml.getString("OPEN_SHOP_GUI.SOUND"), (float)yaml.getDouble("OPEN_SHOP_GUI.VOLUME"), (float)yaml.getDouble("OPEN_SHOP_GUI.PITCH"));
        SELL_ITEM = new PacketSound(yaml.getString("SELL_ITEM.SOUND"), (float)yaml.getDouble("SELL_ITEM.VOLUME"), (float)yaml.getDouble("SELL_ITEM.PITCH"));
    }

}
