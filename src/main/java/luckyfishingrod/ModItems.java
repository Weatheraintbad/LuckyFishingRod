package luckyfishingrod;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class ModItems {

    public static Item LUCKY_FISHING_ROD;

    public static void register() {
        // 创建幸运钓鱼竿物品
        LUCKY_FISHING_ROD = new LuckyFishingRodItem(
                new Item.Settings()
                        .maxDamage(256)
                        .rarity(Rarity.UNCOMMON)
        );

        // 注册物品
        Registry.register(Registries.ITEM,
                LuckyFishingRod.id("lucky_fishing_rod"),
                LUCKY_FISHING_ROD);

        LuckyFishingRod.LOGGER.info("Registered Lucky Fishing Rod item");
    }
}