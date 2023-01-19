package com.jonghyun.fishing.commands;

import com.jonghyun.fishing.guis.shop.GuiShop;
import com.jonghyun.fishing.manager.FishingShopManager;
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
            p.sendMessage("/커스텀낚시 상점 [id]");
            return;
        }
        if(args[0].equalsIgnoreCase("reload"))
        {
            LoadManager.getInstance().save();
            LoadManager.getInstance().load();
            p.sendMessage("Reload Confirm");
        }
        if(args[0].equalsIgnoreCase("상점"))
        {
            if(args.length == 1)
            {
                p.sendMessage("§c상점의 이름을 입력해주세요");
                return;
            }
            String id = args[1];
            if(!FishingShopManager.getInstance().getFishingShops().containsKey(id))
            {
                p.sendMessage("§c존재하지 않는 상점 id입니다");
                return;
            }
            new GuiShop(p, id);
            return;
        }
        return;
    }
}
