package luckyfishingrod;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings; // ← 新增
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class ModItems {

    public static Item LUCKY_FISHING_ROD;

    public static void register() {
        LUCKY_FISHING_ROD = new LuckyFishingRodItem(
                new FabricItemSettings()
                        .maxDamage(256)          // 耐久值
                        .rarity(Rarity.UNCOMMON) // 稀有度
        );

        Registry.register(Registries.ITEM,
                LuckyFishingRod.id("lucky_fishing_rod"),
                LUCKY_FISHING_ROD);

        LuckyFishingRod.LOGGER.info("Registered Lucky Fishing Rod item");
    }
}