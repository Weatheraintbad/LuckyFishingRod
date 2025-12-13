package luckyfishingrod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuckyFishingRod implements ModInitializer {
    public static final String MOD_ID = "luckyfishingrod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // 在这里声明物品变量，方便其他类访问
    public static Item LUCKY_FISHING_ROD;

    @Override
    public void onInitialize() {
        // 先注册物品和实体
        ModItems.register();
        ModEntities.register();

        // 初始化网络 - 使用正确的导入或完整路径
        luckyfishingrod.network.NetworkMessages.register();

        // 将物品引用赋值给主类的静态变量
        LUCKY_FISHING_ROD = ModItems.LUCKY_FISHING_ROD;

        LOGGER.info("Lucky Fishing Rod mod initialized!");
    }

    // 辅助方法：创建标识符
    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}