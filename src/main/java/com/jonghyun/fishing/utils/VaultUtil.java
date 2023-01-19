package com.jonghyun.fishing.utils;

import com.jonghyun.fishing.Fishing;
import org.bukkit.entity.Player;

public class VaultUtil {

    public static void addMoney(Player p, long money)
    {
        Fishing.getEcon().depositPlayer(p, money);
    }

    public static void subtractMoney(Player p, long money)
    {
        Fishing.getEcon().withdrawPlayer(p, money);
    }


}
