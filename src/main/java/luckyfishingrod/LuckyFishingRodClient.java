package luckyfishingrod;

import luckyfishingrod.client.*;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class LuckyFishingRodClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // 先注册网络接收器
        CastStateReceiver.register();
        AutoClearCast.register();

        // 注册物品属性来切换模型
        ModelPredicateProviderRegistry.register(
                LuckyFishingRod.LUCKY_FISHING_ROD,
                new Identifier("casting"),
                (itemStack, clientWorld, livingEntity, seed) -> {
                    if (livingEntity instanceof PlayerEntity player && PlayerCastState.isCasting(player)) {
                        return 1.0F;
                    }
                    return 0.0F;
                }
        );

        LuckyFishingRod.LOGGER.info("Lucky Fishing Rod client initialized!");
    }
}