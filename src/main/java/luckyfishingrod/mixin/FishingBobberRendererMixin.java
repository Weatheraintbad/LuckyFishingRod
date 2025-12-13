package luckyfishingrod.mixin.client;

import luckyfishingrod.LuckyFishingRod;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingBobberEntityRenderer.class)
public class FishingBobberRendererMixin {

    /* 仅拦截第一次 getMainArm() 调用（主手），第二次固定是 LEFT，不变 */
    @Redirect(
            method = "render(Lnet/minecraft/entity/projectile/FishingBobberEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getMainArm()Lnet/minecraft/util/Arm;"),
            require = 1)
    private Arm luckyFlipMainHandOnly(PlayerEntity player) {
        // 主手拿着幸运钓竿 → 返回 LEFT，让渲染器取右手坐标
        if (player.getMainHandStack().isOf(LuckyFishingRod.LUCKY_FISHING_ROD)) {
            return Arm.LEFT;
        }
        // 否则保持原版
        return player.getMainArm();
    }
}