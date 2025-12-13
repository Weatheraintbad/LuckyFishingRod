package luckyfishingrod.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerMainHandFlipMixin {

    /* 只在渲染器查询“主手”时返回左手，让它去取右手坐标 */
    @Inject(method = "getMainArm", at = @At("HEAD"), cancellable = true)
    private void returnLeftForRender(CallbackInfoReturnable<Arm> cir) {
        /* 调用栈里有渲染器就翻转 */
        for (StackTraceElement e : Thread.currentThread().getStackTrace()) {
            if (e.getClassName().contains("FishingBobberEntityRenderer")) {
                cir.setReturnValue(Arm.LEFT);
                return;
            }
        }
    }
}