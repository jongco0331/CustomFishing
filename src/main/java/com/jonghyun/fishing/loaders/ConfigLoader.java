package com.jonghyun.fishing.loaders;

import com.jonghyun.fishing.Language;
import com.jonghyun.fishing.Sound;
import com.jonghyun.fishing.utils.FileUtil;

public class ConfigLoader implements IDataLoader {

    @Override
    public void load() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        FileUtil.createDir("lang/ko_KR");
        FileUtil.createDir("sound");

        Language.update(plugin.getConfig().getString("lang"));
        Sound.update();
    }

    @Override
    public void save() {

    }
}
