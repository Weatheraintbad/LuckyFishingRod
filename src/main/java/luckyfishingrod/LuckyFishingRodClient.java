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
        // 1. 网络与模型
        CastStateReceiver.register();
        AutoClearCast.register();

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

        // 2. 把幸运钓竿插到“工具”栏，紧挨原版钓鱼竿
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries ->
                entries.addAfter(Items.FISHING_ROD, LuckyFishingRod.LUCKY_FISHING_ROD));

        LuckyFishingRod.LOGGER.info("Lucky Fishing Rod client initialized!");
    }
}