package luckyfishingrod;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntities {

    // 声明实体类型变量
    public static EntityType<LuckyFishingBobberEntity> LUCKY_FISHING_BOBBER;

    public static void register() {
        // 注册幸运钓鱼浮标实体
        LUCKY_FISHING_BOBBER = Registry.register(
                Registries.ENTITY_TYPE,
                LuckyFishingRod.id("lucky_fishing_bobber"),
                FabricEntityTypeBuilder.<LuckyFishingBobberEntity>create(
                                SpawnGroup.MISC, LuckyFishingBobberEntity::new)
                        .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                        .trackRangeBlocks(64)  // 增加跟踪范围
                        .trackedUpdateRate(5)
                        .forceTrackedVelocityUpdates(true)
                        .build()
        );

        LuckyFishingRod.LOGGER.info("Registered Lucky Fishing Bobber entity");
    }
}