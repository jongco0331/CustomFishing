package com.jonghyun.fishing.manager;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public final class RankManager {

    private static RankManager rankManager = null;

    private RankManager() {}

    public static RankManager getInstance()
    {
        if(rankManager == null)
            rankManager = new RankManager();
        return rankManager;
    }

    @Getter
    private List<String> ranks = new ArrayList<>();
    @Getter
    private HashMap<String, Integer> rankChance = new HashMap<>();

    @Getter @Setter
    private int chance = 0;

    public String randomRank()
    {
        int selected = new Random().nextInt(chance);
        int chance = 0;
        for(Map.Entry<String, Integer> entry : rankChance.entrySet())
        {
            if(entry.getValue() + chance > selected)
                return entry.getKey();
            chance += entry.getValue();
        }
        return null;
    }

}
