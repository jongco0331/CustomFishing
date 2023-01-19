package com.jonghyun.fishing.utils;

import com.jonghyun.fishing.Glow;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {
    static Glow glow = new Glow(255);

    public static ItemStack getYamlItem(ConfigurationSection section)
    {
        try {
            if(section.getString("material") == null)
                return null;
            ItemStack stack = new ItemStack(Material.getMaterial(section.getString("material")));
            ItemMeta meta = stack.getItemMeta();
            if(section.get("flags") != null)
            {
                for(String flag : section.getStringList("flags"))
                {
                    meta.addItemFlags(ItemFlag.valueOf(flag));
                }
            }
            if(section.get("durability") != null)
            {
                meta.setUnbreakable(true);
                stack.setDurability((short) section.getInt("durability"));
            }
            if(section.get("display-name") != null)
                meta.setDisplayName(StringUtil.colorize(section.getString("display-name")));
            if(section.get("lore") != null)
                meta.setLore(StringUtil.colorize(section.getStringList("lore")));
            if(section.get("glowing") != null && section.getBoolean("glowing"))
                meta.addEnchant(glow, 1, true);
            if(section.get("amount") != null)
                stack.setAmount(section.getInt("amount"));
            stack.setItemMeta(meta);
            return stack;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
