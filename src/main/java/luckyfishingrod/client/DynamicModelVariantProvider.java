package luckyfishingrod.client;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelResolver;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class DynamicModelVariantProvider implements ModelResolver {

    @Override
    @Nullable
    public UnbakedModel resolveModel(Context context) {
        // 现在不需要在这里处理模型切换，因为物品属性已经处理了
        return null;
    }
}