package net.lumi.lidarmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.lumi.lidarmod.LidarMod;
import net.lumi.lidarmod.item.custom.LidarItem;
import net.lumi.lidarmod.item.custom.VisorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item VISOR = registerItem("visor", new VisorItem(
            new FabricItemSettings().maxCount(1)));

    public static final Item LIDAR = registerItem("lidar", new LidarItem(
            new FabricItemSettings().maxCount(1)));

    private static void addItemsToToolsItemGroup(FabricItemGroupEntries entries) {
        entries.add(LIDAR);
        entries.add(VISOR);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(LidarMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LidarMod.LOGGER.info("Registering Mod Items for " + LidarMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToToolsItemGroup);
    }
}
