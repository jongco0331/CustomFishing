package com.jonghyun.fishing.manager;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class RankManager {

    private static RankManager rankManager = null;

    private RankManager() {}

    public static RankManager getInstance()
    {
        if(rankManager == null)
            rankManager = new RankManager();
        return rankManager;
    }

    @Getter @Setter
    private List<String> ranks = new ArrayList<>();
    @Getter @Setter
    private HashMap<String, Integer> rankChance = new HashMap<>();

}
