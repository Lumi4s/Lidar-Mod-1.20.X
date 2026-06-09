package net.lumi.lidarmod.util;

import net.lumi.lidarmod.LidarMod;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> LIDAR_NOT_DETECTABLE_BLOCKS =
                createTag("lidar_not_detectable_blocks");

        private static TagKey<Block> createTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(LidarMod.MOD_ID, name));
        }
    }
}
