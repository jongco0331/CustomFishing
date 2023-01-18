package com.jonghyun.fishing.object;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class ExchangeItem {

    private String range;
    private ItemStack exchange;

    public ExchangeItem(String range, ItemStack exchange) {
        this.range = range;
        this.exchange = exchange;
    }
}
