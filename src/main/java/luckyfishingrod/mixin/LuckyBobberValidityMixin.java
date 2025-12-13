package luckyfishingrod.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import luckyfishingrod.LuckyFishingRod;

@Mixin(FishingBobberEntity.class)
public class LuckyBobberValidityMixin {

    @Inject(method = "removeIfInvalid", at = @At("HEAD"), cancellable = true)
    private void onRemoveIfInvalid(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        FishingBobberEntity self = (FishingBobberEntity)(Object)this;

        // 检查是否是该玩家拥有的浮标
        if (self.getPlayerOwner() != player) {
            return;
        }

        // 检查玩家是否持有幸运钓鱼竿
        ItemStack main = player.getMainHandStack();
        ItemStack off = player.getOffHandStack();

        // 使用主类中的物品引用
        boolean hasLuckyRod = main.getItem() == LuckyFishingRod.LUCKY_FISHING_ROD ||
                off.getItem() == LuckyFishingRod.LUCKY_FISHING_ROD;

        // 如果玩家持有幸运钓鱼竿，并且浮标是LuckyFishingBobberEntity类型，则不无效化
        if (hasLuckyRod && self instanceof luckyfishingrod.LuckyFishingBobberEntity) {
            cir.setReturnValue(false); // 不无效化
            cir.cancel();
        }
    }
}