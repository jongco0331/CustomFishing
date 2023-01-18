package com.jonghyun.fishing.commands;

import com.jonghyun.fishing.manager.LoadManager;
import org.bukkit.entity.Player;

public class FishingCommand extends SimpleCommand {

    public FishingCommand()
    {
        super(false, "커스텀낚시");
    }

    @Override
    public void execute(Player p, String[] args) {
        if(args.length == 0)
        {
            p.sendMessage("/커스텀낚시 reload");
            return;
        }
        if(args[0].equalsIgnoreCase("reload"))
        {
            LoadManager.getInstance().save();
            LoadManager.getInstance().load();
            p.sendMessage("Reload Confirm");
        }
        return;
    }
}
