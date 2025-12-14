package luckyfishingrod;

import luckyfishingrod.network.NetworkMessages;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class LuckyFishingRodItem extends net.minecraft.item.FishingRodItem {

    public LuckyFishingRodItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (user.fishHook != null) {            // 已有浮标则收回
            if (!world.isClient) {
                int dmg = user.fishHook.use(stack);
                stack.damage(dmg, user, p -> p.sendToolBreakStatus(hand));

                // 通知客户端结束投掷状态
                if (user instanceof ServerPlayerEntity sp) {
                    NetworkMessages.sendCastState(sp, false);
                }
            }
            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL,
                    1.0F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            user.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);

        } else {                                // 抛出浮标
            world.playSound(null, user.getX(), user.getY(), user.getZ(),
                    SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL,
                    0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!world.isClient) {
                int lure = EnchantmentHelper.getLure(stack);
                int luck = EnchantmentHelper.getLuckOfTheSea(stack);

                // 使用自定义钓鱼浮标实体
                LuckyFishingBobberEntity bobber =
                        LuckyFishingBobberEntity.create(user, world, luck, lure);
                world.spawnEntity(bobber);

                // 通知客户端进入抛竿状态
                if (user instanceof ServerPlayerEntity sp) {
                    NetworkMessages.sendCastState(sp, true);
                }
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            user.emitGameEvent(GameEvent.ITEM_INTERACT_START);
        }

        return TypedActionResult.success(stack, world.isClient());
    }
}