package luckyfishingrod;

import net.minecraft.entity.*;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import luckyfishingrod.mixin.FishingBobberAccessor;

import java.util.List;
import java.util.Random;

public class LuckyFishingBobberEntity extends FishingBobberEntity {
    private static final Random RANDOM = new Random();

    // 简化：只保留一个构造函数供Fabric使用
    public LuckyFishingBobberEntity(EntityType<? extends FishingBobberEntity> type, World world) {
        super(type, world);
    }

    // 添加一个方便的构造函数供工厂方法使用
    private LuckyFishingBobberEntity(PlayerEntity thrower, World world, int luckOfTheSea, int lureLevel) {
        super(thrower, world, luckOfTheSea, lureLevel);
    }

    /* 业务工厂 – 鱼竿调用：用 4 参构造器，owner 会被正确写入 */
    public static LuckyFishingBobberEntity create(PlayerEntity player,
                                                  World world,
                                                  int luckOfTheSea,
                                                  int lureLevel) {
        /* 使用自定义构造器 */
        return new LuckyFishingBobberEntity(player, world, luckOfTheSea, lureLevel);
    }

    @Override
    public int use(ItemStack usedItem) {
        PlayerEntity player = this.getPlayerOwner();
        if (this.getWorld().isClient || player == null) return 0;

        /* ‼ 提前收杆：没冒泡就不给东西 */
        // 使用反射或mixin访问hookCountdown，这里假设你的mixin正确工作
        try {
            // 通过mixin访问私有字段
            if (((FishingBobberAccessor) this).getHookCountdown() <= 0) {
                this.discard();   // 只回收浮标
                return 0;         // 0 表示无战利品，也不消耗耐久
            }
        } catch (Exception e) {
            // 如果mixin有问题，使用备用方案
            LuckyFishingRod.LOGGER.warn("Failed to access hookCountdown, using fallback logic");
            this.discard();
            return 0;
        }

        /* 真正咬钩后，保持原来的随机掉落逻辑 */
        if (this.getHookedEntity() != null) {
            return super.use(usedItem);
        }

        // 获取所有注册的物品
        List<ItemStack> items = Registries.ITEM.stream()
                .filter(item -> item.isEnabled(player.getWorld().getEnabledFeatures()))
                .map(ItemStack::new)
                .filter(stack -> !stack.isEmpty())
                .toList();

        if (items.isEmpty()) {
            this.discard();
            return 0;
        }

        ItemStack loot = items.get(RANDOM.nextInt(items.size())).copy();

        /* ===== ① 生成位置：玩家面前 1 格、脚下 +0.5 ===== */
        Vec3d look = player.getRotationVec(1.0F);
        Vec3d spawn = new Vec3d(player.getX(),
                player.getY() + player.getEyeHeight(player.getPose()) * 0.5,
                player.getZ()).add(look.multiply(1.0));

        ItemEntity itemEntity = new ItemEntity(this.getWorld(),
                spawn.x, spawn.y, spawn.z,
                loot);
        itemEntity.setPickupDelay(10); // 10 tick延迟可拾取

        /* ===== ② 小幅吸引速度 ===== */
        double dx = player.getX() - spawn.x;
        double dy = (player.getY() + player.getStandingEyeHeight() - spawn.y) * 0.15;
        double dz = player.getZ() - spawn.z;
        itemEntity.setVelocity(dx * 0.12, dy + 0.05, dz * 0.12);
        itemEntity.setVelocity(itemEntity.getVelocity().add(0, 0.05, 0)); // 稍微向上
        this.getWorld().spawnEntity(itemEntity);

        /* ===== ③ 经验球同位置生成（自带吸引） ===== */
        this.getWorld().spawnEntity(new ExperienceOrbEntity(
                this.getWorld(),
                spawn.x, spawn.y, spawn.z,
                RANDOM.nextInt(6) + 1));

        this.discard();
        return 1;   // 1 表示成功钓到东西，会消耗耐久
    }

    // 可选：重写tick方法来自定义行为
    @Override
    public void tick() {
        super.tick();
        // 可以在这里添加自定义的tick逻辑
    }
}