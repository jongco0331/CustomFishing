package com.jonghyun.fishing.objects.fish;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class CustomFish {

    private String id;
    private String rank;
    private ItemStack fish;
    private double min;
    private double max;
    private List<SellPrice> price;
    private List<ExchangeItem> exchangeItems;

    public CustomFish(String id, String rank, ItemStack fish, double min, double max, List<SellPrice> price, List<ExchangeItem> exchangeItems) {
        this.id = id;
        this.rank = rank;
        this.fish = fish;
        this.min = min;
        this.max = max;
        this.price = price;
        this.exchangeItems = exchangeItems;
    }
}
