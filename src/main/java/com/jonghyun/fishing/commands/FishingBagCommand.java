package com.jonghyun.fishing.commands;

import com.jonghyun.fishing.guis.GuiFishingBag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FishingBagCommand extends SimpleCommand {

    public FishingBagCommand()
    {
        super(false, "낚시가방");
    }

    @Override
    public void execute(Player p, String[] args) {
        if(!p.hasPermission("op"))
        {
            new GuiFishingBag(p);
            return;
        }
        if(args.length == 0)
        {
            new GuiFishingBag(p);
            return;
        }
        new GuiFishingBag(p, Bukkit.getOfflinePlayer(args[0]));
    }
}
