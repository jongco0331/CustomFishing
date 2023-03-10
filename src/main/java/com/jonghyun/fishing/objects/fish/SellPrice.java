package com.jonghyun.fishing.objects.fish;

import lombok.Getter;

@Getter
public class SellPrice {

    private String range;
    private long price;

    public SellPrice(String range, long price) {
        this.range = range;
        this.price = price;
    }
}
