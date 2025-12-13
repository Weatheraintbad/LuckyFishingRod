package luckyfishingrod.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class AutoClearCast {

    public static void register() {
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            PlayerCastState.clearAll();
        });
    }
}