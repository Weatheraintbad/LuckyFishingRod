package luckyfishingrod.client;

import luckyfishingrod.network.NetworkMessages;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

public class CastStateReceiver {

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkMessages.CAST_STATE,
                (client, handler, buf, responseSender) -> {
                    boolean casting = buf.readBoolean();
                    client.execute(() ->
                            PlayerCastState.setCasting(client.player, casting));
                });
    }
}