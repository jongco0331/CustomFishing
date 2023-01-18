package com.jonghyun.fishing.loaders;

import com.jonghyun.fishing.Fishing;

public interface IDataLoader {

    Fishing plugin = Fishing.getPlugin();

    void load();
    void save();

}
