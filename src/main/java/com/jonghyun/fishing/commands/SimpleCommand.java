package com.jonghyun.fishing.commands;

import com.jonghyun.fishing.Fishing;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SimpleCommand implements CommandExecutor {

    boolean canConsoleUse;
    String command;
    Fishing plugin;

    public SimpleCommand(boolean canConsoleUse, String command)
    {
        this.canConsoleUse =canConsoleUse;
        this.command = command;
        plugin = Fishing.getPlugin();
        plugin.getCommand(command).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!canConsoleUse && !(commandSender instanceof Player))
            return false;
        execute((Player) commandSender, strings);
        return false;
    }

    public void execute(Player p, String[] args)
    {

    }
}
