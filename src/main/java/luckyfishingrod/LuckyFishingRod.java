package luckyfishingrod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuckyFishingRod implements ModInitializer {
    public static final String MOD_ID = "luckyfishingrod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // 直接在这里初始化，省得后面再赋值
    public static final Item LUCKY_FISHING_ROD = new LuckyFishingRodItem(
            new FabricItemSettings()
                    .maxDamage(64)          // 1.19.3 用 FabricItemSettings
    );

    @Override
    public void onInitialize() {
        // 1. 注册物品
        Registry.register(Registries.ITEM, id("lucky_fishing_rod"), LUCKY_FISHING_ROD);

        // 2. 注册实体
        ModEntities.register();

        // 3. 网络包
        luckyfishingrod.network.NetworkMessages.register();

        LOGGER.info("Lucky Fishing Rod mod initialized!");
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}