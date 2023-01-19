package com.jonghyun.fishing.guis.shop;

import com.jonghyun.fishing.Sound;
import com.jonghyun.fishing.manager.FishingShopManager;
import com.jonghyun.fishing.objects.shop.EventClickType;
import com.jonghyun.fishing.objects.shop.FishShop;
import com.jonghyun.fishing.utils.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GuiShop implements Listener {

    public GuiShop(Player p, String shopName)
    {
        FishShop fishingShop = FishingShopManager.getInstance().getFishingShops().get(shopName);
        Inventory inv = Bukkit.createInventory(null, fishingShop.getContents().length, "[ 낚시상점 ] " + shopName);
        for(int i = 0; i < fishingShop.getContents().length; i++)
        {
            inv.setItem(i, fishingShop.getComponentsMap().get(fishingShop.getContents()[i]).getStack());
        }
        p.openInventory(inv);
        SoundUtil.playSound(p, Sound.OPEN_SHOP_GUI);
    }

    public GuiShop() {}

    @EventHandler
    public void onInvClick(InventoryClickEvent e)
    {
        if(e.getView().getTitle().contains("[ 낚시상점 ] "))
        {
            e.setCancelled(true);
            if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)
                return;
            String id = e.getView().getTitle().replaceAll("\\[ 낚시상점 \\] ", "");
            int slot = e.getRawSlot();
            FishingShopManager shopManager = FishingShopManager.getInstance();
            FishShop fishShop = shopManager.getFishingShops().get(id);
            String event = fishShop.getContents()[slot];
            if(e.getClick() == ClickType.LEFT)
            {
                shopManager.performEvent((Player) e.getWhoClicked(), fishShop.getComponentsMap().get(event).getEvent().get(EventClickType.LEFT));
                return;
            }
            if(e.getClick() == ClickType.RIGHT)
            {
                shopManager.performEvent((Player) e.getWhoClicked(), fishShop.getComponentsMap().get(event).getEvent().get(EventClickType.RIGHT));
                return;
            }
            if(e.getClick() == ClickType.SHIFT_LEFT)
            {
                shopManager.performEvent((Player) e.getWhoClicked(), fishShop.getComponentsMap().get(event).getEvent().get(EventClickType.SHIFT_LEFT));
                return;
            }
            if(e.getClick() == ClickType.SHIFT_RIGHT)
            {
                shopManager.performEvent((Player) e.getWhoClicked(), fishShop.getComponentsMap().get(event).getEvent().get(EventClickType.SHIFT_RIGHT));
                return;
            }
        }
    }

}
