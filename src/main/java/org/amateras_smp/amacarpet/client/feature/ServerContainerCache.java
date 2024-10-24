package org.amateras_smp.amacarpet.client.feature;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.LinkedHashMap;
import java.util.Map;

public class ServerContainerCache {
    // maxSize is 2 for large chest or something.
    private static final ServerContainerCache instance = new ServerContainerCache(2);
    private final LinkedHashMap<BlockPos, NbtCompound> cache;
    private final int maxSize;

    private ServerContainerCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new LinkedHashMap<>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<BlockPos, NbtCompound> eldest) {
                return size() > ServerContainerCache.this.maxSize;
            }
        };
    }

    public static ServerContainerCache getInstance() {
        return instance;
    }

    public void put(BlockPos pos, NbtCompound nbt) {
        cache.put(pos, nbt);
    }

    public NbtCompound get(BlockPos pos) {
        return cache.get(pos);
    }
}
