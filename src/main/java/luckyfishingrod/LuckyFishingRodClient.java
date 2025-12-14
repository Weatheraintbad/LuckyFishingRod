package luckyfishingrod;

import luckyfishingrod.client.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class LuckyFishingRodClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 网络包
        CastStateReceiver.register();
        AutoClearCast.register();

        // 模型属性（1.19.3 开始只有 3 个参数，seed 被移除）
        ModelPredicateProviderRegistry.register(
                LuckyFishingRod.LUCKY_FISHING_ROD,
                new Identifier("casting"),
                (itemStack, clientWorld, livingEntity, seed) -> {   // 补上第四个参数
                    if (livingEntity instanceof PlayerEntity player && PlayerCastState.isCasting(player)) {
                        return 1.0F;
                    }
                    return 0.0F;
                }
        );

        // 物品组插入（1.19.3 签名）
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register(entries -> entries.addAfter(Items.FISHING_ROD,
                        LuckyFishingRod.LUCKY_FISHING_ROD));

        LuckyFishingRod.LOGGER.info("Lucky Fishing Rod client initialized!");
    }
}