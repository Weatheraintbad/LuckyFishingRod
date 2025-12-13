package luckyfishingrod.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkMessages {
    public static final Identifier CAST_STATE = new Identifier("luckyfishingrod", "cast_state");
    private static final Logger LOGGER = LoggerFactory.getLogger("luckyfishingrod");

    public static void register() {
        // 服务器端注册接收器（如果需要接收客户端消息）
        // 如果没有需要服务器接收的消息，这个方法可以留空或添加日志
        LOGGER.info("Network messages registered");
    }

    public static void sendCastState(ServerPlayerEntity player, boolean casting) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(casting);
        ServerPlayNetworking.send(player, CAST_STATE, buf);
    }
}