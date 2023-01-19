package com.jonghyun.fishing.utils;

import com.jonghyun.fishing.objects.PacketSound;
import net.minecraft.server.v1_12_R1.MinecraftKey;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_12_R1.SoundCategory;
import net.minecraft.server.v1_12_R1.SoundEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SoundUtil {

    public static void playSound(Player p, PacketSound object)
    {
        if(object.getSOUND_NAME() != null)
            playSound(p, SoundEffect.a.get(new MinecraftKey(object.getSOUND_NAME())), object.getPitch(), object.getVolume());
    }

    public static void playSound(Player p, SoundEffect effect, float pitch, float volume)
    {
        Location loc = p.getLocation();
        PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect(effect, SoundCategory.PLAYERS, loc.getX(), loc.getY(), loc.getZ(), volume, pitch);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }
}
