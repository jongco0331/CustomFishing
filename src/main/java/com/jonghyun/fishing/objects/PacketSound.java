package com.jonghyun.fishing.objects;

import lombok.Getter;

@Getter
public class PacketSound {

    private String SOUND_NAME;
    private float volume;
    private float pitch;

    public PacketSound(String SOUND_NAME, float volume, float pitch) {
        this.SOUND_NAME = SOUND_NAME;
        this.volume = volume;
        this.pitch = pitch;
    }

}
