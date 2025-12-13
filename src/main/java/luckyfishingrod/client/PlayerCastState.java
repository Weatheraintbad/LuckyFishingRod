package luckyfishingrod.client;

import net.minecraft.entity.player.PlayerEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCastState {
    private static final Map<UUID, Boolean> CASTING = new HashMap<>();

    public static boolean isCasting(PlayerEntity player) {
        return CASTING.getOrDefault(player.getUuid(), false);
    }

    public static void setCasting(PlayerEntity player, boolean casting) {
        if (casting) {
            CASTING.put(player.getUuid(), true);
        } else {
            CASTING.remove(player.getUuid());
        }
    }

    public static void clearAll() {
        CASTING.clear();
    }
}