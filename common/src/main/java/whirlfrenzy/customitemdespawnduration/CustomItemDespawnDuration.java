package whirlfrenzy.customitemdespawnduration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import whirlfrenzy.customitemdespawnduration.config.CustomItemDespawnDurationConfig;

public final class CustomItemDespawnDuration {
    public static final String MOD_ID = "custom-item-despawn-duration";
    public static final String FORGE_MOD_ID = "custom_item_despawn_duration";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final int THIRTY_MINUTES_IN_SECONDS = 1800;

    public static void init() {
        CustomItemDespawnDurationConfig.init(MOD_ID, CustomItemDespawnDurationConfig.class);
    }
}
