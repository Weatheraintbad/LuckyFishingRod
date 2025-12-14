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
        LOGGER.info("Network messages registered");
    }

    public static void sendCastState(ServerPlayerEntity player, boolean casting) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(casting);
        ServerPlayNetworking.send(player, CAST_STATE, buf);
    }
}