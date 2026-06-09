package net.lumi.lidarmod;

import net.fabricmc.api.ModInitializer;
import net.lumi.lidarmod.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LidarMod implements ModInitializer {
    public static final String MOD_ID = "lidarmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
    }
}